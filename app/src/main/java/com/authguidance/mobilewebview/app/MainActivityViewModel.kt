package com.authguidance.mobilewebview.app

import android.content.Context
import com.authguidance.mobilewebview.configuration.Configuration
import com.authguidance.mobilewebview.configuration.ConfigurationLoader
import com.authguidance.mobilewebview.plumbing.oauth.AuthenticatorImpl

/*
 * The view model class for the main activity
 */
public class MainActivityViewModel {

    // Global objects used by the main activity
    var configuration: Configuration? = null
    var authenticator: AuthenticatorImpl? = null
    var exception: Throwable? = null

    // State used by the main activity
    var isInitialised: Boolean = false

    /*
     * Do the main initialisation
     */
    fun initialise(context: Context) {

        // Reset state flags
        this.isInitialised = false

        // Load configuration and create the authenticator object
        this.configuration = ConfigurationLoader().load(context)
        this.authenticator = AuthenticatorImpl(this.configuration!!.oauth, context)

        // Indicate successful startup
        this.isInitialised = true
    }
}
