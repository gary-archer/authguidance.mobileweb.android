package com.authguidance.mobilewebview.plumbing.webview

import android.webkit.JavascriptInterface
import com.authguidance.mobilewebview.plumbing.webview.Authenticator

/*
 * The authenticator class called from Javascript
 */
class AuthenticatorImpl : Authenticator {

    /*
     * Get an access token
     */
    @JavascriptInterface
    override suspend fun getAccessToken(callbackName: String): String {

        println("GJA: Mobile authenticator received callback name: ${callbackName}")

        return "INVALIDTOKEN"
    }
}