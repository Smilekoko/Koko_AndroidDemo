package com.koko.www.androiddemo.component.viewModel.liveData

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.koko.www.androiddemo.R

/**
 * LiveData的使用例子
 */
class LiveDataActivity : AppCompatActivity() {
    private lateinit var mLiveDataTimerViewModel: LiveDataTimerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_data)
//        val listView:ListView = findViewById(R.id.listView)
        mLiveDataTimerViewModel = ViewModelProviders.of(this).get(LiveDataTimerViewModel::class.java)

        subscribe()
    }

    //LiveDate可变的数据类型和ViewModel一起培和使用
    private fun subscribe() {
        //订阅可变的LiveDate数据类型

        //Observer：观察者 观察aLong值的变化，消耗LiveData的数据变化
        val elapsedTimeObserver = Observer<Long> { aLong ->
            val newText = this@LiveDataActivity.resources.getString(R
                    .string.seconds, aLong)
            (findViewById<View>(R.id.timer_textview) as TextView).text = newText
        }

        //将ViewModel中的LiveData和观察者关联
        //observe:观察
        mLiveDataTimerViewModel.getElapsedTime().observe(this, elapsedTimeObserver)
    }
}
