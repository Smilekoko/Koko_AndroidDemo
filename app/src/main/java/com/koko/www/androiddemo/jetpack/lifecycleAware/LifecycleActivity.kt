package com.koko.www.androiddemo.jetpack.lifecycleAware

import android.location.Location
import android.location.LocationListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.koko.www.androiddemo.R

/**
 * 一个 生命周期组件和activity绑定的过程
 */
class LifecycleActivity : AppCompatActivity() {

    private val mGpsListener = MyLocationListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle)

        LifecycleAwareComponent.bindLifecycleAwareComponent(this)
    }

    /**
     * 一些监听器，可以继承系统的组件监听器
     */
    private inner class MyLocationListener : LocationListener {
        override fun onLocationChanged(p0: Location?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProviderEnabled(p0: String?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProviderDisabled(p0: String?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
}
