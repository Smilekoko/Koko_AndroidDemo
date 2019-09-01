package com.koko.opensourcedemo.kotlin.coroutines.selectExpression

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.selects.select

/**
 * select表达式
 */
fun main() {
    selectInChannel()//通道中select
//    e()
}

@ExperimentalCoroutinesApi
fun CoroutineScope.fizz() = produce<String> {
    while (true) { // sends "Fizz" every 300 ms
        delay(300)
        send("Fizz")
    }
}

@ExperimentalCoroutinesApi
fun CoroutineScope.buzz() = produce<String> {
    while (true) { // sends "Buzz!" every 500 ms
        delay(500)
        send("Buzz!")
    }
}

suspend fun selectFizzBuzz(fizz: ReceiveChannel<String>, buzz: ReceiveChannel<String>) {
    //使用 receive 挂起函数，我们可以从两个通道接收 其中一个 的数据。
    // 但是 select 表达式允许我们使用其 onReceive 子句 同时 从两者接收：
    select<Unit> {
        // <Unit> means that this select expression does not produce any result
        fizz.onReceive { value ->
            // this is the first select clause
            println("fizz -> '$value'")
        }
        buzz.onReceive { value ->
            // this is the second select clause
            println("buzz -> '$value'")
        }
    }
}

fun selectInChannel() = runBlocking<Unit> {
    //sampleStart
    val fizz = fizz()
    val buzz = buzz()
    repeat(7) {
        selectFizzBuzz(fizz, buzz)
    }
    coroutineContext.cancelChildren() // cancel fizz & buzz coroutines
//sampleEnd
}










fun CoroutineScope.switchMapDeferreds(input: ReceiveChannel<Deferred<String>>) = produce<String> {
    var current = input.receive() // start with first received deferred value
    while (isActive) { // loop while not cancelled/closed
        val next = select<Deferred<String>?> { // return next deferred value from this select or null
            input.onReceiveOrNull { update ->
                update // replaces next value to wait
            }
            current.onAwait { value ->
                send(value) // send value that current deferred has produced
                input.receiveOrNull() // and use the next deferred from the input channel
            }
        }
        if (next == null) {
            println("Channel was closed")
            break // out of loop
        } else {
            current = next
        }
    }
}

fun CoroutineScope.asyncStringAsync(str: String, time: Long) = async {
    delay(time)
    str
}

fun e() = runBlocking<Unit> {
    //sampleStart
    val chan = Channel<Deferred<String>>() // the channel for test
    launch { // launch printing coroutine
        for (s in switchMapDeferreds(chan))
            println(s) // print each received string
    }
    chan.send(asyncStringAsync("BEGIN", 100))
    delay(200) // enough time for "BEGIN" to be produced
    chan.send(asyncStringAsync("Slow", 500))
    delay(100) // not enough time to produce slow
    chan.send(asyncStringAsync("Replace", 100))
    delay(500) // give it time before the last one
    chan.send(asyncStringAsync("END", 500))
    delay(1000) // give it time to process
    chan.close() // close the channel ...
    delay(500) // and wait some time to let it finish
//sampleEnd
}