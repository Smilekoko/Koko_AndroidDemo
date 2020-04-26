package com.koko.www.androiddemo.activity.status

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.koko.www.androiddemo.R

/**
 * 关于activity保存状态的demo
 * 比如旋转屏幕时
 *
 * 1.使用自带的Bundle保存部分状态
 * 2.使用SharedPreferences保存键值对来保存状态
 */
class StateActivity : AppCompatActivity() {
    private lateinit var editText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state)
        editText = findViewById(R.id.editText)

        //任意context都可以获取的
//        val sharedPref = this.getSharedPreferences(
//                getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        //当前activity获取偏好
        val sharedPref1 = this.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = 25
        val highScore = sharedPref1.getInt(getString(R.string.activity_pref_key), defaultValue)
        Toast.makeText(this, ""+highScore, Toast.LENGTH_SHORT).show()
    }


    //系统将在活动销毁之前调用该方法
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putString("key", editText.text.toString())
        super.onSaveInstanceState(savedInstanceState)
    }

    //恢复状态时调用
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Toast.makeText(this, savedInstanceState.getString("key"), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        //当前activity获取的
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putInt(getString(R.string.activity_pref_key), 50)
            apply()
            super.onDestroy()
        }

    }
}

