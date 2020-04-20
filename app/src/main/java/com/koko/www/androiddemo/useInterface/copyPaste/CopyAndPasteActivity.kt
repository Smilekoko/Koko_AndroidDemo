package com.koko.www.androiddemo.useInterface.copyPaste

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.koko.www.androiddemo.R
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context


class CopyAndPasteActivity : AppCompatActivity() {

    private lateinit var buttonCopyText: Button
    private lateinit var buttonCopyURI: Button
    private lateinit var buttonCopyIntent: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_copy_and_paste)
        val mClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        buttonCopyText = findViewById(R.id.buttonCopyText)
        buttonCopyText.setOnClickListener {
            val clipData = ClipData.newPlainText("copy from demo", "hello I'm koko")
            mClipboardManager.setPrimaryClip(clipData)
        }
        buttonCopyURI = findViewById(R.id.buttonCopyURI)
        buttonCopyURI.setOnClickListener {

        }
        buttonCopyIntent = findViewById(R.id.buttonCopyIntent)
        buttonCopyIntent.setOnClickListener {

        }
    }
}
