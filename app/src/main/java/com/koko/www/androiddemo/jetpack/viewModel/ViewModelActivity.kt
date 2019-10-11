package com.koko.www.androiddemo.jetpack.viewModel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Chronometer
import androidx.lifecycle.ViewModelProviders
import com.koko.www.androiddemo.R
import com.koko.www.androiddemo.ui.ChronometerViewModel

/**
 * ViewModel的基本使用
 */
class ViewModelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_model)

        val chronometerViewModel = ViewModelProviders.of(this).get(ChronometerViewModel::class.java)

        val chronometer: Chronometer = findViewById(R.id.chronometer)


        if (chronometerViewModel.startTime == null) {
            // If the start date is not defined, it's a new ViewModel so set it.
            val startTime = SystemClock.elapsedRealtime()
            chronometerViewModel.setStartTime(startTime)
            chronometer.base = startTime
        } else {
            // Otherwise the ViewModel has been retained, set the chronometer's base to the original
            // starting time.
            chronometer.base = chronometerViewModel.startTime!!
        }

        chronometer.start()
    }
}
