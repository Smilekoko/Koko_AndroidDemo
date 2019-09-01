package com.koko.opensourcedemo.kotlin.coroutines.dispatchers

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 组合上下文
 */
fun main() {
combiningContextElements()
}
//Sometimes we need to define multiple elements for a coroutine context.
//We can use the + operator for that.
//For example, we can launch a coroutine with an explicitly specified dispatcher and an explicitly specified name at the same time
fun combiningContextElements() = runBlocking<Unit> {
    //sampleStart
    launch(Dispatchers.Default + CoroutineName("test")) {
        println("I'm working in thread ${Thread.currentThread().name}")
    }
//sampleEnd
}