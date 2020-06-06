package com.authguidance.mobilewebview.app;

import android.content.Context
import com.authguidance.mobilewebview.configuration.Configuration;
import com.authguidance.mobilewebview.configuration.ConfigurationLoader
import com.authguidance.mobilewebview.views.utilities.DeviceSecurity

/*
 * The view model class for the main activity
 */
public class MainActivityViewModel {

    // Global objects used by the main activity
    var configuration: Configuration? = null
    var exception: Throwable? = null

    // State used by the main activity
    var isInitialised: Boolean = false
    var isDeviceSecured: Boolean = false
    var isTopMost: Boolean = true

    /*
     * Do the main initialisation
     */
    fun initialise(context: Context) {

        // Reset state flags
        this.isInitialised = false
        this.isDeviceSecured = DeviceSecurity.isDeviceSecured(context)
        this.isTopMost = true

        // Load configuration
        this.configuration = ConfigurationLoader().load(context)

        // Indicate successful startup
        this.isInitialised = true
    }
}
