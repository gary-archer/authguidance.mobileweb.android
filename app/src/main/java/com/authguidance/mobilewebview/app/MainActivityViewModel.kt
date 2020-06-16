package com.authguidance.mobilewebview.app

import android.content.Context
import android.content.Intent
import com.authguidance.mobilewebview.configuration.Configuration
import com.authguidance.mobilewebview.configuration.ConfigurationLoader
import com.authguidance.mobilewebview.plumbing.events.LoginCompletedEvent
import com.authguidance.mobilewebview.plumbing.events.LogoutCompletedEvent
import com.authguidance.mobilewebview.plumbing.oauth.AuthenticatorImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

/*
 * The view model class for the main activity
 */
public class MainActivityViewModel {

    // Global objects
    var configuration: Configuration? = null
    var authenticator: AuthenticatorImpl? = null

    /*
     * Load configuration and create global objects
     */
    fun initialise(context: Context) {
        this.configuration = ConfigurationLoader().load(context)
        this.authenticator = AuthenticatorImpl(this.configuration!!.oauth, context)
    }

    /*
     * Complete login processing
     */
    fun onFinishLogin(responseIntent: Intent?) {

        if (responseIntent == null) {
            return
        }

        // Switch to a background thread to perform the code exchange
        CoroutineScope(Dispatchers.IO).launch {

            val that = this@MainActivityViewModel
            try {

                // Exchange the authorization code for tokens
                that.authenticator!!.finishLogin(responseIntent)

                // Raise a successful completion event
                EventBus.getDefault().post(LoginCompletedEvent(null))

            } catch (ex: Throwable) {

                // Raise a failed completion event
                EventBus.getDefault().post(LoginCompletedEvent(ex))
            }
        }
    }

    /*
     * Complete logout processing
     */
    fun onFinishLogout() {

        // Complete OAuth processing
        this.authenticator!!.finishLogout()

        // Raise a completion event
        EventBus.getDefault().post(LogoutCompletedEvent())
    }
}
