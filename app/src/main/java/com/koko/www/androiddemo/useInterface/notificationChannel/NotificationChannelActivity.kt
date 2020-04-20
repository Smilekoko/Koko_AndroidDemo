package com.koko.www.androiddemo.useInterface.notificationChannel

import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.koko.www.androiddemo.R
import android.app.NotificationChannel
import android.app.PendingIntent
import android.provider.Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS
import android.content.Intent
import android.provider.Settings
import android.widget.Toast


/**
 * android 8 之前通知是无法进行分类的，这样用户就无法对自己感兴趣和不感兴趣的通知进行分类处理.
 */
class NotificationChannelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty)
        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

//        basicNotification(this)

        //通知渠道一旦创建，控制权就在用户手中，如果有一个重要通知渠道被用户手动关闭了，我们就要提醒用户去手动打开该渠道。
        isChannelOpen(notificationManager)

        // Make a channel if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            val channelId1 = "upgrade"
            val channelName1 = "升级"
            val importance1 = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel1 = NotificationChannel(channelId1, channelName1, importance1)
            notificationManager.createNotificationChannel(notificationChannel1)

            val notification1 = NotificationCompat.Builder(this, "upgrade")
                    .setContentTitle("升级")
                    .setContentText("程序员终于下班了。。")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.ic_alarm_white_48dp)
                    .setAutoCancel(true)
                    .build()
            notificationManager.notify(100, notification1)



            // Create an explicit intent for an Activity in your app
            val intent = Intent(this, NotificationChannelActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

            val channelId2 = "compose"
            val channelName2 = "私信"
            val importance2 = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel2 = NotificationChannel(channelId2, channelName2, importance2)
            notificationManager.createNotificationChannel(notificationChannel2)

            val notification2 = NotificationCompat.Builder(this, "upgrade")
                    .setContentTitle("私信")
                    .setContentText("有人私信向你提出问题")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.ic_alarm_white_48dp)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build()
            notificationManager.notify(101, notification2)

        }
    }

    //基本使用Notification，不需要创建channel
    private fun basicNotification(context: Context) {
        // id :This is required for compatibility with Android 8.0 (API level 26) and higher,
        // but is ignored by older versions.
        val builder = NotificationCompat.Builder(this, "id")
                .setSmallIcon(R.drawable.ic_alarm_white_48dp)
                .setContentTitle("标题")
                .setContentText("内容啊")
                //The priority determines how intrusive the notification should be on Android 7.1 and lower.
                // (For Android 8.0 and higher, you must instead set the channel importance—shown in the next section.)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        NotificationManagerCompat.from(context).notify(11, builder.build())
    }

    private fun isChannelOpen(notificationManager:NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = notificationManager.getNotificationChannel("upgrade")
            if (channel.importance == NotificationManager.IMPORTANCE_NONE) {
                val intent = Intent(ACTION_CHANNEL_NOTIFICATION_SETTINGS)
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.id)
                startActivity(intent)
                Toast.makeText(this, "升级通知不能关闭，请手动将通知打开", Toast.LENGTH_SHORT).show()
            }
        }

    }

}

