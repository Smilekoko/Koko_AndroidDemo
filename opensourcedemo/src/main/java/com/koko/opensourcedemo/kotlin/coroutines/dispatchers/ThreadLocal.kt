package com.koko.opensourcedemo.kotlin.coroutines.dispatchers

import kotlinx.coroutines.*

/**
 * Thread-local data  局部线程数据
 * Sometimes it is convenient to have an ability to pass some thread-local data to or between coroutines.
 */
val threadLocal = ThreadLocal<String?>() // declare thread-local variable

fun main() {
    threadLocal()
}

fun threadLocal() = runBlocking<Unit> {
    //sampleStart
    threadLocal.set("main")
    println("Pre-main, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
    val job = launch(Dispatchers.Default + threadLocal.asContextElement(value = "launch")) {
        println("Launch start, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
        yield()
        println("After yield, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
        yield()
        println("After yield, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
    }
    job.join()
    println("Post-main, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
//sampleEnd
}