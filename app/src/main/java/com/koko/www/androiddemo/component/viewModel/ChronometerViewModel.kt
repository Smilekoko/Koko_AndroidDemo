package com.koko.www.androiddemo.component.viewModel

import androidx.lifecycle.ViewModel

/**
 * ViewModel使用基本数据类型
 */
class ChronometerViewModel : ViewModel() {
    var startTime: Long? = null

    fun setStartTime(startTime: Long) {
        this.startTime = startTime
    }
}