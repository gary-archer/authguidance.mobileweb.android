package com.authguidance.mobilewebview.views.activities;

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.authguidance.mobilewebview.R

/*
 * Our Single Activity App's activity
 */
class MainActivity : AppCompatActivity() {

    /*
     * Create the activity in a safe manner, to set up navigation and data binding
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
