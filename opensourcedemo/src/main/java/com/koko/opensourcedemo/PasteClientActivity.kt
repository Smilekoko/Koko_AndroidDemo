package com.koko.opensourcedemo

import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity




class PasteClientActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paste_client)
        val mClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        pasteText(mClipboardManager)
    }

    private fun pasteText(mClipboardManager: ClipboardManager) {
        var pasteData: String = ""
      if (mClipboardManager.hasPrimaryClip()&&
              mClipboardManager.primaryClipDescription!!.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)){
          val item = mClipboardManager.primaryClip!!.getItemAt(0)
          pasteData = item.text as String
      }
    }
}
