package com.koko.www.androiddemo.ui

import android.annotation.SuppressLint
import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.work.*
import com.koko.www.androiddemo.background.workManager.*

class BlurViewModel(application: Application) : AndroidViewModel(application) {

    internal var imageUri: Uri? = null
    internal var outputUri: Uri? = null
    private val outputWorkInfoItems: LiveData<List<WorkInfo>>


    //通过WorkManager API，即使应用程序退出或设备重新启动，
    // 也可以轻松地调度预期将要运行的可延迟异步任务。
    private val workManager: WorkManager = WorkManager.getInstance(application)

    init {
        outputWorkInfoItems = workManager.getWorkInfosByTagLiveData(TAG_OUTPUT)
    }

    /**
     * Create the WorkRequest to apply the blur and save the resulting image
     * @param blurLevel The amount to blur the image
     */
    fun applyBlur(blurLevel: Int) {

        // Add WorkRequest to Cleanup temporary images
        //这里使用了协程
        var continuation = workManager
                .beginUniqueWork(
                        "image_manipulation_work",
                        ExistingWorkPolicy.REPLACE,
                        OneTimeWorkRequest.from(CleanupWorker::class.java)
                )

        //利用循环次数来对应模糊Level
        // Add WorkRequests to blur the image the number of times requested
        for (i in 0 until blurLevel) {
            val blurBuilder = OneTimeWorkRequestBuilder<BlurWorker>()

            // Input the Uri if this is the first blur operation
            // After the first blur operation the input will be the output of previous
            // blur operations.
            if (i == 0) {
                blurBuilder.setInputData(createInputDataForUri())
            }
            continuation = continuation.then(blurBuilder.build())
        }

        // Create charging constraint
        val constraints = Constraints.Builder()
                .setRequiresCharging(true)
                .build()


        // Add WorkRequest to save the image to the filesystem
        val save = OneTimeWorkRequestBuilder<SaveImageToFileWorker>()
                .setConstraints(constraints)
                .addTag(TAG_OUTPUT)
                .build()
        continuation = continuation.then(save)



        // Actually start the work
        continuation.enqueue()
    }

    /**
     * Creates the input data bundle which includes the Uri to operate on
     * @return Data which contains the Image Uri as a String
     */
    private fun createInputDataForUri(): Data {
        val builder = Data.Builder()
        imageUri?.let {
            builder.putString("KEY_IMAGE_URI", imageUri.toString())
        }
        return builder.build()
    }

}