package com.authguidance.mobilewebview.views.utilities

import android.webkit.*

/*
 * A custom web view client to load our SPA
 */
class CustomWebViewClient : WebViewClient() {

    /*
     * Ensure that our SPA content loads within the web view and not in an external browser
     */
    @Override
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        return false
    }

    /*
     * Handle error responses from the web view
     */
    @Override
    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?) {

        super.onReceivedError(view, request, error)

        if(error != null) {
            println("GJA mobile: web view error: " + error.description)
        }
    }
}