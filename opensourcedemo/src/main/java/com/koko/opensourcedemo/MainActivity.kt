package com.koko.opensourcedemo

import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.koko.opensourcedemo.kotlin.coroutines.CoroutineScopeActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent()
        val component = ComponentName(this, CoroutineScopeActivity::class.java)
        intent.component = component
        startActivity(intent)
    }
}
