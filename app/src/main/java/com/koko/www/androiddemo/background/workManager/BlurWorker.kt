package com.koko.www.androiddemo.background.workManager

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import java.io.FileNotFoundException

/**
 * BlurWorker
 */
class BlurWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val appContext = applicationContext

        //通知状态栏
        makeStatusNotification("Blurring image", appContext)

        return try {
            val outputData = createBlurredBitmap(appContext, inputData.getString("KEY_IMAGE_URI"))
            Result.success(outputData)
        } catch (fileNotFoundException: FileNotFoundException) {
            throw RuntimeException("Failed to decode input stream", fileNotFoundException)
        } catch (throwable: Throwable) {
            Result.failure()
        }

    }

    //抛出异常
    @Throws(FileNotFoundException::class, IllegalArgumentException::class)
    private fun createBlurredBitmap(appContext: Context, resourceUri: String?): Data {
        if (resourceUri.isNullOrEmpty()) {
            throw IllegalArgumentException("Invalid input uri")
        }

        val resolver = appContext.contentResolver

        // Create a bitmap
        val bitmap = BitmapFactory.decodeStream(
                resolver.openInputStream(Uri.parse(resourceUri)))

        // Blur the bitmap
        val output = blurBitmap(bitmap, appContext)

        // Write bitmap to a temp file
        val outputUri = writeBitmapToFile(appContext, output)

        // Return the output for the temp file
        return workDataOf("KEY_IMAGE_URI" to outputUri.toString())
    }
}