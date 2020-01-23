package com.authguidance.mobilewebview.views.webview

import android.webkit.*

/*
 * A custom web view client to load our SPA
 */
class SpaWebViewClient : WebViewClient() {

    /*
     * Ensure that our SPA content loads within the web view
     */
    @Override
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        return false
    }

    @Override
    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?) {

        super.onReceivedError(view, request, error)

        if(error != null) {
            println("GJA SPA web view error: " + error.description)
        }
    }
}