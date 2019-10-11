package com.koko.www.androiddemo.jetpack.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.koko.www.androiddemo.R
import com.koko.www.androiddemo.db.User
import com.koko.www.androiddemo.ui.UserViewModel

class RoomActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel

    private var users = emptyList<User>() // Cached copy of words

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)



        userViewModel.allUsers.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { users = words }
        })
        Log.e("koko","执行")
    }


    companion object {
        val users = arrayListOf(
                User("Course"),
                User("God"),
                User("Jesus"),
                User("Macbook"),
                User("Windows 7"),
                User("Windows 10"),
                User("Windows XP"),
                User("Windows 8"),
                User("Wi"),
                User("Playstation"),
                User("Nitendo"),
                User("Pro"),
                User("BMW ZX"),
                User("BMW OZ"),
                User("BMW QW"),
                User("AMMER 900"),
                User("ZERO 97"),
                User("ZERO 80"),
                User("Pedro Massango"),
                User("Pedro"),
                User("Massango"),
                User("Emilia")
        )
    }
}
