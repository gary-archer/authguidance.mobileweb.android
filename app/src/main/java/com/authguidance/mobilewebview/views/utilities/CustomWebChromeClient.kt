package com.authguidance.mobilewebview.views.utilities

import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient

/*
 * A custom web chrome client for HTML5 features
 */
class CustomWebChromeClient : WebChromeClient() {

    /*
     * During development, receive any console.log statements from the SPA's Javascript code
     */
    @Override
    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {

        println("MobileDebug: ${consoleMessage?.message()}")
        return super.onConsoleMessage(consoleMessage)
    }
}
