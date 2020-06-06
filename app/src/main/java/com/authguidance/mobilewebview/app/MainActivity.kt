package com.authguidance.mobilewebview.app;

import android.app.admin.DevicePolicyManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.authguidance.mobilewebview.R
import com.authguidance.mobilewebview.databinding.ActivityMainBinding
import com.authguidance.mobilewebview.views.utilities.Constants

/*
 * Our Single Activity App's activity
 */
class MainActivity : AppCompatActivity() {

    // The binding contains our view model
    private lateinit var binding: ActivityMainBinding

    /*
     * Create the activity in a safe manner, to set up navigation and data binding
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create our view model
        val model = MainActivityViewModel()

        // Populate the shared view model used by child fragments
        this.createSharedViewModel(model)

        // Inflate the view, which will trigger child fragments to run
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        this.binding.model = model

        // Finally, do the main application load
        this.initialiseApp()
    }

    /*
     * Called from the device not secured fragment to prompt the user to set a PIN or password
     */
    fun openLockScreenSettings() {

        val intent = Intent(DevicePolicyManager.ACTION_SET_NEW_PASSWORD)
        this.startActivityForResult(intent, Constants.SET_LOCK_SCREEN_REQUEST_CODE)
        this.binding.model?.isTopMost = false
    }

    /*
     * Create or update a view model with data needed by child fragments
     */
    private fun createSharedViewModel(model: MainActivityViewModel) {

        // Get the model from the Android system, which will be created the first time
        val sharedViewModel: MainActivitySharedViewModel by viewModels()

        // Make configuration available to child fragments
        sharedViewModel.configurationAccessor = model::configuration
    }

    /*
     * Initialise the app and handle errors
     */
    private fun initialiseApp() {

        try {
            // Do the main view model initialisation
            this.binding.model!!.initialise(this.applicationContext)

        } catch (ex: Throwable) {

            // Store the error to display later
            println("GJA: startup exception")
            println(ex.message ?: "")
            this.binding.model!!.exception = ex
        }
    }
}
