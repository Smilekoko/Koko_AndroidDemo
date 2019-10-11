package com.koko.opensourcedemo.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.koko.opensourcedemo.R
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.koko.opensourcedemo.retrofit.bean.RepositoryJsonBean
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
        val callRepos = gitHubService.jsonBeanListRepos("smilekoko")

//        https://api.github.com/users/smilekoko/repos
//        callRepos.enqueue(object : Callback<List<RepositoryBean>> {
//            override fun onResponse(call: Call<List<RepositoryBean>>, response: Response<List<RepositoryBean>>) {
//                val repositoryList = response.body()
//                if (repositoryList != null) {
//                    for (item in repositoryList)
//                        Log.e("koko", ":"+item.full_name)
//                }
//            }
//
//            override fun onFailure(call: Call<List<RepositoryBean>>, t: Throwable) {
//            }
//        })

        //使用Json来定义Bean
        callRepos.enqueue(object : Callback<List<RepositoryJsonBean>> {
            override fun onFailure(call: Call<List<RepositoryJsonBean>>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<List<RepositoryJsonBean>>, response: Response<List<RepositoryJsonBean>>) {
                val repositoryList = response.body()
                if (repositoryList != null) {
                    for (item in repositoryList) {
                        Log.e("koko", item.fullName)
                    }
                }
            }
        }
        )
    }
}
