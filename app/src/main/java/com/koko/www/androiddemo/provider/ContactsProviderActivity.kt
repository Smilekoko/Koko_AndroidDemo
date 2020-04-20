package com.koko.www.androiddemo.provider

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import com.koko.www.androiddemo.R
import com.koko.www.androiddemo.model.Phone

class ContactsProviderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts_provider)

        getContacts(this)
    }

    companion object {
        //获取手机联系人
        private fun getContacts(context: Context) {
            //TODO 1:获得ContentResolver:与提供程序进行通信。
            val resolver = context.contentResolver
            //TODO 2:确定uri和要查询的字段:内容 URI 是用于在提供程序中标识数据的 URI。
            val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER)
            //TODO 3:执行查询并将结果遍历展现在ListView中和存储到本地的数据库中
            val cursor = resolver.query(uri, projection, null, null, null)
            val list = arrayListOf<Phone>()
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    val number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    list.add(Phone(name, number))
                    //将每条记录存储到自己的数据库中
                }
            }
            cursor?.close()
            //将list集合中的数据展现在ListView中，此处省略

            for (phone in list) {
                println(phone.name + ":" + phone.number)
            }
        }
    }
}
