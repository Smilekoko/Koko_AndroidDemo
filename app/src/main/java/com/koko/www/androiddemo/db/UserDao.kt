package com.koko.www.androiddemo.db

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Dao类是用注解和方法 代替SQL语法的API
 */
@Dao
interface UserDao {
    //不使用协程插入
//    @Insert
//    fun insert(user: User)

    //使用协程插入
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)


    //DAO和LiveData一起使用，LiveData只是和activity的生命周关联，要跟新数据库必须使用 MutableLiveData（LiveData的扩展类）
    //而且这个和LiveData的扩展，需要在数据被调用时查询才会调用
    @Query("SELECT * from user_table ORDER BY name ASC")
    fun getAlphabetizedUsers(): LiveData<List<User>>


    //使用协程时
    @Insert
    suspend fun coroutinesInsert(user: User)


    @Query("DELETE FROM user_table")
    suspend fun deleteAll()

}
