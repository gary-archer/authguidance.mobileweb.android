package com.authguidance.mobilewebview.plumbing.oauth

import android.webkit.JavascriptInterface

/*
 * The authenticator class called from Javascript
 */
class AuthenticatorImpl : Authenticator {

    /*
     * Get an access token
     */
    @JavascriptInterface
    override fun getAccessToken(input: String): String {

        println("GJA: Mobile authenticator received input: ${input}")
        return "INVALIDTOKEN"
    }
}