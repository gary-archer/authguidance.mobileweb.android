package com.authguidance.mobilewebview.plumbing.webview

/*
 * Mobile entry points called by the SPA
 */
interface JavascriptBridge {

    // Query the logged in state
    fun isLoggedIn(callbackName: String)

    // Try to get an access token
    fun getAccessToken(callbackName: String)

    // Try to refresh an access token
    fun refreshAccessToken(callbackName: String)

    // Perform a login redirect
    fun startLogin(callbackName: String)

    // Perform a logout redirect
    fun startLogout(callbackName: String)

    // For testing, make the access token act expired
    fun expireAccessToken(callbackName: String)

    // For testing, make the refresh token act expired
    fun expireRefreshToken(callbackName: String)
}
