package com.koko.opensourcedemo.kotlin.classAndObject

/**
 * 需要创建一个对某个类做了轻微改动的类的对象，而不用为之显式声明新的子类。
 * Kotlin use object expressions and object declarations处理这种情况。
 */
fun main() {
}

interface Moveable {
    fun move() {}
}

class Outer {
    private var a: String? = null

    //    object c : Moveable {
//        override fun move() {
//            super.move()
//        println(a)
//        }
//    }

    // 用变量c去接收object表达式
    // 如果object只是声明，它代表一个静态内部类。
    // 如果用变量接收object表达式，它代表一个匿名内部类对象。
    private val c = object : Moveable {
        override fun move() {
            super.move()
            println(a)
        }
    }

    //类内部的对象声明可以用 companion 关键字标记：
    //可以省略伴生对象的名称，在这种情况下将使用名称 Companion：
    companion object {
        private const val REQUEST_LOCATION_PERMISSION_CODE = 1
    }
}

//object还有一个非常重要的作用，就是生成单例对象。如果你需要在Kotlin语言中使用单例
object Singleton {
    fun f1() {

    }

    fun f2() {

    }


}

