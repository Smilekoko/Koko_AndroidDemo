package com.koko.opensourcedemo.glide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.koko.opensourcedemo.R

/**
 * 由于现在是在迁移版本的AndroidX的过程中glide也在适应新的版本，所以暂时搁置
 */
class GlideActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glide)

//        val fragment = findViewById(R.id.imageFragment)
//        Glide.with(fragment)
//                .load(myUrl)
//                .placeholder(R.drawable.placeholder)
//                .fitCenter()
//                .into(imageView)
    }
}
