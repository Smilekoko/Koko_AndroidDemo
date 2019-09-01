package com.koko.opensourcedemo.litepal

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.koko.opensourcedemo.litepal.model.Album
import org.litepal.LitePal
import com.koko.opensourcedemo.litepal.model.Song
import android.util.Log
import org.litepal.extension.delete


class LitePalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.koko.opensourcedemo.R.layout.activity_lite_pal)

        //配置完成后，指定的数据库表就会自动被创建。比如获取SQLiteDatabase示例：
//        val db = LitePal.getDatabase()
//        insert()
//        query()
//        update()
//    delete()
    }

    private fun insert() {
        val album = Album()
        album.name = "album3"
        album.price = 10.99f
        album.save()
        val song1 = Song()
        song1.name = "song3"
        song1.duration = 356
        song1.save()
    }


    fun query() {
        val song = LitePal.find(Song::class.java, 3)
        Log.e("koko",song.name+song.duration)
    }

    fun update(){
        val albumToUpdate = LitePal.find(Album::class.java, 1)
        albumToUpdate.price = 20.99f // raise the price
        albumToUpdate.save()
    }

    fun delete(){
        LitePal.delete<Song>(1)
    }

    fun askPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            //SDK_INT:the SDK version of the software currently running on this hardware device.
        }
    }
}
