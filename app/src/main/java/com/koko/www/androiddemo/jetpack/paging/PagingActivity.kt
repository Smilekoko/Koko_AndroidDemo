package com.koko.www.androiddemo.jetpack.paging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.koko.www.androiddemo.R
import com.koko.www.androiddemo.ui.adapter.CheeseAdapter
import com.koko.www.androiddemo.ui.CheeseViewHolder
import com.koko.www.androiddemo.ui.CheeseViewModel
import kotlinx.android.synthetic.main.activity_paging.*

/**
 * Paging的基本使用，从数据库获取数据
 */
class PagingActivity : AppCompatActivity() {
    private lateinit var cheeseViewModel: CheeseViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paging)

        cheeseViewModel = ViewModelProviders.of(this).get(CheeseViewModel::class.java)


        // Create adapter for the RecyclerView
        val adapter = CheeseAdapter()
        cheeseList.adapter = adapter

        // Subscribe the adapter to the ViewModel, so the items in the adapter are refreshed
        // when the list changes
        cheeseViewModel.allCheeses.observe(this, Observer(adapter::submitList))

        initAddButtonListener()
        initSwipeToDelete()
    }

    //设置RecyclerView滑动删除
    private fun initSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            // enable the items to swipe to the left or right
            override fun getMovementFlags(recyclerView: RecyclerView,
                                          viewHolder: RecyclerView.ViewHolder): Int =
                    makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean = false

            // When an item is swiped, remove the item via the view model. The list item will be
            // automatically removed in response, because the adapter is observing the live list.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (viewHolder as CheeseViewHolder).cheese?.let {
                    cheeseViewModel.remove(it)
                }
            }
        }).attachToRecyclerView(cheeseList)
    }

    private fun initAddButtonListener() {
        addButton.setOnClickListener {
            addCheese()
        }
    }

    private fun addCheese() {
        val newCheese = inputText.text.trim()
        if (newCheese.isNotEmpty()) {
            cheeseViewModel.insert(newCheese)
            inputText.setText("")
        }
    }

}
