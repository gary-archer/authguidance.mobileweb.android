package com.authguidance.mobileweb.app

import androidx.lifecycle.ViewModel
import com.authguidance.mobileweb.configuration.Configuration
import com.authguidance.mobileweb.plumbing.oauth.Authenticator

/*
 * Details from the main activity that are shared with child fragments
 * This is done by the Android system using 'by viewModels()' and 'by activityViewModels()' calls
 */
class MainActivitySharedViewModel : ViewModel() {

    lateinit var configurationAccessor: () -> Configuration?
    lateinit var authenticatorAccessor: () -> Authenticator?
}
