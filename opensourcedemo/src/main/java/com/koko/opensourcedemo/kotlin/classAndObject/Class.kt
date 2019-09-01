package com.koko.opensourcedemo.kotlin.classAndObject

import android.content.Context
import android.location.Location
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent


fun main() {
    //Kotlin 中使用关键字 class 声明类
    class Invoice { /*……*/ }

    //类声明由类名、类头（指定其类型参数、主构造函数等）以及由花括号包围的类体构成。
    //类头与类体都是可选的； 如果一个类没有类体，可以省略花括号。
    class Empty

    val p: Person = Person("koko")
}

//在 Kotlin 中的一个类可以有一个主构造函数以及一个或多个次构造函数。
//主构造函数是类头的一部分：它跟在类名（与可选的类型参数）后。
class Person constructor(private var firstName: String) {/*...*/ }

//主构造函数没有任何注解或者可见性修饰符，可以省略这个 constructor 关键字。
class Student(firstName: String) { /*……*/ }

class InitOrderDemo(name: String) {
    val firstProperty = "First property: $name".also(::println)

    //主构造函数不能包含任何的代码。
    // 初始化的代码可以放到以 init 关键字作为前缀的初始化块（initializer blocks）中。
    init {
        println("First initializer block that prints $name")
    }

    val secondProperty = "Second property: ${name.length}".also(::println)


    //在实例初始化期间，初始化块按照它们出现在类体中的顺序执行，与属性初始化器交织在一起：
    init {
        println("Second initializer block that prints ${name.length}")
    }

}

class Person1 {
    var children: MutableList<Person1> = mutableListOf()

    //类也可以声明前缀有 constructor的次构造函数
    constructor(parent: Person1) {
        parent.children.add(this)
    }
}

//internal 意味着该成员只在相同模块内可见。更具体地说， 一个模块是编译在一起的一套 Kotlin 文件：
//
//一个 IntelliJ IDEA 模块；
//一个 Maven 项目；
//一个 Gradle 源集（例外是 test 源集可以访问 main 的 internal 声明）；
//一次 <kotlinc> Ant 任务执行所编译的一套文件。
internal class MyLocationListener(
        private val context: Context,
        private val lifecycle: Lifecycle,
        private val callback: (Location) -> Unit
) {

    private var enabled = false

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        if (enabled) {
            // connect
        }
    }

    fun enable() {
        enabled = true
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            // connect if not connected
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {
        // disconnect if connected
    }
}

