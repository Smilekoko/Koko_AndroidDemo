package com.koko.opensourcedemo.okHttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.koko.opensourcedemo.R
import com.koko.opensourcedemo.utils.ioThread
import okhttp3.OkHttpClient
import okhttp3.Request


/**
 * use OkHttp
 * github:  https://github.com/square/okhttp
 * Document:    https://square.github.io/okhttp/
 * 由于文档写的清晰直接从4.x版本开始，因为从此版本将OkHttp升级到Kotlin。
 */
class OkHttpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ok_http)
        ioThread { getUrl() }

    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun getUrl() {
        val client = OkHttpClient()
        val url = "https://square.github.io/okhttp/"
        val request = Request.Builder()
                .url(url)
                .build()
        val responseBody = client.newCall(request).execute().body?.string()
        Log.e("koko", responseBody)
    }
}
