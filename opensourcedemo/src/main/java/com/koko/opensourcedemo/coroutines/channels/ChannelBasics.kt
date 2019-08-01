package com.koko.opensourcedemo.coroutines.channels

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 通道的基本用法
 */
fun main() {
//    basics()
    closeAndIterationChannel()
}

/**
 * Channel
 * 提供了挂起的 send
 * 提供了挂起的 receive
 */
fun basics() = runBlocking {
    //sampleStart
    val channel = Channel<Int>()
    launch {
        // this might be heavy CPU-consuming computation or async logic, we'll just send five squares
        for (x in 1..5) channel.send(x * x)
    }
    // here we print five received integers:
    repeat(5) { println(channel.receive()) }
    println("Done!")
//sampleEnd
}

/**
 * 关闭通道和迭代通道
 */
fun closeAndIterationChannel(): Unit = runBlocking {
    //sampleStart
    val channel = Channel<Int>()
    launch {
        for (x in 1..5) channel.send(x * x)
        channel.close() // we're done sending
    }
    // here we print received values using `for` loop (until the channel is closed)
    for (y in channel) println(y)
    println("Done!")
//sampleEnd
}