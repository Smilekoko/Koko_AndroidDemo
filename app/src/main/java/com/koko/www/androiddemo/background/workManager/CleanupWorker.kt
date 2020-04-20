package com.koko.www.androiddemo.background.workManager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.File
import java.lang.Exception

/**
 * Cleans up temporary files generated during blurring process
 */
class CleanupWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        makeStatusNotification("Cleaning up old temporary files", applicationContext)
        //模拟耗时操作
        sleep()
        try {
            val outputDirectory = File(applicationContext.filesDir, "blur_filter_outputs")
            if (outputDirectory.exists()) {
                val entries = outputDirectory.listFiles()
                if (entries != null) {
                    for (entry in entries) {
                        val name = entry.name
                        if (name.isNotEmpty() && name.endsWith(".png")) {
                            entry.delete()
                        }
                    }
                }
            }
            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }
}