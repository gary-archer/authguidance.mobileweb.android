package com.authguidance.mobilewebview.plumbing.webview

import android.webkit.JavascriptInterface
import android.webkit.WebView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/*
 * The authenticator class has methods called from Javascript in an SPA
 */
class AuthenticatorImpl(private val webView: WebView) : Authenticator {

    /*
     * The SPA calls this to get an access token
     */
    @JavascriptInterface
    override fun getAccessToken(callbackName: String) {

        val that = this@AuthenticatorImpl
        CoroutineScope(Dispatchers.IO).launch {

            try {
                // Do the mobile work

                // Call back the SPA with an access token
                withContext(Dispatchers.Main) {
                    val accessToken = "eyJraWQiOiJvNWh6MGNBe"
                    that.successResult(callbackName, accessToken)
                }

            } catch (ex: Throwable) {

                // Call back the SPA with an error response
                val errorText = ex.message ?: ""
                withContext(Dispatchers.Main) {
                    that.errorResult(callbackName, errorText)
                }
            }
        }
    }

    /*
     * Return a success result to Javascript
     */
    private fun successResult(callbackName: String, data: String) {
        webView.loadUrl("javascript: window['${callbackName}']('${data}', null)")
    }

    /*
     * Return an error result to Javascript
     */
    private fun errorResult(callbackName: String, error: String) {
        webView.loadUrl("javascript: window['${callbackName}'](null, '${error}')")
    }
}