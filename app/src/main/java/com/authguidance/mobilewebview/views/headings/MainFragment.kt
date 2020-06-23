package com.authguidance.mobilewebview.views.headings

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.authguidance.mobilewebview.app.MainActivitySharedViewModel
import com.authguidance.mobilewebview.databinding.FragmentMainBinding
import com.authguidance.mobilewebview.plumbing.interop.CustomWebChromeClient
import com.authguidance.mobilewebview.plumbing.interop.CustomWebViewClient
import com.authguidance.mobilewebview.plumbing.interop.JavascriptBridgeImpl

/*
 * The host fragment for the web view
 */
class MainFragment : androidx.fragment.app.Fragment() {

    private lateinit var binding: FragmentMainBinding

    /*
     * Inflate the view
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        this.binding = FragmentMainBinding.inflate(inflater, container, false)
        return this.binding.root
    }

    /*
     * Initialise and load the web view
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the shared view model supplied by the main activity
        val sharedViewModel: MainActivitySharedViewModel by activityViewModels()
        val configuration = sharedViewModel.configurationAccessor()
        val authenticator = sharedViewModel.authenticatorAccessor()
        if (configuration != null && authenticator != null) {

            val webRootUrl = configuration.app.webBaseUrl

            // Make a mobile bridge available to the SPA
            val bridge = JavascriptBridgeImpl(this.binding.webview, authenticator, this.context as Activity)
            this.binding.webview.addJavascriptInterface(bridge, "mobileBridge")

            // Set web view properties to enable interop and debugging
            this.binding.webview.settings.javaScriptEnabled = true
            this.binding.webview.webViewClient =
                CustomWebViewClient()
            this.binding.webview.webChromeClient =
                CustomWebChromeClient()

            // Load our SPA's content, which will trigger OAuth calls back to the mobile app later
            this.binding.webview.loadUrl(webRootUrl)
        }
    }

    /*
     * Clean up when destroyed
     */
    override fun onDestroyView() {
        super.onDestroyView()
        this.binding.webview.removeJavascriptInterface("mobileBridge")
    }
}
