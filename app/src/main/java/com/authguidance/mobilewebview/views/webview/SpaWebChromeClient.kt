package com.authguidance.mobilewebview.views.webview

import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient

/*
 * A custom web chrome client for HTML5 features
 */
class SpaWebChromeClient: WebChromeClient() {

    @Override
    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {

        println("GJA SPA console message: ${consoleMessage?.message()}")
        return super.onConsoleMessage(consoleMessage)
    }
}