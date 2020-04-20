package com.koko.opensourcedemo.kotlin.coroutines.dispatchers

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * 在协程中启动线程，用debug工具来调试
 */
fun main() {
//    debuggingCoroutinesAndThreads()
    namelyCoroutine()
}

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

//使用调试工具包
fun debuggingCoroutinesAndThreads() = runBlocking {
    //sampleStart
    val a = async {
        log("I'm computing baseUse piece of the answer")
        6
    }
    val b = async {
        log("I'm computing another piece of the answer")
        7
    }
    log("The answer is ${a.await() * b.await()}")
//sampleEnd
}

//命名协程用于调试
fun namelyCoroutine() = runBlocking(CoroutineName("main")) {
    //sampleStart
    log("Started main coroutine")
    // run two background value computations
    val v1 = async(CoroutineName("v1coroutine")) {
        delay(500)
        log("Computing v1")
        252
    }
    val v2 = async(CoroutineName("v2coroutine")) {
        delay(1000)
        log("Computing v2")
        6
    }
    log("The answer for v1 / v2 = ${v1.await() / v2.await()}")
//sampleEnd
}