package com.koko.opensourcedemo.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.koko.opensourcedemo.R
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.koko.opensourcedemo.retrofit.bean.RepositoryBean
import retrofit2.Call
import retrofit2.Response


class RetrofitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)


        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()


        getRepositoryListA(retrofit)

    }

    //异步请求
    private fun getRepositoryListA(retrofit: Retrofit) {
        val gitHubService = retrofit.create(GitHubService::class.java)
        val callRepos = gitHubService.listRepos("smilekoko")

        callRepos.enqueue(object : Callback<List<RepositoryBean>> {
            override fun onResponse(call: Call<List<RepositoryBean>>, response: Response<List<RepositoryBean>>) {
                val repositoryList = response.body()
                if (repositoryList != null) {
                    for (item in repositoryList)
                        Log.e("koko", ":"+item.full_name)
                }
            }

            override fun onFailure(call: Call<List<RepositoryBean>>, t: Throwable) {
            }
        })
    }
}
