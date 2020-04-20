package com.koko.opensourcedemo

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/**
 * 作为content provider 的客户端来测试
 */
class ProviderClientActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider_client)
        query()
    }

    private fun query() {
        val uri = Uri.parse("content://com.koko")
        val cursor = contentResolver.query(uri, arrayOf("guestName", "partySizes"), null, null, null)

        if (cursor != null) {
            while (cursor.moveToNext()) {
                val name = cursor.getString(cursor.getColumnIndex("guestName"))
                val number = cursor.getString(cursor.getColumnIndex("partySizes"))
                Log.e("koko", "guestName:$name ; partySizes$number")
            }
        }
        cursor?.close()
    }

}
