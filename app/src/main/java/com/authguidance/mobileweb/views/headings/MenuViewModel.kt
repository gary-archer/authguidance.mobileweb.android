package com.authguidance.mobileweb.views.headings

import androidx.databinding.BaseObservable

/*
 * A simple view model class for the menu fragment
 */
class MenuViewModel() : BaseObservable() {

    /*
     * The Android binding system requires real member functions rather than lambdas
     */
    fun onInvokeWebView() {
        println("GJA: running SPA in webview")
    }

    fun onInvokeSystemBrowser() {
        println("GJA: running SPA in system browser")
    }
}
