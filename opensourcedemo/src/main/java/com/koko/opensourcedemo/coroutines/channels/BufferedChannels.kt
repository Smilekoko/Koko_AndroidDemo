package com.koko.opensourcedemo.coroutines.channels

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 带缓存的通道
 * 不带缓冲的话,如果发送先被调用，则它将被挂起直到接收被调用， 如果接收先被调用，它将被挂起直到发送被调用。
 */
fun main() {
    bufferedChannels()
}

/**
 * 缓冲允许发送者在被挂起前发送多个元素
 * 当缓冲区被占满的时候将会引起阻塞
 */
fun bufferedChannels() = runBlocking<Unit> {
    //sampleStart
    val channel = Channel<Int>(4) // create buffered channel
    val sender = launch {
        // launch sender coroutine
        repeat(10) {
            println("Sending $it") // print before sending each element
            channel.send(it) // will suspend when buffer is full
        }
    }
    // don't receive anything... just wait....
    delay(1000)
    sender.cancel() // cancel sender coroutine
//sampleEnd
}