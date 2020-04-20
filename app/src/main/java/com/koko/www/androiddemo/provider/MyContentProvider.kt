package com.koko.www.androiddemo.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import com.koko.www.androiddemo.data.UserRepository
import com.koko.www.androiddemo.db.UserDatabase
import com.koko.www.androiddemo.sqlite.WaitListContract
import com.koko.www.androiddemo.sqlite.WaitListContract.WaitListEntry.Companion.TABLE_NAME
import com.koko.www.androiddemo.utils.TestUtil.insertFakeData
import com.koko.www.androiddemo.sqlite.WaitListDbHelper
import com.koko.www.androiddemo.ui.UserViewModel


/**
 * 自定义内容提供者
 */
class MyContentProvider : ContentProvider() {

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun query(p0: Uri, p1: Array<out String>?, p2: String?, p3: Array<out String>?, p4: String?): Cursor? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getType(p0: Uri): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    /**
     * 初始化提供程序。创建提供程序后，Android 系统会立即调用此方法。
     * 请注意，只有在 ContentResolver 对象尝试访问您的提供程序时，系统才会创建它。
     */
    override fun onCreate(): Boolean {
        return true
    }


}