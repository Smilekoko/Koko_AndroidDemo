package com.koko.www.androiddemo.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/**
 * 抽象activity封装activity的公共逻辑
 *
 * 1.编写具体activity时思考那些是公共的逻辑，抽象处理，可以减轻思考难度
 * 2.帮助理解封装和基础,多态的问题
 *
 * 封装方面
 * 1. protected 修饰保证对本包和子类可见
 *
 * 继承方面
 * 1.子类继承父类,执行方法时先去找子类实现,子类没有覆盖方法再去找父类中方法
 * 2.super.onCreate(savedInstanceState) 可以保证优先去执行父类方法
 * 3.abstract 抽象出公共的类的属性,保证在之类中赋值
 * 多态方面
 *
 *
 */
abstract class BaseActivity : AppCompatActivity() {

    //抽象出activity的layout布局，在onCreate中应用
    protected abstract var layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Log.e("koko", "BaseActivity -->onCreate ")
        setContentView(layoutId)
    }
}