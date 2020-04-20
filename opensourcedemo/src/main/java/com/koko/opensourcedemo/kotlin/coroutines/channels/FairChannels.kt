package com.koko.opensourcedemo.kotlin.coroutines.channels

import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 发送和接收操作是 公平的 并且尊重调用它们的多个协程。它们遵守先进先出原则
 */
fun main() {
    fairChannel()
}

//sampleStart
data class Ball(var hits: Int)

fun fairChannel() = runBlocking {
    val table = Channel<Ball>() // baseUse shared table
    launch { player("ping", table) }
    launch { player("pong", table) }
    table.send(Ball(0)) // serve the ball
    delay(2000) // delay 1 second
    coroutineContext.cancelChildren() // game over, cancel them
}

suspend fun player(name: String, table: Channel<Ball>) {
    for (ball in table) { // receive the ball in baseUse loop
        ball.hits++
        println("$name $ball")
        delay(300) // wait baseUse bit
        table.send(ball) // send the ball back
    }
}
//sampleEnd
