package com.koko.www.androiddemo.sqlite

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper
import android.widget.EditText

import com.koko.www.androiddemo.R
import com.koko.www.androiddemo.sqlite.WaitListContract.WaitListEntry.Companion.COLUMN_GUEST_NAME
import com.koko.www.androiddemo.sqlite.WaitListContract.WaitListEntry.Companion.COLUMN_PARTY_SIZE
import com.koko.www.androiddemo.utils.TestUtil
import com.koko.www.androiddemo.sqlite.WaitListContract.WaitListEntry.Companion.COLUMN_TIMESTAMP
import com.koko.www.androiddemo.sqlite.WaitListContract.WaitListEntry.Companion.ID
import com.koko.www.androiddemo.sqlite.WaitListContract.WaitListEntry.Companion.TABLE_NAME

/**
 * 目前用Room组件来操作数据库了
 */
class SqliteActivity : AppCompatActivity() {
    private var database: SQLiteDatabase? = null
    private var guestListAdapter: GuestListAdapter? = null
    private var newGuestNameEditText: EditText? = null
    private var newPartySizeEditText: EditText? = null

    private val allGuests: Cursor
        get() = database!!.query(
                TABLE_NAME, null, null, null, null, null,
                COLUMN_TIMESTAMP
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sqllite)
        val waitListRecyclerView: RecyclerView = this.findViewById(R.id.all_guests_list_view)
        newGuestNameEditText = findViewById(R.id.person_name_edit_text)
        newPartySizeEditText = findViewById(R.id.party_count_edit_text)
        waitListRecyclerView.layoutManager = LinearLayoutManager(this)

        // Create a DB helper (this will create the DB if run for the first time)
        val waitListDbHelper = WaitListDbHelper(this)

        // Keep a reference to the mDb until paused or killed. Get a writable database
        // because you will be adding restaurant customers
        database = waitListDbHelper.writableDatabase


        //Fill the database with fake data
        TestUtil.insertFakeData(database)
        // Create an adapter for that cursor to display the data
        val cursor = allGuests


        guestListAdapter = GuestListAdapter(this, cursor)
        waitListRecyclerView.adapter = guestListAdapter

        /**
         * 为recycleView添加滑动删除
         */
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val id = viewHolder.itemView.tag as Long
                removeGuest(id)
                guestListAdapter!!.swapCursor(allGuests)
            }
        }).attachToRecyclerView(waitListRecyclerView)

    }

    fun addToWaitList(view: View) {
        if (newGuestNameEditText!!.text.isEmpty() || newPartySizeEditText!!.text.isEmpty()) {
            return
        }
        Log.e("koko", "插入的数据都不为空")
        val partySize = Integer.parseInt(newPartySizeEditText!!.text.toString())

        addNewGuest(newGuestNameEditText!!.text.toString(), partySize)

        guestListAdapter!!.swapCursor(allGuests)

        newPartySizeEditText!!.clearFocus()
        newGuestNameEditText!!.text.clear()
        newPartySizeEditText!!.text.clear()
    }

    private fun addNewGuest(name: String, partySize: Int): Long {
        val cv = ContentValues()
        cv.put(COLUMN_GUEST_NAME, name)
        cv.put(COLUMN_PARTY_SIZE, partySize)

        return database!!.insert(TABLE_NAME, null, cv)
    }

    private fun removeGuest(id: Long): Boolean {
        return database!!.delete(TABLE_NAME,
                "$ID=$id", null) > 0
    }
}
