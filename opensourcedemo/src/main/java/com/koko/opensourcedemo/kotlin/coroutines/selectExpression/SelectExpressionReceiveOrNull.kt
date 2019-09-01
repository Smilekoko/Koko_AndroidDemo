package com.koko.opensourcedemo.kotlin.coroutines.selectExpression

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select


@ExperimentalCoroutinesApi
fun main(){
    //关闭通道的select Expression
    selectExpressionReceiveOrNull()
}
@ExperimentalCoroutinesApi
suspend fun selectAorB(a: ReceiveChannel<String>, b: ReceiveChannel<String>): String =
        select {
            a.onReceiveOrNull { value ->
                if (value == null)
                    "Channel 'a' is closed"
                else
                    "a -> '$value'"
            }
            b.onReceiveOrNull { value ->
                if (value == null)
                    "Channel 'b' is closed"
                else
                    "b -> '$value'"
            }
        }


@ExperimentalCoroutinesApi
fun selectExpressionReceiveOrNull() = runBlocking<Unit> {
    //sampleStart
    val a = produce<String> {
        repeat(4) { send("Hello $it") }
    }
    val b = produce<String> {
        repeat(4) { send("World $it") }
    }
    repeat(8) {
        // print first eight results
        println(selectAorB(a, b))
    }
    coroutineContext.cancelChildren()
//sampleEnd
}