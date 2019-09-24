package com.koko.www.androiddemo.component.paging.pagingWithDb

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.paging.Config
import androidx.paging.toLiveData

class CheeseViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = CheeseDb.getCheeseDb(app).cheeseDao()
    val allCheeses = dao.allCheesesByName().toLiveData(
            Config(
                    pageSize = 30,
                    //如果希望UI在应用程序完成数据提取之前显示列表，则可以向用户显示占位符列表项。
                    // 在 PagedList处理这种情况，通过呈现列表项数据null，直到数据被加载。
                    enablePlaceholders = true,
                    maxSize = 200))


    fun insert(text: CharSequence) = ioThread {
        dao.insert(Cheese(id = 0, name = text.toString()))
    }

    fun remove(cheese: Cheese) = ioThread {
        dao.delete(cheese)
    }
}
