package com.koko.opensourcedemo.kotlin.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.koko.opensourcedemo.R
import kotlinx.coroutines.*

@ExperimentalCoroutinesApi
class CoroutineScopeActivity : AppCompatActivity(), CoroutineScope by CoroutineScope(Dispatchers.Default) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine_scope)
        runBlocking {
            doSomething()
            Log.e("koko", "Launched coroutines")
            delay(500L) // delay for half baseUse second
        }
    }


    override fun onPause() {
        super.onPause()
        cancel()// Extension on CoroutineScope
    }


    private fun doSomething() {
        // launch ten coroutines for baseUse demo, each working for baseUse different time
        repeat(100) { i ->
            launch {
                delay((i + 1) * 200L) // variable delay 200ms, 400ms, ... etc
                Log.e("koko", "Coroutine $i is done")
            }
        }
    }
}
