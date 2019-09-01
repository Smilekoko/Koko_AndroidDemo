package com.koko.www.androiddemo.component.viewModel.liveData

import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

/**
 * LiveData的使用
 */
class LiveDataTimerViewModel : ViewModel() {
    private val oneSecond: Int = 1000
    private val mElapsedTime = MutableLiveData<Long>()
    private var mInitialTime: Long = 0

    fun getElapsedTime(): LiveData<Long> {
        return mElapsedTime
    }

    //初始化
    init {
        mInitialTime = SystemClock.elapsedRealtime()
        val timer = Timer()

        // Update the elapsed time every second.
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val newValue = (SystemClock.elapsedRealtime() - mInitialTime) / 1000
                // setValue() cannot be called from a background thread so post to main thread.
                mElapsedTime.postValue(newValue)
            }
        }, oneSecond.toLong(), oneSecond.toLong())
    }


}