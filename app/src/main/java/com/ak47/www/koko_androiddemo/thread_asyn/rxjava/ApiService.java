package com.ak47.www.koko_androiddemo.thread_asyn.rxjava;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by 1796 on 2018/6/19.
 */

public interface ApiService {
    @GET("xiaohua.json")
    Observable<List<MyJoke>> getData();
}
