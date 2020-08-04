package com.authguidance.mobileweb.configuration

/*
 * OAuth configuration settings
 */
class OAuthConfiguration {

    // The authority base URL
    lateinit var authority: String

    // The identifier for our mobile app
    lateinit var clientId: String

    // The mobile HTTPS domain name
    lateinit var mobileBaseUrl: String

    // The interstitial page that receives the login response
    lateinit var loginRedirectPath: String

    // The interstitial page that receives the logout response
    lateinit var postLogoutRedirectPath: String

    // The deep linking path on which the app is invoked, after login
    lateinit var loginActivatePath: String

    // The deep linking path on which the app is invoked, after login
    lateinit var postLogoutActivatePath: String

    // The Authorization Server endpoint used for logouts
    lateinit var logoutEndpoint: String

    // OAuth scopes being requested, for use when calling APIs after login
    lateinit var scope: String
}
