@file:Suppress("UNREACHABLE_CODE", "IMPLICIT_NOTHING_AS_TYPE_PARAMETER")

package com.koko.opensourcedemo.kotlin.coroutines.exception

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 协程中异常的传递
 */
fun main() {
    exceptionPropagation()
}

//协程构建器有两种风格：自动的传播异常（launch 以及 actor）对待异常是不处理的，类似于 Java 的 Thread.uncaughtExceptionHandler，
// 将它们暴露给用户的（async 以及 produce）。后者依赖用户来最终消耗异常，比如说，通过 await 或 receive （produce 以及 receive 在通道中介绍过）。
fun exceptionPropagation() = runBlocking {
    val job = GlobalScope.launch {
        println("Throwing exception from launch")
        throw IndexOutOfBoundsException() // Will be printed to the console by Thread.defaultUncaughtExceptionHandler
    }
    job.join()
    println("Joined failed job")
    val deferred = GlobalScope.async {
        println("Throwing exception from async")
        throw ArithmeticException() // Nothing is printed, relying on user to call await
    }
    try {
        deferred.await()
        println("Unreached")
    } catch (e: ArithmeticException) {
        println("Caught ArithmeticException")
    }
}