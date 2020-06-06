package com.authguidance.mobilewebview.app

import androidx.lifecycle.ViewModel
import com.authguidance.mobilewebview.configuration.Configuration

/*
 * Details from the main activity that are shared with child fragments
 * This is done by the Android system using 'by viewModels()' and 'by activityViewModels()' calls
 */
class MainActivitySharedViewModel : ViewModel() {

    lateinit var configurationAccessor: () -> Configuration?
}
