package com.authguidance.mobilewebview.plumbing.oauth

import android.webkit.JavascriptInterface

/*
 * The authenticator class called from Javascript
 */
class SpaAuthenticator {

    /*
     * Get an access token
     */
    @JavascriptInterface
    fun getAccessToken(info: String): String? {

        println("GJA: SPA Authenticator called: ${info}")
        return "INVALIDTOKEN"
    }
}