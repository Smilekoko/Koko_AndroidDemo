package com.koko.www.androiddemo

import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.koko.www.androiddemo.component.room.RoomActivity
import com.koko.www.androiddemo.component.viewModel.ViewModelActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val intent = Intent()
        val component = ComponentName(this, RoomActivity::class.java)
        intent.component = component
        startActivity(intent)
    }

}

