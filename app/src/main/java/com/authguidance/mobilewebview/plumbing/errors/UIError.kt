package com.authguidance.mobilewebview.plumbing.errors

import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Locale

/*
 * An error entity for the UI
 */
class UIError(
    var area: String,
    val errorCode: String,
    userMessage: String
) : RuntimeException(userMessage) {

    var utcTime: String
    var appAuthCode: String? = ""
    var details: String? = ""

    /*
     * Initialise state
     */
    init {

        val formatter = SimpleDateFormat("MMM dd yyyy HH:mm", Locale.ROOT)
        this.utcTime = formatter.format(System.currentTimeMillis())
    }

    /*
     * Serialize the error based on data
     */
    fun toJson(): String {

        val data = JSONObject()
        data.put("area", this.area)
        data.put("errorCode", this.errorCode)
        data.put("userMessage", this.message)

        if (!this.appAuthCode.isNullOrBlank()) {
            data.put("appAuthCode", this.appAuthCode)
        }

        if (!this.details.isNullOrBlank()) {
            data.put("details", this.details)
        }

        val frames = this.stackTrace
        if (frames.isNotEmpty()) {
            val framesArray = JSONArray()
            for (frame in frames) {
                framesArray.put(frame.toString())
            }
            data.put("stack", framesArray)
        }

        return data.toString()
    }
}
