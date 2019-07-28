package com.koko.opensourcedemo

import android.app.Application
import org.litepal.LitePal

class MyApplication :Application() {
    override fun onCreate() {
        super.onCreate()
        // 初始化
//        LitePal.initialize(this)
    }
}