package com.authguidance.mobilewebview.views.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.authguidance.mobilewebview.app.MainActivitySharedViewModel
import com.authguidance.mobilewebview.databinding.FragmentHostBinding
import com.authguidance.mobilewebview.plumbing.webview.AuthenticatorImpl
import com.authguidance.mobilewebview.views.utilities.CustomWebChromeClient
import com.authguidance.mobilewebview.views.utilities.CustomWebViewClient

/*
 * The host fragment for the web view
 */
class HostFragment : androidx.fragment.app.Fragment() {

    private lateinit var binding: FragmentHostBinding

    /*
     * Inflate the view
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        this.binding = FragmentHostBinding.inflate(inflater, container, false)
        return this.binding.root
    }

    /*
     * Initialise and load the web view
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Enable standard SPA HTML5 features to run in the web view
        this.binding.webview.settings.javaScriptEnabled = true

        // This makes window.mobileAuthenticator available to the SPA, so that it can call back the mobile app
        val authenticator = AuthenticatorImpl(this.binding.webview)
        this.binding.webview.addJavascriptInterface(authenticator, "mobileAuthenticator")

        // Customise clients to control debugging
        this.binding.webview.webViewClient = CustomWebViewClient()
        this.binding.webview.webChromeClient = CustomWebChromeClient()

        // Load our SPA's content, which will trigger calls back to the mobile app later
        val sharedViewModel: MainActivitySharedViewModel by activityViewModels()
        val webRootUrl = sharedViewModel.configurationAccessor()!!.app.webBaseUrl

        // Load our SPA's content, which will trigger calls back to the mobile app later
        this.binding.webview.loadUrl(webRootUrl)
    }

    /*
     * Clean up when destroyed
     */
    override fun onDestroyView() {
        super.onDestroyView()
        this.binding.webview.removeJavascriptInterface("mobileAuthenticator")
    }
}
