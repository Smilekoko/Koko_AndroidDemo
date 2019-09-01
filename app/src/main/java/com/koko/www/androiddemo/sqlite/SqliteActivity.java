package com.koko.www.androiddemo.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.koko.www.androiddemo.R;


public class SqliteActivity extends AppCompatActivity {
    private SQLiteDatabase database;
    private GuestListAdapter guestListAdapter;
    private EditText newGuestNameEditText, newPartySizeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqllite);
        RecyclerView waitlistRecyclerView;
        newGuestNameEditText = findViewById(R.id.person_name_edit_text);
        newPartySizeEditText = findViewById(R.id.party_count_edit_text);
        waitlistRecyclerView = this.findViewById(R.id.all_guests_list_view);
        waitlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create a DB helper (this will create the DB if run for the first time)
        WaitlistDbHelper waitlistDbHelper = new WaitlistDbHelper(this);

        // Keep a reference to the mDb until paused or killed. Get a writable database
        // because you will be adding restaurant customers
        database = waitlistDbHelper.getWritableDatabase();

        //Fill the database with fake data
        TestUtil.insertFakeData(database);
        // Create an adapter for that cursor to display the data
        Cursor cursor = getAllGuests();


        guestListAdapter = new GuestListAdapter(this, cursor);
        waitlistRecyclerView.setAdapter(guestListAdapter);

        /**
         * 为recycleView添加滑动删除
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                long id = (long) viewHolder.itemView.getTag();
                removeGuest(id);
                guestListAdapter.swapCursor(getAllGuests());
            }
        }).attachToRecyclerView(waitlistRecyclerView);

    }

    private Cursor getAllGuests() {
        return database.query(
                WaitlistContract.WaitlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                WaitlistContract.WaitlistEntry.COLUMN_TIMESTAMP
        );
    }

    public void addToWaitlist(View view) {
        if (newGuestNameEditText.getText().length() == 0 ||
                newPartySizeEditText.getText().length() == 0) {
            return;
        }
        int partySize = 1;
        try {

            partySize = Integer.parseInt(newPartySizeEditText.getText().toString());
        } catch (Exception e) {
            Log.e("TAG", e.getMessage());
        }
        addNewGuest(newGuestNameEditText.getText().toString(), partySize);

        guestListAdapter.swapCursor(getAllGuests());

        newPartySizeEditText.clearFocus();
        newGuestNameEditText.getText().clear();
        newPartySizeEditText.getText().clear();
    }

    private long addNewGuest(String name, int partySize) {
        ContentValues cv = new ContentValues();
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME, name);
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE, partySize);

        return database.insert(WaitlistContract.WaitlistEntry.TABLE_NAME, null, cv);
    }

    private boolean removeGuest(long id) {
        return database.delete(WaitlistContract.WaitlistEntry.TABLE_NAME,
                WaitlistContract.WaitlistEntry._ID + "=" + id, null) > 0;

    }
}
