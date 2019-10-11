package com.koko.www.androiddemo.activity

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.koko.www.androiddemo.R

/**
 * 四大组件之activity
 * 测试回退行为
 */
class TwoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two)
    }

    /**
     * 当活动在前台后退/返回按钮，
     * 重要的是要注意，默认情况下， onSaveInstanceState() 回调在这种情况下不会触发。
     * 但是，您可以覆盖该 onBackPressed()方法以实现某些自定义行为，例如“确认退出”对话框。
     *
     * @param outState
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.e("TAG", "回退按钮触发了保存状态吗？/没有")
    }

    override fun onBackPressed() {
        //如果重写该onBackPressed() 方法，
        // 我们仍强烈建议您super.onBackPressed()从重写方法调用 。否则，后退按钮行为可能会对用户产生不良影响。
        //        super.onBackPressed();
        showNormalDialog()
    }

    private fun showNormalDialog() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        val normalDialog = AlertDialog.Builder(this@TwoActivity)
        normalDialog.setIcon(R.drawable.icon_dialog)
        normalDialog.setTitle("我是一个普通Dialog")
        normalDialog.setMessage("你要点击哪一个按钮呢?")
        normalDialog.setPositiveButton("确定"
        ) { _, _ ->
            //...To-do
        }
        normalDialog.setNegativeButton("关闭"
        ) { _, _ ->
            //...To-do
            // 在您的活动完成后调用此选项并应关闭
            finish()
        }
        // 显示
        normalDialog.show()
    }
}

