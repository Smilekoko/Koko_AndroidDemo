package com.koko.opensourcedemo.kotlin.coroutines.async

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

/**
 * 结构化并发来使用异步
 */
fun main() {
    structuredConcurrency()
}

/**
 * 由于 async 被定义为了 CoroutineScope 上的扩展，我们需要将它写在作用域内
 * 如果在 concurrentSum 函数内部发生了错误，并且它抛出了一个异常， 所有在作用域中启动的协程都将会被取消。
 */
suspend fun concurrentSum() = coroutineScope {
    val one = async { doSomethingUsefulOne() }
    val two = async { doSomethingUsefulTwo() }
    one.await() + two.await()
}

fun structuredConcurrency() = runBlocking {
    //sampleStart
    val time = measureTimeMillis {
        println("The answer is ${concurrentSum()}")
    }
    println("Completed in $time ms")
//sampleEnd
}
