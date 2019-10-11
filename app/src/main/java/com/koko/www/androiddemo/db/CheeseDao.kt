package com.koko.www.androiddemo.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.koko.www.androiddemo.model.Cheese

@Dao
interface CheeseDao {
    @Delete
    fun delete(cheese: Cheese)

    @Insert
    fun insert(cheese: Cheese)

    @Insert
    fun insert(cheeses: List<Cheese>)

    @Query("SELECT * FROM Cheese ORDER BY name COLLATE NOCASE ASC")
    fun allCheesesByName(): DataSource.Factory<Int, Cheese>
}

