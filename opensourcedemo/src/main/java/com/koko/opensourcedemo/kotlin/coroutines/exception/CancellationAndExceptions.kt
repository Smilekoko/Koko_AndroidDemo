package com.koko.opensourcedemo.kotlin.coroutines.exception

import kotlinx.coroutines.*
import java.io.IOException

fun main() {
//    cancellationExceptions()
//    otherExceptionsCancel()
//    exceptionsAggregation()
    transparentAndUnwrapped()
}

//协程内部使用 CancellationException 来进行取消，这个异常会被所有的处理者忽略，
// 所以那些可以被 catch 代码块捕获的异常仅仅应该被用来作为额外调试信息的资源。
fun cancellationExceptions() = runBlocking {
    //sampleStart
    val job = launch {
        val child = launch {
            try {
                delay(Long.MAX_VALUE)
            } finally {
                println("Child is cancelled")
            }
        }
        yield()
        println("Cancelling child")
        //当一个协程在没有任何理由的情况下使用 Job.cancel 取消的时候，它会被终止，但是它不会取消它的父协程。
        // it does not cancel its parent. Cancelling without cause is baseUse mechanism for parent to cancel its children without cancelling itself.
        child.cancel()
        child.join()
        yield()
        println("Parent is not cancelled")
    }
    job.join()
//sampleEnd
}

//CoroutineExceptionHandler 总是被设置在由 GlobalScope 启动的协程中。
//将异常处理者设置在 runBlocking 主作用域内启动的协程中是没有意义的，尽管子协程已经设置了异常处理者，但是主协程也总是会被取消的。
fun otherExceptionsCancel() = runBlocking {
    //sampleStart
    val handler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception")
    }
    val job = GlobalScope.launch(handler) {
        launch {
            // the first child
            try {
                delay(Long.MAX_VALUE)
            } finally {
                withContext(NonCancellable) {
                    println("Children are cancelled, but exception is not handled until all children terminate")
                    delay(100)
                    println("The first child finished its non cancellable block")
                }
            }
        }
        launch {
            // the second child
            delay(10)
            println("Second child throws an exception")
            throw ArithmeticException()
        }
    }
    job.join()
//sampleEnd
}

//处理多个异常
//通常的规则是“第一个异常赢得了胜利”，所以第一个被抛出的异常将会暴露给处理者。
// if coroutine throws an exception in its finally block. So, additional exceptions are suppressed.
fun exceptionsAggregation()= runBlocking {
    val handler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception with suppressed ${exception.suppressed.contentToString()}")
    }
    val job = GlobalScope.launch(handler) {
        launch {
            try {
                delay(Long.MAX_VALUE)
            } finally {
                throw ArithmeticException()
            }
        }
        launch {
            delay(100)
            throw IOException()
        }
        delay(Long.MAX_VALUE)
    }
    job.join()
}

//Cancellation exceptions are transparent and unwrapped by default:
fun transparentAndUnwrapped()= runBlocking {
    //sampleStart
    val handler = CoroutineExceptionHandler { _, exception ->
        println("Caught original $exception")
    }
    val job = GlobalScope.launch(handler) {
        val inner = launch {
            launch {
                launch {
                    throw IOException()
                }
            }
        }
        try {
            inner.join()
        } catch (e: CancellationException) {
            println("Rethrowing CancellationException with original cause")
            throw e
        }
    }
    job.join()
//sampleEnd
}