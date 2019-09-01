package com.koko.opensourcedemo.kotlin.coroutines.exception

import kotlinx.coroutines.*

/**
 * 常规的 Job,取消是一种双向机制，在协程的整个层次结构之间传播。
 * SupervisorJob 的取消只会向下传播。
 */
fun main() {

//    supervisorJob()
//    supervisionScope()
    exceptionsInSupervised()
}

fun supervisorJob() = runBlocking {
    val supervisor = SupervisorJob()
    with(CoroutineScope(coroutineContext + supervisor)) {
        // launch the first child -- its exception is ignored for this example (don't do this in practice!)
        val firstChild = launch(CoroutineExceptionHandler { _, _ -> }) {
            println("First child is failing")
            throw AssertionError("First child is cancelled")
        }
        // launch the second child
        val secondChild = launch {
            firstChild.join()
            // Cancellation of the first child is not propagated to the second child
            println("First child is cancelled: ${firstChild.isCancelled}, but second one is still active")
            try {
                delay(Long.MAX_VALUE)
            } finally {
                // But cancellation of the supervisor is propagated
                println("Second child is cancelled because supervisor is cancelled")
            }
        }
        // wait until the first child fails & completes
        firstChild.join()
        println("Cancelling supervisor")
        supervisor.cancel()
        secondChild.join()
    }
}

//对于作用域的并发，supervisorScope 可以被用来替代 coroutineScope 来实现相同的目的。
// 它只会单向的传播并且当子作业自身执行失败的时候将它们全部取消。
// 它也会在所有的子作业执行结束前等待， 就像 coroutineScope 所做的那样。
fun supervisionScope() = runBlocking {
    try {
        supervisorScope {
            val child = launch {
                try {
                    println("Child is sleeping")
                    delay(Long.MAX_VALUE)
                } finally {
                    println("Child is cancelled")
                }
            }
            // Give our child a chance to execute and print using yield
            yield()
            println("Throwing exception from scope")
            throw AssertionError()
        }
    } catch (e: AssertionError) {
        println("Caught assertion error")
    }
}

//常规的作业和监督作业之间的另一个重要区别是异常处理。 每一个子作业应该通过异常处理机制处理自身的异常。
// 这种差异来自于子作业的执行失败不会传播给它的父作业的事实。
fun exceptionsInSupervised() = runBlocking {
    val handler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception")
    }
//    coroutineScope {
//        val child = launch(handler) {
//            println("Child throws an exception")
//            throw AssertionError()
//        }
//        println("Scope is completing")
//    }
    supervisorScope {
        val child = launch(handler) {
            println("Child throws an exception")
            throw AssertionError()
        }
        println("Scope is completing")
    }
    println("Scope is completed")
}