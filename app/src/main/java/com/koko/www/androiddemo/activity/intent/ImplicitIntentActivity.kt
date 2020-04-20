package com.koko.www.androiddemo.activity.intent

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

import com.koko.www.androiddemo.R

import android.content.Intent.ACTION_DIAL


/**
 * 隐含Intent
 */
class ImplicitIntentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_implicit_intent)
        val button1 = findViewById<Button>(R.id.btn_DIAL)
        val button2 = findViewById<Button>(R.id.btn_web)
        val button3 = findViewById<Button>(R.id.btn_map)
        val button4 = findViewById<Button>(R.id.btn_result_StartActivity)
        button1.setOnClickListener {
            val number = Uri.parse("tel:5551234")
            val callIntent = Intent(ACTION_DIAL, number)
            startActivity(callIntent)
        }
        button2.setOnClickListener {
            val webPage = Uri.parse("http://www.android.com")
            val webIntent = Intent(Intent.ACTION_VIEW, webPage)
            this@ImplicitIntentActivity.startActivity(webIntent)
        }
        button3.setOnClickListener {
            // Build the intent
            val location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California")
            val mapIntent = Intent(Intent.ACTION_VIEW, location)

            // Verify it resolves
            val packageManager = packageManager
            val activities = packageManager.queryIntentActivities(mapIntent, 0)
            val isIntentSafe = activities.size > 0

            // Start an activity if it's safe
            if (isIntentSafe) {
                startActivity(mapIntent)
            }
        }

        button4.setOnClickListener {
            val pickContactIntent = Intent(Intent.ACTION_PICK)
            pickContactIntent.setDataAndType(Uri.parse("content://contacts"), ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE) // Show user only contacts w/ phone numbers
            startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST)
        }
    }

    /**
     * 当用户完成启动的Activity并返回时
     *
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        携带意图
     */
    @SuppressLint("ShowToast", "Recycle")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                // The user picked a contact.
                val contactUri = data!!.data
                // The Intent's data Uri identifies which contact was selected.
                val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)
                assert(contactUri != null)
                val cursor = contentResolver
                        .query(contactUri!!, projection, null, null, null)!!
                cursor.moveToFirst()
                val column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val number = cursor.getString(column)
                cursor.close()
                // Do something with the contact here (bigger example below)
                Log.e("TAG", number)
            }
        }
    }

    companion object {
        internal const val PICK_CONTACT_REQUEST = 1  // The request code
    }

}
