package com.koko.www.androiddemo.useInterface.webView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.koko.www.androiddemo.R

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        baseUse()

    }

    private fun baseUse() {
        val myWebView: WebView = findViewById<WebView>(R.id.webview)
        myWebView.loadUrl("https://www.bilibili.com/")
    }

}
