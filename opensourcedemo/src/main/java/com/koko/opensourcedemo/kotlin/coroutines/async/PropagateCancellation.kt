package com.koko.opensourcedemo.kotlin.coroutines.async

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 *取消始终通过协程的层次结构来进行传递
 */
fun main() {
    propagateCancellation()
}

fun propagateCancellation() = runBlocking<Unit> {
    try {
        failedConcurrentSum()
    } catch (e: ArithmeticException) {
        println("Computation failed with ArithmeticException")
    }
}

//当第一个子协程失败的时候第一个 async 是如何等待父线程被取消的：
suspend fun failedConcurrentSum() = coroutineScope {
    val one = async {
        try {
            delay(Long.MAX_VALUE) // Emulates very long computation
            42
        } finally {
            println("First child was cancelled")
        }
    }
    val two = async<Int> {
        println("Second child throws an exception")
        throw ArithmeticException()
    }
    one.await() + two.await()
}
