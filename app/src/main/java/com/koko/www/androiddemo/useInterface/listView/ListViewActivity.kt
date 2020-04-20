package com.koko.www.androiddemo.useInterface.listView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ListView
import com.koko.www.androiddemo.R
import com.koko.www.androiddemo.db.Animal
import com.koko.www.androiddemo.ui.adapter.MyBaseAdapter


/**
 * ListView的常用属性有：
 * android:divider 子分割线
 * android:dividerHeight 分割线高度
 * android:listSelector 子项点击效果
 * android:scrollbars 活动条
 *
 * ListView常用方法有：
 * addFooterView(View v)在列表尾部加一个View
 * addHeaderView(View v)在列表头部加一个View
 * setAdapter(ListAdapter adapter)设置配置器
 * setDivider(Drawable divider)设置子分隔栏
 * setDividerHeight(int height)设置分隔栏高度
 *
 * 适配器:
 * ArrayAdapter适用于显示信息比较单一的场景，若显示项中包含多种形式的数据，就不太适用了
 * SimpleAdapter是BaseAdapter的子类，BaseAdapter较SimpleAdapter来讲更为灵活，在开发中也更为常用。
 *
 *
 */
class ListViewActivity : AppCompatActivity() {
    private lateinit var mListView: ListView
    private lateinit var mListView2: ListView
    private lateinit var mArrayAdapter: ArrayAdapter<String>
    private lateinit var mBaseAdapter: BaseAdapter
    private val data = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")//准备数据源
    private lateinit var data2: ArrayList<Animal>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)
        mListView = findViewById(R.id.listView)
        mListView2 = findViewById(R.id.listView2)

        mArrayAdapter = ArrayAdapter(this, R.layout.simple_list_item_text, data)
        mListView.adapter = mArrayAdapter

        initData()
        mBaseAdapter = MyBaseAdapter(this,data2)
        mListView2.adapter = mBaseAdapter

    }

    private fun initData() {
        data2 = ArrayList()
        val animal0 = Animal("兔八哥", R.drawable.ic_home)
        val animal1 = Animal("眼镜蛇", R.drawable.ic_home)
        val animal2 = Animal("小金鱼", R.drawable.ic_home)
        val animal3 = Animal("千里马", R.drawable.ic_home)
        val animal4 = Animal("米老鼠", R.drawable.ic_home)
        val animal5 = Animal("大国宝", R.drawable.ic_home)
        data2.add(animal0)
        data2.add(animal1)
        data2.add(animal2)
        data2.add(animal3)
        data2.add(animal4)
        data2.add(animal5)
    }
}
