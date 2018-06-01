package com.ak47.www.koko_androiddemo.net.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ak47.www.koko_androiddemo.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        simpleUse();


    }

    //简单实用
    public void simpleUse() {
        //第一步构建Retrofit
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.github.com/")
//                .addConverterFactory(
//                        GsonConverterFactory.create()
//                )
//                .build();

        //第二步创建接口类
        // GitHubService service = retrofit.create(GitHubService.class);


        //封装避免重复创建对象
        GitHubService service = ServiceGenerator.createService(GitHubService.class);

        //第三步接口类调用抽象方法，返回Call类
        //https://api.github.com/users/smilekoko/repos
        Call<List<GitHubRepos>> call = service.listRepos("smilekoko");

        call.enqueue(new Callback<List<GitHubRepos>>() {
            @Override
            public void onResponse(Call<List<GitHubRepos>> call, Response<List<GitHubRepos>> response) {
                List<GitHubRepos> gitHubRepos = response.body();
                if (gitHubRepos != null) {
                    for (GitHubRepos repos : gitHubRepos) {
                        Log.e("koko", "Smilekoko的仓库id:" + repos.getId() + "///"
                                + "Smilekoko的仓库名字:" + repos.getName());
                    }

                }
            }

            @Override
            public void onFailure(Call<List<GitHubRepos>> call, Throwable t) {

            }
        });
    }
}
