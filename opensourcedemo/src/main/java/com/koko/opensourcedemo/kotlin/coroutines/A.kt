package com.koko.opensourcedemo.kotlin.coroutines

import kotlinx.coroutines.*


fun main(args: Array<String>) {
    //    baseUse()
//    mainCoroutine1()
//    mainCoroutine2()
    daemonThread()
//    d()
//    e()
//    f()
}

/**
 * 守护线程
 * 携程启动是一个守护线程，当主线程完成后，携程会被销毁，及时没有执行完
 */
fun daemonThread() {
    val job = GlobalScope.launch {
        delay(1000)
        println("hello")
    }
    println("主线程睡眠前：isActive=${job.isActive} isCompleted=${job.isCompleted}")

    println("world")
    //主线程不睡眠的话,携程会提前结束
    Thread.sleep(2000)
    println("主线程睡眠后：isActive=${job.isActive} isCompleted=${job.isCompleted}")
}


/**
 * 基本使用
 * 携程是由程序直接实现的一种轻量级线程
 */
fun baseUse() {
    //在后台启动一个新的协程并继续
    GlobalScope.launch {
        delay(1000L) // 延迟（挂起）1000毫秒，注意这不会阻塞线程
        println("World!") //延迟之后执行打印
    }
    println("Hello,") // 协程延迟的时候不会影响主线程的执行
    Thread.sleep(2000L) // 阻塞线程2s，保证JVM存活，协程可正常执行完
}

/**
 * runBlocking{}定义主携程
 */
fun mainCoroutine1() {
    GlobalScope.launch {
        // 在后台启动一个新的协程并继续
        delay(1000L)
        println("World!")
    }
    println("Hello,") // 主线程中的代码会立即执行
    runBlocking {
        // 但是这个表达式阻塞了主线程
        delay(2000L)  // ……我们延迟 2 秒来保证 JVM 的存活
    }
}

/**
 * 另外一种方式定义主携程
 */
fun mainCoroutine2() = runBlocking {
    // 开始执行主协程
    GlobalScope.launch {
        // 在后台启动一个新的协程并继续
        delay(1000L)
        println("World!")
    }
    println("Hello,") // 主协程在这里会立即执行
    delay(2000L)      // 延迟 2 秒来保证 JVM 存活
}

fun d() = runBlocking {
    val job = GlobalScope.launch {
        // 启动一个新协程并保持对这个作业的引用
        delay(1000L)
        println("World!")
    }
    println("Hello,")
    job.join() // 等待直到子协程执行结束
}

fun e() = runBlocking {
    // this: CoroutineScope
    launch {
        // 在 runBlocking 作用域中启动一个新协程
        delay(1000L)
        println("World!")
    }
    println("Hello,")
}

fun f() = runBlocking {
    // this: CoroutineScope
    launch {
        delay(200L)
        println("Task from runBlocking")
    }

    //还可以使用 coroutineScope 构建器声明自己的作用域。
    // 它会创建一个协程作用域并且在所有已启动子协程执行完毕之前不会结束。
    coroutineScope {
        // 创建一个协程作用域
        launch {
            delay(500L)
            println("Task from nested launch")
        }

        delay(100L)
        println("Task from coroutine scope") // 这一行会在内嵌 launch 之前输出
    }

    println("Coroutine scope is over") // 这一行在内嵌 launch 执行完毕后才输出
}


