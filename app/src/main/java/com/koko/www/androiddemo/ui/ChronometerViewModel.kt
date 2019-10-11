package com.koko.www.androiddemo.ui

import androidx.lifecycle.ViewModel

/**
 * ViewModel使用基本数据类型
 * ViewModel是UI和数据的中介
 */
class ChronometerViewModel : ViewModel() {
    var startTime: Long? = null

    fun setStartTime(startTime: Long) {
        this.startTime = startTime
    }
}