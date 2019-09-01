package com.koko.www.androiddemo.component.activity

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Button

import com.koko.www.androiddemo.R
import com.koko.www.androiddemo.component.activity.launchMode.CLEAR_TOPActivity
import com.koko.www.androiddemo.component.activity.launchMode.SingleTopActivity
import com.koko.www.androiddemo.component.activity.launchMode.TaskAffinityActivity
import com.koko.www.androiddemo.component.ipc.bundle.BundleActivity
import com.koko.www.androiddemo.component.ipc.parcelable.Hobby
import com.koko.www.androiddemo.component.ipc.parcelable.ParcelableActivity
import com.koko.www.androiddemo.component.ipc.parcelable.Person

import java.util.ArrayList

/**
 * 四大组件之activity
 */
class OneActivity : AppCompatActivity() {
    private val s: String = "OneActivity"
    private var button1: Button? = null
    private var button2: Button? = null
    private var button3: Button? = null
    private var button4: Button? = null
    private var button5: Button? = null
    private var button6: Button? = null
    private var hobbies: ArrayList<Hobby>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e(s, "onCreate():activity调用的第一个方法/当内存不足时，用户用navigates back到activity时，在onPause/onStop状态的activity会调用")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one)
        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)
        button4 = findViewById(R.id.button4)
        button5 = findViewById(R.id.button5)
        button6 = findViewById(R.id.button6)

        button1!!.setOnClickListener {
            val intent = Intent()
            val component = ComponentName(this@OneActivity, TwoActivity::class.java)
            intent.component = component
            startActivity(intent)
        }

        button2!!.setOnClickListener {
            val intent = Intent()
            val component = ComponentName(this@OneActivity, TaskAffinityActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.component = component
            startActivity(intent)
        }

        button3!!.setOnClickListener {
            val intent = Intent()
            val component = ComponentName(this@OneActivity, SingleTopActivity::class.java)
            intent.component = component
            startActivity(intent)
        }

        button4!!.setOnClickListener {
            val intent = Intent()
            val component = ComponentName(this@OneActivity, CLEAR_TOPActivity::class.java)
            intent.component = component
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.action = "FLAG_ACTIVITY_CLEAR_TOP tag启动"
            startActivity(intent)
        }

        button5!!.setOnClickListener {
            //传递
            val hobby1 = Hobby("唱歌")
            val hobby2 = Hobby("跳舞")
            hobbies = ArrayList()
            hobbies!!.add(hobby1)
            hobbies!!.add(hobby2)
            val person = Person("王雷", 23, false, Hobby("敲代码"), hobbies)
            Intent(this@OneActivity, ParcelableActivity::class.java).also {
                it.putExtra("person_data", person)
                startActivity(it)
            }
        }

        button6!!.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("str", "字符数据")
            bundle.putInt("int", 1)
            val intent = Intent()
            val component = ComponentName(this@OneActivity, BundleActivity::class.java)
            intent.component = component
            intent.putExtra("bundle", bundle)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.e(s, "onStart():紧接着onCreate后调用/onRestart后调用")
    }

    override fun onResume() {
        super.onResume()
        Log.e(s, "onResume():紧接着onStart后调用，接着Activity is running/或者这个activity回到foreground在onPause()后调用")
    }

    override fun onPause() {
        super.onPause()
        Log.e(s, "onPause():onStop()调用前为保存数据的必要调用/当另外一个activity启动时调用")
    }

    override fun onStop() {
        super.onStop()
        Log.e(s, "onStop():这个activity是不再可见时调用")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e(s, "onRestart():activity由onstop不可见状态回到foreground时调用")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(s, "onDestroy():activity摧毁时调用")
    }
}
