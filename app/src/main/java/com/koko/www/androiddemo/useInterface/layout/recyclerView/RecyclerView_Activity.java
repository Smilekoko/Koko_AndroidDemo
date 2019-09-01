package com.koko.www.androiddemo.useInterface.layout.recyclerView;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.koko.www.androiddemo.R;

public class RecyclerView_Activity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recyclerview);
        mRecyclerView = findViewById(R.id.my_recycler_view);

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
