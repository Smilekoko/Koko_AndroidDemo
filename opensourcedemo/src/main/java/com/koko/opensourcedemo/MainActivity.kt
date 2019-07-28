package com.koko.opensourcedemo

import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.koko.opensourcedemo.retrofit.RetrofitActivity
import kotlin.jvm.java as java1

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent()
        val component = ComponentName(this, RetrofitActivity::class.java1)
        intent.component = component
        startActivity(intent)
    }
}
