package com.koko.opensourcedemo.retrofit

import com.koko.opensourcedemo.retrofit.bean.RepositoryBean
import com.koko.opensourcedemo.retrofit.bean.RepositoryJsonBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path




interface GitHubService {

    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Call<List<RepositoryBean>>

    @GET("users/{user}/repos")
    fun jsonBeanListRepos(@Path("user") user: String): Call<List<RepositoryJsonBean>>
}