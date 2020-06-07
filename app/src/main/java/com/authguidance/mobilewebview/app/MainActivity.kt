package com.authguidance.mobilewebview.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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

        // Run the application startup logic, to load configuration
        this.initialiseApp()
    }

    /*
     * Create or update a view model with data needed by child fragments
     */
    private fun createSharedViewModel(model: MainActivityViewModel) {

        // Get the model from the Android system, which will be created the first time
        val sharedViewModel: MainActivitySharedViewModel by viewModels()

        // Make configuration available to child fragments
        sharedViewModel.configurationAccessor = model::configuration
        sharedViewModel.authenticatorAccessor = model::authenticator
    }

    /*
     * Initialise the app and handle errors
     */
    private fun initialiseApp() {

        this.binding.model!!.initialise(this.applicationContext)
    }

    /*
     * Handle the result from other activities, such as AppAuth or lock screen activities
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        // Handle login responses
        if (requestCode == Constants.LOGIN_REDIRECT_REQUEST_CODE) {
            this.binding.model!!.onFinishLogin(data)
        }

        // Handle logout responses and reset state
        else if (requestCode == Constants.LOGOUT_REDIRECT_REQUEST_CODE) {
            this.binding.model!!.onFinishLogout()
        }
    }
}
