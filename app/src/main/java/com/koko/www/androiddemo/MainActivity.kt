package com.koko.www.androiddemo

import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.koko.www.androiddemo.jetpack.paging.WithoutPagingLabActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val intent = Intent()
        val component = ComponentName(this, WithoutPagingLabActivity::class.java)
        intent.component = component
        startActivity(intent)
    }

}

