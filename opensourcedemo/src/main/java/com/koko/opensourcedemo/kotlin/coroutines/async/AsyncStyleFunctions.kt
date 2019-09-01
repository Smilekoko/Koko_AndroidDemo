package com.koko.opensourcedemo.kotlin.coroutines.async

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

/**
 * 定义异步风格的函数
 */
fun main() {
    asyncStyleFunctions()
}

//定义异步风格的函数来 异步 的调用
//这些 xxxAsync 函数不是 挂起 函数。
//它们可以在任何地方被使用。 然而，它们总是在调用它们的代码中意味着异步（这里的意思是 并发 ）执行。
//这种函数可能在抛出异常后不终止,继续运行在后台
fun somethingUsefulOneAsync() = GlobalScope.async {
    doSomethingUsefulOne()
}

//定义异步风格的函数来 异步 的调用
fun somethingUsefulTwoAsync() = GlobalScope.async {
    doSomethingUsefulTwo()
}

/**
 * 如果该函数内部发生异常抛出
 * somethingUsefulOneAsync()和somethingUsefulTwoAsync()仍然可以在后台继续
 */
fun asyncStyleFunctions() {
    val time = measureTimeMillis {
        // we can initiate async actions outside of a coroutine
        val one = somethingUsefulOneAsync()
        val two = somethingUsefulTwoAsync()
        // but waiting for a result must involve either suspending or blocking.
        // here we use `runBlocking { ... }` to block the main thread while waiting for the result
        runBlocking {
            println("The answer is ${one.await() + two.await()}")
        }
    }
    println("Completed in $time ms")
}