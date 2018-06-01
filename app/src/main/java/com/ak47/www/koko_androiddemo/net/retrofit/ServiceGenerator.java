package com.ak47.www.koko_androiddemo.net.retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 1796 on 2018/6/1.
 * Retrofit对象及其构建器是所有请求的核心。将其分离到一个干净的类中。
 */

public class ServiceGenerator {


    private static final String BASE_URL = "https://api.github.com/";

    //用静态来节约内存
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();


    //定义泛型 T, S, 且S 继承 T
    public static <S> S createService(
            Class<S> serviceClass) {

        return retrofit.create(serviceClass);
    }
}
