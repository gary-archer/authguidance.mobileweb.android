package com.authguidance.mobilewebview.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.authguidance.mobilewebview.R
import com.authguidance.mobilewebview.databinding.ActivityMainBinding
import com.authguidance.mobilewebview.plumbing.events.LoginCompletedEvent
import com.authguidance.mobilewebview.plumbing.events.LogoutCompletedEvent
import com.authguidance.mobilewebview.views.utilities.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

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

        try {
            // Do the main view model initialisation
            this.binding.model!!.initialise(this.applicationContext)

        } catch (ex: Throwable) {

            // Store the error to display later
            println("MobileDebug: startup exception: ${ex.message}")
            this.binding.model!!.exception = ex
        }
    }

    /*
     * Handle the result from other activities, such as AppAuth or lock screen activities
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        // Handle login responses
        if (requestCode == Constants.LOGIN_REDIRECT_REQUEST_CODE) {
            this.onFinishLogin(data)
        }

        // Handle logout responses and reset state
        else if (requestCode == Constants.LOGOUT_REDIRECT_REQUEST_CODE) {
            this.onFinishLogout()
        }
    }

    /*
     * Complete login processing
     */
    private fun onFinishLogin(responseIntent: Intent?) {

        if (responseIntent == null) {
            return
        }

        // Switch to a background thread to perform the code exchange
        CoroutineScope(Dispatchers.IO).launch {

            val that = this@MainActivity
            try {

                // Exchange the authorization code for tokens
                that.binding.model!!.authenticator!!.finishLogin(responseIntent)

                // Raise a successful completion event
                EventBus.getDefault().post(LoginCompletedEvent(error = ""))

            } catch (ex: Throwable) {

                // Report errors
                val errorText = ex.message ?: ""
                println("MobileDebug: finishLogin error: $errorText")

                // Raise a failed completion event
                EventBus.getDefault().post(LoginCompletedEvent(error = errorText))
            }
        }
    }

    /*
     * Complete logout processing
     */
    private fun onFinishLogout() {

        // Complete OAuth processing
        this.binding.model!!.authenticator!!.finishLogout()

        // Raise a completion event
        EventBus.getDefault().post(LogoutCompletedEvent())
    }
}
