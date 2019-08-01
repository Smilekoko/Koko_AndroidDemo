package com.koko.opensourcedemo.coroutines.channels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking


fun main() {
    buildingChannelProducers()
}

//通道生产者
fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce {
    for (x in 1..5) send(x * x)
}

/**
 * 生产者——消费者 模式的一部分，并且经常能在并发的代码中看到它。
 * 你可以将生产者抽象成一个函数，并且使通道作为它的参数，但这与必须从函数中返回结果的常识相违悖。
 */
fun buildingChannelProducers() = runBlocking {
    //sampleStart
    val squares = produceSquares()
    //使用扩展函数 consumeEach 在消费者端替代 for 循环：
    squares.consumeEach { println(it) }
    println("Done!")
//sampleEnd
}