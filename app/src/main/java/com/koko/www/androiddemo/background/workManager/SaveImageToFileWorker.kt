package com.koko.www.androiddemo.background.workManager

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat
import java.util.*

/**
 * Saves the image to a permanent file
 */
class SaveImageToFileWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private val title = "Blurred Image"
    private val dateFormatter = SimpleDateFormat(
            "yyyy.MM.dd 'at' HH:mm:ss z",
            Locale.getDefault()
    )

   override fun doWork(): Result {

        makeStatusNotification("Saving image", applicationContext)
        sleep()

       val resolver = applicationContext.contentResolver
       val resourceUri = inputData.getString("KEY_IMAGE_URI")

       val bitmap = BitmapFactory.decodeStream(
               resolver.openInputStream(Uri.parse(resourceUri)))

       val imageUrl = MediaStore.Images.Media.insertImage(
               resolver, bitmap, title, dateFormatter.format(Date()))

        return Result.success()
    }
}