package com.koko.opensourcedemo.kotlin.coroutines.selectExpression

import kotlinx.coroutines.*
import kotlinx.coroutines.selects.select
import kotlin.random.Random

/**
 *    select 表达式获取 延迟值
 */
fun main() {
    selectDeferredExpression()
}


//启动一个异步函数，它在随机的延迟后会延迟返回字符串
fun CoroutineScope.asyncStringAsync(time: Int) = async {
    delay(time.toLong())
    "Waited for $time ms"
}

//随机启动十余个异步函数，每个都延迟随机的时间。
fun CoroutineScope.asyncStringsList(): List<Deferred<String>> {
    val random = Random(3)
    return List(12) { asyncStringAsync(random.nextInt(1000)) }
}

//等待第一个函数完成，并统计仍处于激活状态的延迟值的数量。
fun selectDeferredExpression() = runBlocking<Unit> {
    //sampleStart
    val list = asyncStringsList()
    val result = select<String> {
        list.withIndex().forEach { (index, deferred) ->
            //延迟值可以使用 onAwait 子句查询。
            deferred.onAwait { answer ->
                "Deferred $index produced answer '$answer'"
            }
        }
    }
    println(result)
    val countActive = list.count { it.isActive }
    println("$countActive coroutines are still active")
//sampleEnd
}
