package com.authguidance.mobileweb.app

import android.content.Context
import android.content.Intent
import com.authguidance.mobileweb.configuration.Configuration
import com.authguidance.mobileweb.configuration.ConfigurationLoader
import com.authguidance.mobileweb.plumbing.events.LoginCompletedEvent
import com.authguidance.mobileweb.plumbing.events.LogoutCompletedEvent
import com.authguidance.mobileweb.plumbing.oauth.AuthenticatorImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

/*
 * The view model class for the main activity
 */
public class MainActivityViewModel {

    // A flag to record whether we initialised correctly
    var isInitialised = false

    // Global objects
    var configuration: Configuration? = null
    var authenticator: AuthenticatorImpl? = null

    /*
     * Load configuration and create global objects
     */
    fun initialise(context: Context) {

        // Reset state flags
        this.isInitialised = false

        // Load configuration and create global objects
        this.configuration = ConfigurationLoader().load(context)
        this.authenticator = AuthenticatorImpl(this.configuration!!.oauth, context)

        // Indicate successful startup
        this.isInitialised = true
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
