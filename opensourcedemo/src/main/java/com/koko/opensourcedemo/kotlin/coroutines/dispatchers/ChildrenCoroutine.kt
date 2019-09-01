package com.koko.opensourcedemo.kotlin.coroutines.dispatchers

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 子协程
 */
fun main() {
    childrenCoroutine()
}

//当一个协程被其它协程在 CoroutineScope 中启动的时候，
// 它将通过 CoroutineScope.coroutineContext 来承袭上下文，并且这个新协程的 Job 将会成为父协程作业的 子 作业。
// 当一个父协程被取消的时候，所有它的子协程也会被递归的取消。
fun childrenCoroutine() = runBlocking<Unit> {
    //sampleStart
    //However, when GlobalScope is used to launch a coroutine, there is no parent for the job of the new coroutine.
    // It is therefore not tied to the scope it was launched from and operates independently.
    val request = launch {
        // it spawns two other jobs, one with GlobalScope
        GlobalScope.launch {
            println("job1: I run in GlobalScope and execute independently!")
            delay(1000)
            println("job1: I am not affected by cancellation of the request")
        }
        // and the other inherits the parent context
        launch {
            delay(100)
            println("job2: I am a child of the request coroutine")
            delay(1000)
            println("job2: I will not execute this line if my parent request is cancelled")
        }
    }
    delay(500)
    request.cancel() // cancel processing of the request
    delay(1000) // delay a second to see what happens
    println("main: Who has survived request cancellation?")
//sampleEnd
}