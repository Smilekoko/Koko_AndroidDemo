package com.koko.www.androiddemo.component.lifecycleAware

import android.content.Context
import android.location.LocationListener
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

/**
 * 订阅生命周期的组件
 * object还有一个非常重要的作用，就是生成单例对象。
 */
object LifecycleAwareComponent {

    fun bindLifecycleAwareComponent(lifecycleOwner: LifecycleOwner, mGpsListener: LocationListener, context: Context) {

        MyObserver(lifecycleOwner, mGpsListener, context)
    }

    /**
     * 订阅生命周期
     */
    internal class MyObserver(lifecycleOwner: LifecycleOwner, mGpsListener: LocationListener, context: Context) : LifecycleObserver {


        init {
            lifecycleOwner.lifecycle.addObserver(this)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun connectListener() {
            //如果activity生命周期恢复执行的操纵
            Log.e("koko", "activity生命周期恢复了")
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun disconnectListener() {
            //如果activity生命周期暂停执行的操纵
            Log.e("koko", "activity生命周期暂停")
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun destroy() {
            Log.e("koko", "activity摧毁了")
        }
    }
}