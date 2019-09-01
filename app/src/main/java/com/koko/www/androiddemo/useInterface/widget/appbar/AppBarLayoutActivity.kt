package com.koko.www.androiddemo.useInterface.widget.appbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.koko.www.androiddemo.R
import kotlinx.android.synthetic.main.activity_tool_bar.*

class AppBarLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_bar_layout)

        //基本使用
        val toolbar = toolbar
        toolbar.title = "这里是Title"
        toolbar.subtitle = "这里是副标题"
        toolbar.setLogo(R.drawable.android_blue)
        setSupportActionBar(toolbar)
    }
}
