package com.koko.www.androiddemo.component.paging.pagingWithDb

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

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

