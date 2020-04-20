package com.koko.www.androiddemo.activity.launchMode

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.koko.www.androiddemo.R

/**
 * 测试 taskAffinity 属性
 * 1.singleTask 与 android:taskAffinity 属性相结合的方式来指定我们 Activity 所需要的栈名称，
 * 使相应的 Activity 存在于不同的栈中。
 *
 * 2.因为以 singleTask 和 singleInstance 模式启动的 Activity 只能位于任务的根部
 * Activity 的 allowTaskReparenting 仅限于 standard 和 singleTop 启动模式中使用。
 *
 * 3.设置了 FLAG_ACTIVITY_NEW_TASK 标识，意思是把将要启动的 Activity 放在一个新 Task（任务）中。
 * 首先会查找是否存在和被启动的 Activity 具有相同的 taskAffinity（亲和关系）的 Task（任务栈）（注意同一个应用程序中的 Activity 的 taskAffinity 一样）。
 * 如果有，则直接把这个 Task（任务栈）整体移动到前台，并保持栈中的状态不变，即 Task（任务栈）中的 Activity 顺序不变；
 * 如果没有，则新建一个 Task（任务栈）来存放被启动的 Activity。
 *
 */
class TaskAffinityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_affinity)

        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {
            val intent = Intent()
            val component = ComponentName(this, SingleTaskActivity::class.java)
            intent.component = component
            startActivity(intent)
        }


    }
}