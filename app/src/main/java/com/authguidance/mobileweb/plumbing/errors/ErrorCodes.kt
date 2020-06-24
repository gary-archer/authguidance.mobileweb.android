package com.authguidance.mobileweb.plumbing.errors

/*
 * Error codes that the UI can program against
 */
object ErrorCodes {

    // A general exception in the UI
    const val generalUIError = "mobile_ui_error"

    // Used to indicate that the API cannot be called until the user logs in
    const val loginRequired = "login_required"

    // Used to indicate that the Chrome Custom Tab was cancelled
    const val redirectCancelled = "redirect_cancelled"

    // A technical error starting a login request, such as contacting the metadata endpoint
    const val loginRequestFailed = "login_request_failed"

    // A technical error processing the login response containing the authorization code
    const val loginResponseFailed = "login_response_failed"

    // A technical error exchanging the authorization code for tokens
    const val authorizationCodeGrantFailed = "authorization_code_grant"

    // A technical problem during background token renewal
    const val tokenRenewalError = "token_renewal_error"

    // An error starting a logout request, such as contacting the metadata endpoint
    const val logoutRequestFailed = "logout_request_failed"
}
