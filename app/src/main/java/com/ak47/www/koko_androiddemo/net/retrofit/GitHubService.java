package com.ak47.www.koko_androiddemo.net.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by 1796 on 2018/5/30.
 */

public interface GitHubService {

    //该方法获取gitub用户的repository(仓库)的json
    @GET("users/{user}/repos")
    Call<List<GitHubRepos>> listRepos(@Path("user") String user);//@Path的作用就是替换路径中的{user}
}
