package com.koko.www.androiddemo.useInterface.systemUi

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.*
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import com.koko.www.androiddemo.R

/**
 * 关于System Bar和Navigation bar的一些flag的使用
 */
class SystemUIActivity : AppCompatActivity() {

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//         You should never show the action bar without the status bar.
//         supportActionBar?.hide()

        if (Build.VERSION.SDK_INT < 16) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            Thread.sleep(1000)
            //flag有各自的效果，单一使用无法达到效果的需要混合使用

//            控制SystemBar相关：

//            SYSTEM_UI_FLAG_FULLSCREEN:该属性是用来隐藏状态栏的。
//            当滑动system bar、点击home键menu键就会清除掉flag，状态栏会重新显示出来。
//            并且布局也会随着状态栏的显隐进行布局调整进行重绘。
//            hideStatusBar()


//            SYSTEM_UI_FLAG_HIDE_NAVIGATION：该属性是用来隐藏导航栏的
//            与隐藏状态栏不同的是点击任意布局中的任意位置都会导致导航栏导航栏重新显示出来。
//            并且布局也会随着状态栏导航栏的显隐进行布局调整进行重绘。
//            hideNavigation()


//            SYSTEM_UI_FLAG_LOW_PROFILE:这个属性的能力是让SystemBar在视觉上变得模糊，重要性变得更低一点。
//            dimSystemBar()


//            布局相关：

//            SYSTEM_UI_FLAG_LAYOUT_STABLE：该flag的作用是保持布局稳定，避免在显隐状态栏导航栏的时候发生布局的变化。
//            可以辅助以下SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN、SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION两个flag的使用，
//            让应用保持全屏的情况下，布局不随状态栏导航栏显隐发生变化。
//            也可以不配合这两个flag使用，也能达到保持布局稳定的效果，不过不能实现全屏，会留出状态栏和导航栏的位置。
//            layoutStable()


//            SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN:可以让布局延伸到状态栏的位置。
//            在状态栏在隐藏和显示之前切换的时候，布局稳定的显示在状态栏后面（如果显示状态栏则布局在状态栏后面，隐藏状态栏布局也不变）。
//            layoutFullScreen()

//            SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION:可以让布局延伸到导航栏的位置。
//            可以让导航栏在隐藏和显示之前切换的时候，布局稳定的显示在导航栏后面（如果显示导航栏则在导航栏后面，隐藏导航栏也不变）。
//            layoutHideNavigation()


//            沉浸式相关
//            SYSTEM_UI_IMMERSIVE：
//            该沉浸模式用于应用程序在用户将很大程度上与屏幕的交互。
//            例如游戏，在图库中查看图像，或者阅读分页内容，例如书籍或演示文稿中的幻灯片。
//            immersive()


//            SYSTEM_UI_FLAG_IMMERSIVE_STICKY:粘性沉浸式
//            在粘性沉浸式模式下，如果用户使用系统栏从边缘滑动，系统栏会出现，
//            但它们是半透明的，并且触摸手势会传递到您的应用程序，因此应用程序也可以响应手势。
            immersiveSticky()

        }

        setContentView(R.layout.activity_system_ui)
        val imageView = findViewById<ImageView>(R.id.imageView)
        imageView.setOnClickListener {
        }
        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
        }
    }


    fun dimSystemBar() {
        window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_LOW_PROFILE
    }

    private fun hideStatusBar() {
        window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_FULLSCREEN

    }


    private fun showStatusBar() {
        window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_VISIBLE
    }


    private fun hideNavigation() {
        val uiOptions = SYSTEM_UI_FLAG_HIDE_NAVIGATION
        window.decorView.systemUiVisibility = uiOptions
    }


    private fun layoutStable() {
        val uiOptions = SYSTEM_UI_FLAG_FULLSCREEN or SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.decorView.systemUiVisibility = uiOptions
    }

    private fun layoutFullScreen() {
        val uiOptions = (SYSTEM_UI_FLAG_FULLSCREEN
                or SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.decorView.systemUiVisibility = uiOptions
    }


    private fun layoutHideNavigation() {
        window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or SYSTEM_UI_FLAG_LAYOUT_STABLE
    }

    private fun immersive() {
        window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_IMMERSIVE or
                SYSTEM_UI_FLAG_FULLSCREEN or
                SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }

    private fun immersiveSticky() {
        window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                SYSTEM_UI_FLAG_FULLSCREEN or
                SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                SYSTEM_UI_FLAG_LAYOUT_STABLE
    }
}
