package com.authguidance.mobileweb.plumbing.errors

import net.openid.appauth.AuthorizationException

/*
 * A class to manage error translation
 */
class ErrorHandler {

    /*
     * Return an error object starting the mobile app
     */
    fun fromStartupError(ex: Throwable): UIError {

        val error = UIError(
            "Startup",
            ErrorCodes.generalUIError,
            "A problem was encountered starting the mobile app"
        )

        this.updateFromException(ex, error)
        return error
    }

    /*
     * Return an error object from an exception
     */
    fun fromException(ex: Throwable): UIError {

        // Already handled
        if (ex is UIError) {
            return ex
        }

        val error = UIError(
            "Mobile UI",
            ErrorCodes.generalUIError,
            "A technical problem was encountered in the Mobile UI"
        )

        this.updateFromException(ex, error)
        return error
    }

    /*
     * Return an error to short circuit execution
     */
    fun fromLoginRequired(): UIError {

        return UIError(
            "Login",
            ErrorCodes.loginRequired,
            "A login is required so the API call was aborted"
        )
    }

    /*
     * Return an error to indicate that the Chrome custom tab window was closed
     */
    fun fromRedirectCancelled(): UIError {

        return UIError(
            "Redirect",
            ErrorCodes.redirectCancelled,
            "The login request was cancelled"
        )
    }

    /*
     * Handle errors triggering login requests
     */
    fun fromLoginRequestError(ex: Throwable, errorCode: String): UIError {

        // Already handled
        if (ex is UIError) {
            return ex
        }

        val error = UIError(
            "Login",
            errorCode,
            "A technical problem occurred during login processing"
        )

        if (ex is AuthorizationException) {
            this.updateFromAppAuthException(ex, error)
        } else {
            this.updateFromException(ex, error)
        }

        return error
    }

    /*
     * Handle errors processing login responses
     */
    fun fromLoginResponseError(ex: Throwable, errorCode: String): UIError {

        // Already handled
        if (ex is UIError) {
            return ex
        }

        val error = UIError(
            "Login",
            errorCode,
            "A technical problem occurred during login processing"
        )

        if (ex is AuthorizationException) {
            this.updateFromAppAuthException(ex, error)
        } else {
            this.updateFromException(ex, error)
        }

        return error
    }

    /*
     * Return an error to indicate that the Chrome custom tab window was closed
     */
    fun fromLogoutRequestError(ex: Throwable): UIError {

        // Already handled
        if (ex is UIError) {
            return ex
        }

        val error = UIError(
            "Logout",
            ErrorCodes.logoutRequestFailed,
            "A technical problem occurred during logout processing"
        )

        this.updateFromException(ex, error)
        return error
    }

    /*
     * Handle errors from the token endpoint
     */
    fun fromTokenError(ex: Throwable, errorCode: String): UIError {

        // Already handled
        if (ex is UIError) {
            return ex
        }

        val error = UIError(
            "Token",
            errorCode,
            "A technical problem occurred during token processing"
        )

        if (ex is AuthorizationException) {
            this.updateFromAppAuthException(ex, error)
        } else {
            this.updateFromException(ex, error)
        }

        return error
    }

    /*
     * Get details from the underlying exception
     */
    private fun updateFromException(ex: Throwable, error: UIError) {

        error.details = this.getErrorDescription(ex)
        error.stackTrace = ex.stackTrace
    }

    /*
     * Add AppAuth details to our standard error object
     */
    private fun updateFromAppAuthException(ex: AuthorizationException, error: UIError) {

        if (ex.code != 0) {

            val appAuthErrorType = when {
                ex.type == 1 -> {
                    "AUTHORIZATION"
                }
                ex.type == 2 -> {
                    "TOKEN"
                }
                else -> {
                    "GENERAL"
                }
            }

            error.appAuthCode = "$appAuthErrorType / ${ex.code}"
        }

        this.updateFromException(ex, error)
    }

    /*
     * Get the error message property's value
     */
    private fun getErrorDescription(ex: Throwable): String? {

        if (ex.message != null) {
            return ex.message
        } else {
            return ex.javaClass.simpleName
        }
    }
}
