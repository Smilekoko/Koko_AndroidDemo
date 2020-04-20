package com.koko.www.androiddemo.utils

import android.content.ContentValues
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.koko.www.androiddemo.sqlite.WaitListContract
import com.koko.www.androiddemo.sqlite.WaitListContract.WaitListEntry.Companion.COLUMN_GUEST_NAME
import com.koko.www.androiddemo.sqlite.WaitListContract.WaitListEntry.Companion.COLUMN_PARTY_SIZE

import java.util.ArrayList

object TestUtil {

    fun insertFakeData(db: SQLiteDatabase?) {
        if (db == null) {
            return
        }
        //create a list of fake guests
        val list = ArrayList<ContentValues>()

        var cv = ContentValues()
        cv.put(COLUMN_GUEST_NAME, "John")
        cv.put(COLUMN_PARTY_SIZE, 12)
        list.add(cv)

        cv = ContentValues()
        cv.put(COLUMN_GUEST_NAME, "Tim")
        cv.put(COLUMN_PARTY_SIZE, 2)
        list.add(cv)

        cv = ContentValues()
        cv.put(COLUMN_GUEST_NAME, "Jessica")
        cv.put(COLUMN_PARTY_SIZE, 99)
        list.add(cv)

        cv = ContentValues()
        cv.put(COLUMN_GUEST_NAME, "Larry")
        cv.put(COLUMN_PARTY_SIZE, 1)
        list.add(cv)

        cv = ContentValues()
        cv.put(COLUMN_GUEST_NAME, "Kim")
        cv.put(COLUMN_PARTY_SIZE, 45)
        list.add(cv)

        //insert all guests in one transaction
        try {
            db.beginTransaction()
            //clear the table first
            db.delete(WaitListContract.WaitListEntry.TABLE_NAME, null, null)
            //go through the list and add one by one
            for (c in list) {
                db.insert(WaitListContract.WaitListEntry.TABLE_NAME, null, c)
            }
            db.setTransactionSuccessful()
        } catch (e: SQLException) {
            //too bad :(
        } finally {
            db.endTransaction()
        }

    }
}
