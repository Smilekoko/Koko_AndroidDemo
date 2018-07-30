package com.ak47.www.koko_androiddemo.thread_asyn.rxjava;

import com.ak47.www.koko_androiddemo.net.retrofit.GitHubRepos;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 1796 on 2018/6/19.
 */

public class HttpMethods {
    private static final String BASE_URL = "http://api.laifudao.com/open/";
    private static final int TIME_OUT = 4;
    private Retrofit retrofit;
    public ApiService apiService;

    private HttpMethods() {
        /**
         * 构造函数私有化
         * 并在构造函数中进行retrofit的初始化
         */
        OkHttpClient client = new OkHttpClient();
        client.newBuilder().connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        /**
         * 由于retrofit底层的实现是通过okhttp实现的，所以可以通过okHttp来设置一些连接参数
         * 如超时等
         */
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                //负责把http请求json转换为java类，这里转换为MyJoke.class
                .addConverterFactory(GsonConverterFactory.create())
                //必须添加,否则http请求的无法转换为Observable类
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

    }

    private static class sinalInstance {
        static final HttpMethods instance = new HttpMethods();
    }

    public static HttpMethods getInstance() {
        return sinalInstance.instance;
    }

    public void getJoke(Observer<List<MyJoke>> observer) {


        apiService.getData()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


}
