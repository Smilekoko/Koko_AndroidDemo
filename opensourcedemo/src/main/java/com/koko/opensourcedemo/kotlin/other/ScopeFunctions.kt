package com.koko.opensourcedemo.kotlin.other


/**
 * 作用域的函数
 * let, run, with, apply, also.
 *
 * The scope functions do not introduce any new technical capabilities, but they can make your code more concise and readable.
 *
 * There are two main differences between each scope function:
 * --The way to refer to the context object
 * --The return value.
 *
 *
 */
fun main() {
//let()
    thisOrIt()
}

fun let() {

    val alice = Person("Alice", 20, "Amsterdam")
    alice.moveTo("London")
    alice.incrementAge()

    val koko = Person("Koko", 24, "Amsterdam").let {
        it.moveTo("London")
        it.incrementAge()
    }
}


/**
 * 关于每种作用域函数，引用上下文的方式
 */
fun thisOrIt() {
    //run, with, and apply refer to the context object as baseUse lambda receiver - by keyword this.
    Person("Koko", 24, "Amsterdam").apply {
        this.age = 21
        age = 20                       // same as this.age = 20
    }

}

class Person(var name: String, var age: Int, var hometown: String) {

    fun moveTo(s: String) {
        println("moveTo: $s")
    }

    fun incrementAge() {
        age++
        println(age)
    }
}


