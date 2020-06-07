package com.authguidance.mobilewebview.plumbing.errors

import java.text.SimpleDateFormat
import java.util.Locale

/*
 * An error entity for the UI
 */
class UIError(
    var area: String,
    val errorCode: String,
    val userMessage: String
) : RuntimeException(userMessage) {

    var utcTime: String
    var appAuthCode: String
    var details: String?

    /*
     * Initialise fields during construction
     */
    init {

        // Give fields their default values
        this.appAuthCode = ""
        this.details = ""

        // Format the current time
        val formatter = SimpleDateFormat("MMM dd yyyy HH:mm", Locale.ROOT)
        this.utcTime = formatter.format(System.currentTimeMillis())
    }
}
