package com.koko.opensourcedemo.kotlin.functions

import java.util.*
import kotlin.collections.ArrayList

/**
 * 函数的概念用法
 */
fun main() {
//    defaultArguments()

    //可以通过使用星号操作符将可变数量参数（vararg） 以命名形式传入：
    koko(strings = *arrayOf("a", "b", "c"))

    //已经有一个数组并希望将其内容传给该函数，
    // 我们使用伸展（spread）操作符（在数组前面加 *）
    val a = arrayOf(1, 2, 3)
    val list = asList(-1, 0, *a, 4)

    //局部函数
    localFunctions()
    //调用成员函数
    val sample = Sample()
    sample.foo()
    //函数可以有泛型参数，通过在函数名前使用尖括号指定：
    asList(1)

}

//泛型函数
fun <T> asList(vararg ts: T): List<T> {
    val result = ArrayList<T>()
    for (t in ts) // ts is an Array
        result.add(t)
    return result
}

//成员函数是在类或对象内部定义的函数：
class Sample {
    fun foo() {
        print("Foo")
    }
}

//局部函数
fun localFunctions() {
    val visited = LinkedList<Int>()
    fun localFunctions() {
//        局部函数可以访问外部函数（即闭包）的局部变量，所以在上例中，visited 可以是局部变量：
        if (visited.size != 7) return
    }
}

fun foo(bar: Int = 0, baz: Int = 1, qux: () -> Unit) { /*……*/
}

//可变参数
fun koko(vararg strings: String) { /*……*/
}


//默认参数
//如果在默认参数之后的最后一个参数是 lambda 表达式，
//那么它既可以作为命名参数在括号内传入，也可以在括号外传入：
fun defaultArguments() {
    foo(1) { println("hello") }     // 使用默认值 baz = 1
    foo(qux = { println("hello") }) // 使用两个默认值 bar = 0 与 baz = 1
    foo { println("hello") }        // 使用两个默认值 bar = 0 与 baz = 1
}
