package com.koko.www.androiddemo.useInterface.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.SearchView
import com.koko.www.androiddemo.R

class SearchableActivity : AppCompatActivity() {
    private lateinit var searchView: SearchView
    private lateinit var mListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchable)
        searchView = findViewById(R.id.searchView)
        mListView = findViewById(R.id.listView)




        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {

                return true
            }
        })

    }

}


