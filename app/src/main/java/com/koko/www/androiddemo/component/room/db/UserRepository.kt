package com.koko.www.androiddemo.component.room.db

import androidx.lifecycle.LiveData

/**
 * UserRepository用来管理数据源，数据可能来自本来数据库，也可能来自网络
 * 例如构造函数可以再添加网络数据源
 */
class UserRepository(userDao: UserDao) {
    val allWords: LiveData<List<User>> = userDao.getAlphabetizedUsers()
}