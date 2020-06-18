package com.authguidance.mobilewebview.plumbing.interop

import android.app.Activity
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.authguidance.mobilewebview.plumbing.errors.ErrorHandler
import com.authguidance.mobilewebview.plumbing.events.LoginCompletedEvent
import com.authguidance.mobilewebview.plumbing.events.LogoutCompletedEvent
import com.authguidance.mobilewebview.plumbing.oauth.Authenticator
import com.authguidance.mobilewebview.views.utilities.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/*
 * Mobile entry points called from Javascript in our SPA
 */
class JavascriptBridgeImpl(
    private val webView: WebView,
    private val authenticator: Authenticator,
    private val activity: Activity
) : JavascriptBridge {

    private var loginCallbackName: String = ""
    private var logoutCallbackName: String = ""
    private var redirectInProgress: Boolean = false

    /*
     * Register with the events system
     */
    init {
        EventBus.getDefault().register(this)
    }

    /*
     * Handle SPA requests for the logged in state
     */
    @JavascriptInterface
    override fun isLoggedIn(callbackName: String) {

        val that = this@JavascriptBridgeImpl
        CoroutineScope(Dispatchers.Main).launch {

            try {
                // Do the mobile work
                that.successResult(callbackName, that.authenticator.isLoggedIn().toString())

            } catch (ex: Throwable) {

                // Call back the SPA with an error response
                that.errorResult(callbackName, ex)
            }
        }
    }

    /*
     * Handle SPA requests to get an access token
     */
    @JavascriptInterface
    override fun getAccessToken(callbackName: String) {

        val that = this@JavascriptBridgeImpl
        CoroutineScope(Dispatchers.IO).launch {

            try {
                // Do the mobile work
                val accessToken = authenticator.getAccessToken()

                // Call back the SPA with an access token
                withContext(Dispatchers.Main) {
                    that.successResult(callbackName, accessToken)
                }

            } catch (ex: Throwable) {

                // Call back the SPA with an error response
                withContext(Dispatchers.Main) {
                    that.errorResult(callbackName, ex)
                }
            }
        }
    }

    /*
     * Handle SPA requests to refresh an access token
     */
    @JavascriptInterface
    override fun refreshAccessToken(callbackName: String) {

        val that = this@JavascriptBridgeImpl
        CoroutineScope(Dispatchers.IO).launch {

            try {
                // Do the mobile work
                val accessToken = authenticator.refreshAccessToken()

                // Call back the SPA with an access token
                withContext(Dispatchers.Main) {
                    that.successResult(callbackName, accessToken)
                }

            } catch (ex: Throwable) {

                // Call back the SPA with an error response
                withContext(Dispatchers.Main) {
                    that.errorResult(callbackName, ex)
                }
            }
        }
    }

    /*
     * Handle SPA requests to trigger a login redirect
     */
    @JavascriptInterface
    override fun login(callbackName: String) {

        // Run on the UI thread since we present UI elements
        this.loginCallbackName = callbackName
        CoroutineScope(Dispatchers.Main).launch {

            val that = this@JavascriptBridgeImpl
            try {

                // Start the redirect
                that.redirectInProgress = true
                that.authenticator.startLogin(that.activity, Constants.LOGIN_REDIRECT_REQUEST_CODE)

            } catch (ex: Throwable) {

                // Report errors such as those looking up endpoints
                that.redirectInProgress = false
                that.errorResult(callbackName, ex)
            }
        }
    }

    /*
     * Subscribe to the login completed event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginCompleted(event: LoginCompletedEvent) {

        // If the login was not initiated from Javascript then do nothing
        if (!this.redirectInProgress) {
            return
        }

        // Notify the SPA and return an error if applicable
        this.redirectInProgress = false
        if (event.exception == null) {
            this.successResult(this.loginCallbackName, "")
        } else {
            this.errorResult(this.loginCallbackName, event.exception)
        }
    }

    /*
     * Handle SPA requests to trigger a logout redirect
     */
    @JavascriptInterface
    override fun logout(callbackName: String) {

        // Run on the UI thread since we present UI elements
        this.logoutCallbackName = callbackName
        CoroutineScope(Dispatchers.Main).launch {

            val that = this@JavascriptBridgeImpl
            try {

                // Start the redirect
                that.redirectInProgress = true
                that.authenticator.startLogout(that.activity, Constants.LOGOUT_REDIRECT_REQUEST_CODE)

            } catch (ex: Throwable) {

                // Report errors such as those looking up endpoints
                that.redirectInProgress = false
                that.errorResult(callbackName, ex)
            }
        }
    }

    /*
     * Subscribe to the logout completed event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLogoutCompleted(event: LogoutCompletedEvent) {

        // If the logout was not initiated from Javascript then do nothing
        event.used()
        if (!this.redirectInProgress) {
            return
        }

        // Notify the SPA
        this.redirectInProgress = false
        this.successResult(this.logoutCallbackName, "")
    }

    /*
     * Handle SPA requests to expire the access token
     */
    @JavascriptInterface
    override fun expireAccessToken(callbackName: String) {

        val that = this@JavascriptBridgeImpl
        CoroutineScope(Dispatchers.Main).launch {

            try {
                // Do the mobile work
                that.authenticator.expireAccessToken()
                that.successResult(callbackName, "")

            } catch (ex: Throwable) {

                // Call back the SPA with an error response
                withContext(Dispatchers.Main) {
                    that.errorResult(callbackName, ex)
                }
            }
        }
    }

    /*
     * Handle SPA requests to expire the refresh token
     */
    @JavascriptInterface
    override fun expireRefreshToken(callbackName: String) {

        val that = this@JavascriptBridgeImpl
        CoroutineScope(Dispatchers.Main).launch {

            try {
                // Do the mobile work
                that.authenticator.expireRefreshToken()
                that.successResult(callbackName, "")

            } catch (ex: Throwable) {

                // Call back the SPA with an error response
                withContext(Dispatchers.Main) {
                    that.errorResult(callbackName, ex)
                }
            }
        }
    }

    /*
     * Return a success result back to the SPA
     */
    private fun successResult(callbackName: String, data: String) {
        webView.loadUrl("javascript: window['$callbackName']('$data', null)")
    }

    /*
     * Pass an error result back to the SPA as a JSON object
     */
    private fun errorResult(callbackName: String, ex: Throwable) {

        val errorJson = ErrorHandler().fromException(ex).toJson()
        webView.loadUrl("javascript: window['$callbackName'](null, '$errorJson')")
    }
}
