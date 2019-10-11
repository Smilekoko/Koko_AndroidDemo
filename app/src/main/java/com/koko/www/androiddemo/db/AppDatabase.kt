package com.koko.www.androiddemo.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * 数据库对象，一般使用单例模式来实例化
 */
@Database(exportSchema = false,entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "user_database")
                        .fallbackToDestructiveMigration()
                        //一般UI主线程是不运行运行数据库操作的
//                        .allowMainThreadQueries()
                        //添加数据库回调方法
                        .addCallback(UserDatabaseCallback(scope))
                        .build()
                INSTANCE = instance
                return instance
            }


        }


        /**
         * 数据库的回调方法，可以在里面添加数据库初始化的策略
         * 一般作为单例的模式的内部类，方便访问数据库实例
         *
         */
        class UserDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
            init {
                Log.e("koko", "数据库回调类这里初始化了")
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                Log.e("koko", "回调onOpen(),只有在在Dao操作时调用")
                INSTANCE?.let { database ->
                    scope.launch {
                        populateDatabase(database.userDao())
                    }
                }
            }

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Log.e("koko", "回调onCreate()")
            }

            override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                super.onDestructiveMigration(db)
                Log.e("koko", "回调onDestructiveMigration()")
            }

            /**
             * 填充数据库
             */
            private suspend fun populateDatabase(userDao: UserDao) {
                Log.e("koko", "填充数据库populateDatabase()")
                userDao.deleteAll()

                var word = User("Hello")
                userDao.insert(word)
                word = User("World!")
                userDao.insert(word)
            }
        }

    }
}
