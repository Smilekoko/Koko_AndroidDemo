package com.koko.www.androiddemo.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import com.koko.www.androiddemo.sqlite.WaitListContract.WaitListEntry.Companion.COLUMN_GUEST_NAME
import com.koko.www.androiddemo.sqlite.WaitListContract.WaitListEntry.Companion.COLUMN_PARTY_SIZE
import com.koko.www.androiddemo.sqlite.WaitListContract.WaitListEntry.Companion.COLUMN_TIMESTAMP
import com.koko.www.androiddemo.sqlite.WaitListContract.WaitListEntry.Companion.ID
import com.koko.www.androiddemo.sqlite.WaitListContract.WaitListEntry.Companion.TABLE_NAME

class WaitListDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        //很容易忘记必要的空格 "CREATE TABLE "
        //很容易忘记分割的 ，
        //很容易忘记结尾 ；
        val sqlCreateWaitListTable = "CREATE TABLE " + TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_PARTY_SIZE + " INTEGER NOT NULL, " +
                COLUMN_GUEST_NAME + " TEXT NOT NULL, " +
                COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); "
        sqLiteDatabase.execSQL(sqlCreateWaitListTable)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(sqLiteDatabase)
    }

    companion object {
        private const val DATABASE_NAME = "waitList.db"
        private const val DATABASE_VERSION = 2
    }
}
