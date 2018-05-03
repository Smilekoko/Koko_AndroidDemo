package com.ak47.www.koko_androiddemo.layout.swiperefreshLayout;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ak47.www.koko_androiddemo.R;

public class SwipeRefreshLayout_Activity extends AppCompatActivity {
    ListView mListView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_demo4);
        mListView = (ListView) findViewById(R.id.list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.query_suggestions));
        mListView.setAdapter((ListAdapter) mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
    }

    private void refreshContent() {
        mAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.cat_names));
        mListView.setAdapter((ListAdapter) mAdapter);
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
