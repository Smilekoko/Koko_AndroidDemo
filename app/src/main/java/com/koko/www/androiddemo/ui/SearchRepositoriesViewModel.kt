package com.koko.www.androiddemo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.koko.www.androiddemo.data.GithubRepository
import com.koko.www.androiddemo.model.Repo
import com.koko.www.androiddemo.model.RepoSearchResult
import com.koko.www.androiddemo.jetpack.paging.WithoutPagingLabActivity

/**
 * ViewModel for the [WithoutPagingLabActivity] screen
 * The ViewModel works with the [GithubRepository] to get the data.
 */
class SearchRepositoriesViewModel(private val repository: GithubRepository) : ViewModel() {
    companion object {
        //触发加载更多数据的阀门
        private const val VISIBLE_THRESHOLD = 5
    }

    //请求String可变
    private val queryLiveData = MutableLiveData<String>()
    //Transformations：生命周期相关的延迟计算的工具类，隐形传递LiveData信息到observer's lifecycle
    //queryLiveData为源来返回一个LiveData<RepoSearchResult>
    private val repoResult: LiveData<RepoSearchResult> = Transformations.map(queryLiveData) {
        repository.search(it)
    }
    //repoResult为源分离成LiveData<List<Repo>>和 LiveData<String>
    val repos: LiveData<List<Repo>> = Transformations.switchMap(repoResult) { it.data }
    val networkErrors: LiveData<String> = Transformations.switchMap(repoResult) {
        it.networkErrors
    }


    /**
     * Search a repository based on a query string.
     * 提交查询字符串MutableLiveData<String>()
     */
    fun searchRepo(queryString: String) {
        queryLiveData.postValue(queryString)
    }

    /**
     * Get the last query value.
     */
    private fun lastQueryValue(): String? = queryLiveData.value


    /**
     * viewModel响应RecyclerView滑动到底数据更改
     */
    fun listScrolled(visibleItemCount: Int, lastVisibleItem: Int, totalItemCount: Int) {

        if (visibleItemCount + lastVisibleItem + VISIBLE_THRESHOLD >= totalItemCount) {
            val immutableQuery = lastQueryValue()
            if (immutableQuery != null) {
                repository.requestMore(immutableQuery)
            }
        }
    }



}