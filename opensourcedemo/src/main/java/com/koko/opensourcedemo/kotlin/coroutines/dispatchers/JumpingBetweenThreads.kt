package com.koko.opensourcedemo.kotlin.coroutines.dispatchers

import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/**
 * 在不同线程间跳转
 *
 */
@ObsoleteCoroutinesApi
fun main() {
    //sampleStart
    //Note that this example also uses the use function from the Kotlin standard library
    // to release threads created with newSingleThreadContext when they are no longer needed.
    newSingleThreadContext("Ctx1").use { ctx1 ->

        newSingleThreadContext("Ctx2").use { ctx2 ->

            //使用 runBlocking 来显式指定了一个上下文，并且另一个使用 withContext 函数来改变协程的上下文，而仍然驻留在相同的协程中
            runBlocking(ctx1) {
                log("Started in ctx1")
                withContext(ctx2) {
                    log("Working in ctx2")
                }
                log("Back to ctx1")
            }

        }

    }
//sampleEnd

}

