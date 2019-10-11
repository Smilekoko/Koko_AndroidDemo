package com.koko.www.androiddemo.useInterface.recyclerView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.koko.www.androiddemo.R

class RecyclerViewActivity : AppCompatActivity() {

    private var mRecyclerView: RecyclerView? = null
    private var mLayoutManager: LinearLayoutManager? = null
    private var myRecyclerViewAdapter: ListAdapter? = null
    private var myMultiItemAdapter: MultiItemAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_recyclerview)
        mRecyclerView = findViewById(R.id.my_recycler_view)

        initAdapter()

        upDate()

    }

    private fun initAdapter() {
        // in content do not change the layout size of the RecyclerView
        mRecyclerView!!.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(this)
        mRecyclerView!!.layoutManager = mLayoutManager

        //添加item分界线
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        mRecyclerView!!.addItemDecoration(decoration)

        //使用ListAdapter
//        myRecyclerViewAdapter = ListAdapter()
        //使用多种布局Adapter
        myMultiItemAdapter = MultiItemAdapter()

        mRecyclerView!!.adapter = myMultiItemAdapter
    }

    private fun upDate() {
        myRecyclerViewAdapter?.notifyDataSetChanged()
    }
}
