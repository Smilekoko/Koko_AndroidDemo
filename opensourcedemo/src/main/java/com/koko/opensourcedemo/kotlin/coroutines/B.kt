package com.koko.opensourcedemo.kotlin.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {
    launch { doWorld() }
    println("Hello,")
}

// 这是你的第一个挂起函数
//在协程内部可以像普通函数一样使用挂起函数，
// 不过其额外特性是，同样可以使用其他挂起函数（如本例中的 delay）来挂起协程的执行。
suspend fun doWorld() {
    delay(1000L)
    println("World!")
}