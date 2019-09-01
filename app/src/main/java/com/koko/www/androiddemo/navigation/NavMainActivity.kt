package com.koko.www.androiddemo.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.koko.www.androiddemo.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_nav_main.*

/**
 * 应用程序的入口
 */
class NavMainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_main)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        val navController: NavController = Navigation.findNavController(this, R.id.nav_host_fragment)

        //绑定当前的ActionBar，除此之外NavigationUI还能绑定Toolbar和CollapsingToolbarLayout
        //绑定后，系统会默认处理ActionBar左上角区域，为你添加返回按钮，将所切换到的Fragment在导航图里的name属性中的内容显示到Title
//        appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()
        //当要使用侧边栏导航时 .setDrawerLayout(drawerLayout)
        //获取导航配置文件信息
        appBarConfiguration = AppBarConfiguration.Builder(navController.graph).setDrawerLayout(drawerLayout).build()

        //绑定当前的ActionBar和navController绑定
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

        //底部栏和navController绑定
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        //设置左侧菜单
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        //setupWithNavController这个函数有多个重载
        NavigationUI.setupWithNavController(navigationView,navController)
    }


    /**
     * 加载菜单
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_settings, menu)
        return true
    }

    /**
     * ActionBar中的按钮被点击时，根据菜单中的Id，自动跳转到相应的页面
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val navController: NavController = Navigation.findNavController(this, R.id.nav_host_fragment)
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item)
    }

    /**
     * 左上角的反回按钮被点击时调用
     */
    override fun onSupportNavigateUp(): Boolean {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        Toast.makeText(this, "onSupportNavigateUp() called", Toast.LENGTH_SHORT).show()
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp()
    }

    /**
     * 点击后台按钮时调用
     */
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
