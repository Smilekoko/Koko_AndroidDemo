package com.koko.www.androiddemo.data

import androidx.lifecycle.LiveData
import com.koko.www.androiddemo.db.User
import com.koko.www.androiddemo.db.UserDao

/**
 * UserRepository用来管理数据源，数据可能来自本来数据库，也可能来自网络
 * 例如构造函数可以再添加网络数据源
 *
 * 这里data的结果没有分离成数据model
 */
class UserRepository(userDao: UserDao) {
    val allWords: LiveData<List<User>> = userDao.getAlphabetizedUsers()
}