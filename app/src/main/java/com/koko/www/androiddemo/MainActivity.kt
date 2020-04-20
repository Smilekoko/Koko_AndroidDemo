package com.koko.www.androiddemo

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.koko.www.androiddemo.activity.launchMode.TaskAffinityActivity
import com.koko.www.androiddemo.thread.asyncTask.AsyncTaskActivity
import com.koko.www.androiddemo.useInterface.recyclerView.RecyclerViewActivity


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handleIntent(intent)
        newIntent()
    }

    //关于处理appLinking的intent
    //对应AndroidManifest文件,看看怎么设置的Intent过滤器
    private fun handleIntent(intent: Intent) {
        val appLinkAction = intent.action
        val appLinkData: Uri? = intent.data
        if (Intent.ACTION_VIEW == appLinkAction) {
            appLinkData?.lastPathSegment?.also { recipeId ->
                Uri.parse("content://com.recipe_app/recipe/")
                        .buildUpon()
                        .appendPath(recipeId)
                        .build().also { appData ->
                            showRecipe(appData)
                        }
            }
        }
    }

    private fun showRecipe(appData: Uri?) {

        Log.i("koko",appData.toString())
    }

    private fun newIntent() {
        val intent = Intent()
        val component = ComponentName(this, TaskAffinityActivity::class.java)
        intent.component = component
        startActivity(intent)
    }


}

