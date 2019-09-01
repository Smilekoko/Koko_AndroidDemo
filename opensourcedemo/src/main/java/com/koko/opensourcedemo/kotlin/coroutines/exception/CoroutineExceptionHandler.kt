package com.koko.opensourcedemo.kotlin.coroutines.exception

import kotlinx.coroutines.*

/**
 * 处理协程异常
 *  CoroutineExceptionHandler上下文元素用作catch协同程序的通用块，可以进行自定义日志记录或异常处理。
 */
fun main() {
    coroutineExceptionHandler()
}

fun coroutineExceptionHandler() = runBlocking {
    //sampleStart
    val handler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception")
    }
    val job = GlobalScope.launch(handler) {
        throw AssertionError()
    }
    val deferred = GlobalScope.async(handler) {
        throw ArithmeticException() // Nothing will be printed, relying on user to call deferred.await()
    }
    joinAll(job, deferred)
//sampleEnd
}