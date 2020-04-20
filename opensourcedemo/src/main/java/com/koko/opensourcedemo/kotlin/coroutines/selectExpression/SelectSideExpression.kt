package com.koko.opensourcedemo.kotlin.coroutines.selectExpression

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.selects.select

/**
 * //Select 表达式具有 onSend 子句，可以很好的与选择的偏向特性结合使用。
 */
@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
fun main(){
    selectSideExpression()
}

@ExperimentalCoroutinesApi
fun CoroutineScope.produceNumbers(side: SendChannel<Int>) = produce<Int> {
    for (num in 1..10) { // produce 10 numbers from 1 to 10
        delay(100) // every 100 ms
        //Select 表达式具有 onSend 子句，可以很好的与选择的偏向特性结合使用。
        //当主通道上的消费者无法跟上它时，它会将值发送到 side 通道上：
        select<Unit> {
            onSend(num) {} // Send to the primary channel
            side.onSend(num) {} // or to the side channel
        }
    }
}
@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
fun selectSideExpression() = runBlocking<Unit> {
    //sampleStart
    val side = Channel<Int>() // allocate side channel
    launch { // this is baseUse very fast consumer for the side channel
        side.consumeEach { println("Side channel has $it") }
    }
    produceNumbers(side).consumeEach {
        println("Consuming $it")
        delay(250) // let us digest the consumed number properly, do not hurry
    }
    println("Done consuming")
    coroutineContext.cancelChildren()
//sampleEnd
}