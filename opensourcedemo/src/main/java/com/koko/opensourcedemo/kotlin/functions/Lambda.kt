package com.koko.opensourcedemo.kotlin.functions

import android.os.Build
import androidx.annotation.RequiresApi


/**
 * lambda表示式用法
 * lambda 表达式与匿名函数是“function literals”，即未声明的函数， 但立即做为表达式传递。
 *
 * Lambda表达式的特点:
 * 1.lambda 表达式总是括在{}中，
 * 2.完整语法形式的参数声明放在()内，并有可选的类型标注，
 * 3.函数体(如果存在)跟在一个 -> 符号之后。
 */
@RequiresApi(Build.VERSION_CODES.N)
fun main() {

    /**
     *   1.无参数的情况
     *   val / var 变量名 = { 操作的代码 }
     */
    //调用它只需要在后面加上()
    { println("Lambda表达式无参数有返回值") }()

    // lambda代码
    val test = { println("无参数") }
    // 调用
    test()

}


////
////
////
////    /**
////     *
////     *   2. 有参数的情况
////     *   val/var 变量名 : (参数的类型，参数类型，...) -> 返回值类型 = {参数1，参数2，... -> 操作参数的代码 }
////     *
////     *   可等价于
////     *   此种写法：即表达式的返回值类型会根据操作的代码自推导出来。
////     *   val/var 变量名 = { 参数1 ： 类型，参数2 : 类型, ... -> 操作参数的代码 }
////     */
////    // 源代码
//////    fun sum(baseUse : Int , b : Int) : Int{
//////        return baseUse + b
//////    }
////    // lambda
//////    val sum: (Int, Int) -> Int = { baseUse, b -> baseUse + b }
////    // 或者
////    val sum = { baseUse: Int, b: Int -> baseUse + b }
////    // 调用
////    sum(3, 5)
////
////
////    /**
////     * lambda表达式作为函数中的参数的时候
////     *  fun test(baseUse : Int, 参数名 : (参数1 ： 类型，参数2 : 类型, ... ) -> 表达式返回类型){
////     *  ...
////     *  }
////     */
////    // 源代码
//////    fun test(baseUse : Int , b : Int) : Int{
//////        return baseUse + b
//////    }
//////    fun sum(num1 : Int , num2 : Int) : Int{
//////        return num1 + num2
//////    }
////    // 调用
//////    test(10,sum(3,5)) // 结果为：18
////
////    // lambda
////    fun test(baseUse : Int , b : (num1 : Int , num2 : Int) -> Int) : Int{
////        return baseUse + b.invoke(3,5)
////    }
////
////    // 调用
//////    当Lambda表达式作为其一个参数时，只为其表达式提供了参数类型与返回类型，
//////    所以，我们在调用此高阶函数的时候我们要为该Lambda表达式写出它的具体实现。
//////   test(10,{ num1: Int, num2: Int ->  num1 + num2 })  // 结果为：18
////
//////    在 Kotlin 中有一个约定：如果函数的最后一个参数是函数，
//////    那么作为相应参数传入的 lambda 表达式可以放在圆括号之外
////    test(10) { num1: Int, num2: Int ->  num1 + num2 }  // 结果为：18
////
////
////    /**
////     * it并不是Kotlin中的一个关键字(保留字)。
////     *
////     * it是在当一个高阶函数中Lambda表达式的参数只有一个的时候可以使用it来使用此参数。
////     * it可表示为单个参数的隐式名称，是Kotlin语言约定的。
////     *
////     */
////    val it : Int = 0  // 即it不是`Kotlin`中的关键字。可用于变量名称
////    // 这里举例一个语言自带的一个高阶函数filter,此函数的作用是过滤掉不满足条件的值。
////    val arr = arrayOf(1,3,5,7,9)
////    // 过滤掉数组中元素小于2的元素，取其第一个打印。这里的it就表示每一个元素。
////    println(arr.filter { it < 5 }.component1())
////
////
////    fun itTest(num1 : Int, bool : (Int) -> Boolean) : Int{
////        return if (bool(num1)){ num1 } else 0
////    }
////
//////    println(itTest(10,{it > 5}))
////    println(itTest(4) {it > 5})
////
////
////    /**
////     * 使用Lambda表达式的时候，可以用下划线(_)表示未使用的参数，表示不处理这个参数。
////     */
////    val map = mapOf("key1" to "value1","key2" to "value2","key3" to "value3")
////
//////    map.forEach{
//////        key , value -> println("$key \t $value")
//////    }
////    map.forEach{ (key, value) -> println("$key \t $value")
////    }
////
////// 不需要key的时候
//////    map.forEach{
//////        _ , value -> println("$value")
//////    }
////    map.forEach{ (_, value) -> println(value)
////    }





