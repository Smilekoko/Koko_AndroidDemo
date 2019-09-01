package com.koko.www.androiddemo.debug

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.koko.www.androiddemo.R

/**
 * 测试Android studio 的debug等功能
 */
class DebugActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debug)
        variables()
    }

    /**
     *debug 变量的变化
     */
    fun variables() {
        //只读的变量
        val number1 = 1
        var number2 = 2
        Log.e("debug", "number2=$number2")
        number2 = 3
        Log.e("debug", "number1=$number1 number2=$number2")

        var operator: Calculator.Operator = Calculator.Operator.ADD

//        使用断点
        when (operator) {
//            Assignments are not expressions, and only expressions are allowed in this context
//            赋值不是表达式，在此上下文中只允许使用表达式
//            "="是赋值，这里只允许使用表达式"=="
//       断点条件    true结果的表达是可以执行，false结果的表达式不可执行 Reason: Boolean value expected
            Calculator.Operator.ADD -> Log.e("debug", "number2=$number2+2")
            Calculator.Operator.SUB -> Log.e("debug", "number2=$number2-2")
            Calculator.Operator.MUL -> Log.e("debug", "number2=$number2*2")
            Calculator.Operator.DIV -> Log.e("debug", "number2=$number2/2")
        }
    }
}
