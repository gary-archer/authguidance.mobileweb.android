package com.authguidance.mobilewebview.plumbing.errors

/*
 * A simple field value pair to be shown on a line
 */
data class ErrorLine(

    // The label
    val name: String,

    // The formatted value
    val value: String?
)
