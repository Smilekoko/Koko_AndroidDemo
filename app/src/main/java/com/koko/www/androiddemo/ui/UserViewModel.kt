package com.koko.www.androiddemo.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.koko.www.androiddemo.db.AppDatabase
import com.koko.www.androiddemo.db.User
import com.koko.www.androiddemo.data.UserRepository

/**
 * ViewModel是UI和数据的中介
 * UI通过ViewModel获取数据或者更改数据
 *
 * 编写ViewModel时Repository和RoomDatabase类应该是私有的不应该暴露
 * 可以暴露的是数据如  val allUsers: LiveData<List<User>>
 */
class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    val allUsers: LiveData<List<User>>

    init {
        val userDao = AppDatabase.getDatabase(application, viewModelScope).userDao()
        repository = UserRepository(userDao)

        //数据
        allUsers = repository.allWords
    }
}