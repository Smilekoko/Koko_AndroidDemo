package com.koko.www.androiddemo.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 实体类，添加注解来对应数据库的表
 * Room disallows object references between entity classes，避免在UI线程上进行查询
 */
@Entity(tableName = "user_table")
class User(
        @ColumnInfo(name = "name")
        var name: String? = null,
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0

)
