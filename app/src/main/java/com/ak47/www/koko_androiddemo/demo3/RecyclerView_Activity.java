package com.ak47.www.koko_androiddemo.demo3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ak47.www.koko_androiddemo.R;

public class RecyclerView_Activity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_demo3);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        myRecyclerViewAdapter = new MyRecyclerViewAdapter();
        mRecyclerView.setAdapter(myRecyclerViewAdapter);
    }
}
