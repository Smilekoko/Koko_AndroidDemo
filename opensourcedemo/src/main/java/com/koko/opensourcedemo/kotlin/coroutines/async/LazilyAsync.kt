package com.koko.opensourcedemo.kotlin.coroutines.async

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

/**
 * 惰性启动的 async
 */
fun main(){
    lazilyAsync()
}

//如果我们在 println 中调用 await 并在个别协程上省略 start，
// 则我们会得到顺序的行为作为 await 来启动协程的执行并且等待执行结束，这不是懒序列的预期用例。
fun lazilyAsync() = runBlocking<Unit> {
    //sampleStart
    val time = measureTimeMillis {
        val one = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
        val two = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }
        // some computation
        one.start() // start the first one
        two.start() // start the second one
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
//sampleEnd
}