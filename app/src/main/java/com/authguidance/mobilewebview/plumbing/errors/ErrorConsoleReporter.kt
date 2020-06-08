package com.authguidance.mobilewebview.plumbing.errors

import android.content.Context

/*
 * A helper class to output error details to the console and avoid end users
 */
object ErrorConsoleReporter {

    /*
     * Output names and values
     */
    fun output(error: UIError, context: Context) {

        val lines = ErrorFormatter(context).getErrorLines(error)
        lines.forEach {
            println("MobileDebug Error: ${it.name} = ${it.value}")
        }
    }
}
