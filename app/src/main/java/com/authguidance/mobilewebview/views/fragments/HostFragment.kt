package com.authguidance.mobilewebview.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.authguidance.mobilewebview.databinding.FragmentHostBinding
import com.authguidance.mobilewebview.views.webview.SpaWebViewClient
import com.authguidance.mobilewebview.plumbing.oauth.SpaAuthenticator
import com.authguidance.mobilewebview.views.webview.SpaWebChromeClient

/*
 * The host fragment for the web view
 */
class HostFragment : androidx.fragment.app.Fragment() {

    private lateinit var binding: FragmentHostBinding

    /*
     * Inflate the view
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

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
        this.binding.webview.settings.domStorageEnabled = true

        // Add a bridge so that the Javascript code can call us back
        this.binding.webview.addJavascriptInterface(SpaAuthenticator(), "mobileAuthenticator")

        // Create clients for debug purposes
        this.binding.webview.webViewClient = SpaWebViewClient()
        this.binding.webview.webChromeClient = SpaWebChromeClient()

        // Load our SPA's content
        this.binding.webview.loadUrl("https://web.authguidance-examples.com/spa")
    }
}
