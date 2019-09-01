package com.koko.opensourcedemo.kotlin.coroutines.dispatchers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 不受限的调度器
 */
fun main() {
    unconfinedDispatchers()
}

/**
 * Dispatchers.Unconfined 协程调度器会在程序运行到第一个挂起点时，在调用者线程中启动
 * 挂起后，它将在挂起函数执行的线程中恢复，恢复的线程完全取决于该挂起函数在哪个线程执行。
 * 非受限调度器适合协程不消耗 CPU 时间也不更新任何限于特定线程的共享数据（如 UI）的情境。
 */
fun unconfinedDispatchers() = runBlocking<Unit> {
    //sampleStart
    //非受限的调度器是一种高级机制，可以在某些极端情况下提供帮助而不需要调度协程以便稍后执行或产生不希望的副作用，
    // 因为某些操作必须立即在协程中执行。 非受限调度器不应该被用在通常的代码中。
    launch(Dispatchers.Unconfined) {
        // not confined -- will work with main thread
        println("Unconfined      : I'm working in thread ${Thread.currentThread().name}")
        delay(500)
        println("Unconfined      : After delay in thread ${Thread.currentThread().name}")
    }
    launch {
        // context of the parent, main runBlocking coroutine
        println("main runBlocking: I'm working in thread ${Thread.currentThread().name}")
        delay(1000)
        println("main runBlocking: After delay in thread ${Thread.currentThread().name}")
    }
//sampleEnd
}
