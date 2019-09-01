package com.koko.opensourcedemo.kotlin.coroutines.dispatchers

import kotlinx.coroutines.*

/**
 * 协程中调度器和线程的基本示例
 */


@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
fun main() {
    dispatchersAndThreads()
}


@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
fun dispatchersAndThreads() = runBlocking<Unit> {
    //sampleStart
    //当调用 launch { …… } 时不传参数，它从启动了它的 CoroutineScope 中承袭了上下文（以及调度器）。
    launch {
        // context of the parent, main runBlocking coroutine
        println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
    }
    launch(Dispatchers.Unconfined) {
        // not confined -- will work with main thread
        println("Unconfined            : I'm working in thread ${Thread.currentThread().name}")
    }
    launch(Dispatchers.Default) {
        // will get dispatched to DefaultDispatcher
        println("Default               : I'm working in thread ${Thread.currentThread().name}")
    }
    launch(newSingleThreadContext("MyOwnThread")) {
        // will get its own new thread
        println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
    }
//sampleEnd
}