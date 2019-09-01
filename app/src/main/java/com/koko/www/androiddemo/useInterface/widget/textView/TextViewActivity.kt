package com.koko.www.androiddemo.useInterface.widget.textView

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.Html.fromHtml
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.widget.TextView
import com.koko.www.androiddemo.R

class TextViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_view)
        useHtml()
    }


    private fun useHtml() {
        val credits = findViewById<TextView>(R.id.credits)
        credits.text = fromHtml(getString(R.string.credits))
        //为TextView设置导航方法
        credits.movementMethod = LinkMovementMethod.getInstance()
    }

    @Suppress("DEPRECATION")
    private fun fromHtml(input: String): Spanned {
        //当前设备sdk版本>sdk 22 ,Android5.1
        //FROM_HTML_MODE_COMPACT 紧凑模式
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            fromHtml(input, Html.FROM_HTML_MODE_COMPACT)
        } else {
            // method deprecated at API 24.
            Html.fromHtml(input)
        }
    }
}
