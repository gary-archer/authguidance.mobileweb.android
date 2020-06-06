package com.authguidance.mobilewebview.configuration

import android.content.Context
import com.authguidance.mobilewebview.R
import com.google.gson.Gson
import java.nio.charset.Charset
import okio.Buffer
import okio.buffer
import okio.source

/*
 * A helper class to load the application configuration
 */
class ConfigurationLoader {

    /*
     * Load configuration from the resource
     */
    fun load(context: Context): Configuration {

        // Get the raw resource
        val stream = context.resources.openRawResource(R.raw.mobile_config)
        val configSource = stream.source().buffer()

        // Read it as JSON text
        val configBuffer = Buffer()
        configSource.readAll(configBuffer)
        val configJson = configBuffer.readString(Charset.forName("UTF-8"))

        // Deserialize it into objects
        return Gson().fromJson(configJson, Configuration::class.java)
    }
}
