package com.koko.www.androiddemo.appData

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.koko.www.androiddemo.R
import java.io.File
import java.math.BigDecimal


/**
 * 内部储存
 */
class InternalStorageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_internal_storage)

//        queryFreeSpace()

        writeFile()
    }

    private fun queryFreeSpace() {
//        filesDir 就是内部储存文件
        val internalFile: File = this.filesDir

        var fileSize = bytes2kb(internalFile.freeSpace)
        //返回的是Bytes的long值
        Log.e("koko", "目前内部储存空间的未分配容量为:$fileSize +\n")
        fileSize = bytes2kb(internalFile.totalSpace)
        Log.e("koko", "目前内部储存空间的总容量为: $fileSize")

    }

    private fun writeFile() {
        val file = File(this.filesDir, "kokoInternalFile")
    //这里https://developer.android.google.cn/topic/security/data，需要一个支持库vpn断了，打不开依赖
    }

    /**
     * 1Bytes=8bits（1字节等于8位）
     * 1KB=1024Bytes（1KB等于1024字节）
     * 1MB=1024KB
     * 1GB=1024MB
     * 1TB=1024GB
     */
    private fun bytes2kb(bytes: Long): String {
        Log.e("koko", bytes.toString())
        val fileSize = BigDecimal(bytes)
        val megabyte = BigDecimal(1024 * 1024)
        var returnValue = fileSize.divide(megabyte, 2, BigDecimal.ROUND_UP).toFloat()
        if (returnValue > 1)
            return "$returnValue  MB "
        val kilobyte = BigDecimal(1024)
        returnValue = fileSize.divide(kilobyte, 2, BigDecimal.ROUND_UP).toFloat()
        return "$returnValue  KB "
    }
}
