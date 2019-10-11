package com.koko.www.androiddemo.jetpack.paging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.koko.www.androiddemo.R
import com.koko.www.androiddemo.model.Repo
import com.koko.www.androiddemo.ui.ReposAdapter
import com.koko.www.androiddemo.ui.SearchRepositoriesViewModel
import com.koko.www.androiddemo.utils.Injection
import kotlinx.android.synthetic.main.activity_paging_with_net.*

/**
 * 从网络中获取数据并分页处理，但是这个并没有使用jetpack的paging库
 */
class WithoutPagingLabActivity : AppCompatActivity() {

    private lateinit var viewModel: SearchRepositoriesViewModel
    private val adapter = ReposAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paging_with_net)

        // get the view model
        viewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(this))
                .get(SearchRepositoriesViewModel::class.java)

        // add dividers between RecyclerView's row items
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        list.addItemDecoration(decoration)
        //设置滑动到底请求更多数据的监听
        setupScrollListener()

        initAdapter()
        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        viewModel.searchRepo(query)
        initSearch(query)
    }

    private fun initSearch(query: String) {
        search_repo.setText(query)

        search_repo.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
        search_repo.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
    }

    /**
     * EditText输入来重置查询
     */
    private fun updateRepoListFromInput() {
        search_repo.text.trim().let {
            if (it.isNotEmpty()) {
                list.scrollToPosition(0)
                viewModel.searchRepo(it.toString())
                // viewModel.repos.observe(this, Observer<List<Repo>> {}，所以这里先清空adapter数据
                adapter.submitList(null)
            }
        }
    }

    /**
     * RecyclerView的适配器初始化，viewModel
     */
    private fun initAdapter() {
        list.adapter = adapter

        //LiveData观察数据变化，更新Adapter中数据
        viewModel.repos.observe(this, Observer<List<Repo>> {
            Log.d("Activity", "list: ${it?.size}")
            showEmptyList(it?.size == 0)

            //提交新的list到diffed
            adapter.submitList(it)
        })


        viewModel.networkErrors.observe(this, Observer<String> {
            Toast.makeText(this, "\uD83D\uDE28 Wooops $it", Toast.LENGTH_LONG).show()
        })
    }

    /**
     * 显示和隐藏列表
     */
    private fun showEmptyList(show: Boolean) {
        if (show) {
            emptyList.visibility = View.VISIBLE
            list.visibility = View.GONE
        } else {
            emptyList.visibility = View.GONE
            list.visibility = View.VISIBLE
        }
    }

    //当recyclerView滑动到底时，设置viewModel请求更多的数据
    private fun setupScrollListener() {
        val layoutManager = list.layoutManager as androidx.recyclerview.widget.LinearLayoutManager
        list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                viewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })
    }


    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = "Android"
    }
}
