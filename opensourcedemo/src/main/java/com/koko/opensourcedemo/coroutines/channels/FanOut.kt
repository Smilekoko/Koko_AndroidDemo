package com.koko.opensourcedemo.coroutines.channels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 多个协程也许会接收相同的管道，在它们之间进行分布式工作。
 */
fun main(){
    fanOut()
}

fun CoroutineScope.produceNumbersdelay() = produce {
    var x = 1 // start from 1
    while (true) {
        send(x++) // produce next
        delay(100) // wait 0.1s
    }
}

fun CoroutineScope.launchProcessor(id: Int, channel: ReceiveChannel<Int>) = launch {
    for (msg in channel) {
        println("Processor #$id received $msg")
    }
}

fun fanOut()= runBlocking {
    //sampleStart
    val producer = produceNumbersdelay()
//    repeat(5) { launchProcessor(it, producer) }
    launchProcessor(1,producer)
    launchProcessor(2,producer)
    launchProcessor(3,producer)
    launchProcessor(4,producer)
    launchProcessor(5,producer)
    delay(950)
    producer.cancel() // cancel producer coroutine and thus kill them all
//sampleEnd
}