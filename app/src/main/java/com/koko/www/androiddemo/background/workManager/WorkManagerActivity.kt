package com.koko.www.androiddemo.background.workManager

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.koko.www.androiddemo.R
import com.koko.www.androiddemo.ui.BlurViewModel

/**
 *  使用主要步骤：
 * 第一步：初始化WorkManager
 *
 * 第二部：定义后台同步工作的 Work
 * The doWork() method is run synchronously on a background thread provided by WorkManager.
 *
 * 第三步：定义WorkRequest，Configure how and when to run the task
 * (包括constraints，Delays，
 * Retries and Backoff Policy，
 * Defining input/output for your task)
 * val uploadWorkRequest = OneTimeWorkRequestBuilder<UploadWorker>().build()
 * 或者  OneTimeWorkRequest.from(CleanupWorker::class.java)
 *
 * 第三步：WorkManager提交任务到系统
 * WorkManager.getInstance(myContext).enqueue(uploadWorkRequest)
 */
class WorkManagerActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var goButton: Button
    private lateinit var outputButton: Button
    private lateinit var cancelButton: Button
    private lateinit var radioGroup: RadioGroup
    private lateinit var viewModel: BlurViewModel
    private val blurLevel: Int
        get() =
            when (radioGroup.checkedRadioButtonId) {
                R.id.radio_blur_lv_1 -> 1
                R.id.radio_blur_lv_2 -> 2
                R.id.radio_blur_lv_3 -> 3
                else -> 1
            }


    //请求权限的特征码
    private val requestCodePermissions = 101
    //onActivityResult的特征码
    private val requestCodeImage = 100

    private val keyPermissionsRequestCount = "keyPermissionsRequestCount"
    private lateinit var selectImageButton: Button

    //最多需检查的权限数
    private val maxNumberRequestPermissions = 2
    private var permissionRequestCount: Int = 0

    //定义需要检查的权限
    private val permissions = listOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    private fun bindResources() {
        imageView = findViewById(R.id.image_view)
        progressBar = findViewById(R.id.progress_bar)
        goButton = findViewById(R.id.go_button)
        outputButton = findViewById(R.id.see_file_button)
        cancelButton = findViewById(R.id.cancel_button)
        radioGroup = findViewById(R.id.radio_group)
        selectImageButton = findViewById(R.id.selectImage)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_manager)
        bindResources()

        // Check if the required number of permissions is saved
        savedInstanceState?.let {
            permissionRequestCount = it.getInt(keyPermissionsRequestCount, 0)
        }

        // Make sure the app has correct permissions to run
        requestPermissionsIfNecessary()

        // Create request to get image from filesystem when button clicked
        selectImageButton.setOnClickListener {
            val chooseIntent = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(chooseIntent, requestCodeImage)
        }


        // Get the ViewModel
        viewModel = ViewModelProviders.of(this).get(BlurViewModel::class.java)

        // Setup blur image file button
        setOnClickListeners()
    }


    private fun setOnClickListeners() {
        goButton.setOnClickListener { viewModel.applyBlur(blurLevel) }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
//                Log.e("koko",requestCode.toString())
            when (requestCode) {
                requestCodeImage -> data?.let { handleImageRequestResult(data) }
            }
        }
    }

    private fun handleImageRequestResult(data: Intent) {
//        Log.e("koko",data.toString())
        val imageUri: Uri? = data.data
        Glide.with(this).load(imageUri).into(imageView)
    }


    private fun requestPermissionsIfNecessary() {

        if (!checkAllPermissions()) {
            if (permissionRequestCount < maxNumberRequestPermissions) {
                permissionRequestCount += 1 //为了请求两次权限

                //请求权限
                //onRequestPermissionsResult方法中接收传递的请求结果
                ActivityCompat.requestPermissions(
                        this,
                        permissions.toTypedArray(),
                        requestCodePermissions
                )
            } else {
                //请求两次权限都deny后
                Toast.makeText(
                        this,
                        R.string.set_permissions_in_settings,
                        Toast.LENGTH_LONG
                ).show()
                selectImageButton.isEnabled = false
            }
        }
    }

    /**
     * 权限请求
     * the results of permission requests will be delivered to its onRequestPermissionsResult(int, String[], int[]) method.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestCodePermissions) {
            requestPermissionsIfNecessary() // no-op if permissions are granted already.
        }
    }


    /** all Permission Checking  */
    private fun checkAllPermissions(): Boolean {
        var hasPermissions = true
        for (permission in permissions) {
            //and和&&(短路与)不一样，不具备短路功能，每个结果都会被评估
            hasPermissions = hasPermissions and isPermissionGranted(permission)
        }
        return hasPermissions
    }


    /**
     * 检查Permission的核心代码
     *
     * 	PERMISSION_GRANTED如果您拥有许可，或者PERMISSION_DENIED没有。
     */
    private fun isPermissionGranted(permission: String) =
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

