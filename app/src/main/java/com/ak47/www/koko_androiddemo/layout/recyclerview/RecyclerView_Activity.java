package com.ak47.www.koko_androiddemo.layout.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ak47.www.koko_androiddemo.R;

public class RecyclerView_Activity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_demo3);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        //spanCount = 3：表示一横行显示3列
        mLayoutManager = new GridLayoutManager(this, 4);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            //表示该position的Item，占用该横行的几列
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);

        myRecyclerViewAdapter = new MyRecyclerViewAdapter();
        mRecyclerView.setAdapter(myRecyclerViewAdapter);
    }
}
