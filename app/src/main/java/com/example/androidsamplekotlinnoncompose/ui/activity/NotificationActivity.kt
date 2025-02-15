package com.example.androidsamplekotlinnoncompose.ui.activity

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.example.androidsamplekotlinnoncompose.EdgeMode
import com.example.androidsamplekotlinnoncompose.R
import com.example.androidsamplekotlinnoncompose.databinding.ActivityNotificationBinding
import com.example.androidsamplekotlinnoncompose.repository.model.bundle.NotificationActivityData
import com.example.androidsamplekotlinnoncompose.repository.model.bundle.NotificationFragmentData
import com.example.androidsamplekotlinnoncompose.utility.ActivityLifeCycleHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.random.Random

private const val CHANNEL_ID_BASIC = "Test_Notification_ID_Basic"
private const val CHANNEL_ID_IMPORTANCE_HIGH = "Test_Notification_ID_Importance_High"
private const val GROUP_KEY_1 = "Test_Notification_Group_Key_1"
private const val GROUP_KEY_2 = "Test_Notification_Group_Key_2"

private const val BASIC_CONTENT = "SystemStateRecoveryActivity"

//private const val BASIC_CONTENT = "NotificationActivity"
//private const val IMPORTANCE_CONTENT = "SystemStateRecoveryActivity"
private const val IMPORTANCE_CONTENT = "NotificationActivity"


class NotificationActivity : BaseActivity() {
    private lateinit var binding: ActivityNotificationBinding

    //通知管理器
    private lateinit var notificationManager: NotificationManager
    private var basicIDIndex = 0
    private var importanceIDIndex = 0
    private var importanceBigTextIDIndex = 0
    private var importanceBigPictureIDIndex = 0
    private var groupKeyIndex_1 = 0
    private var groupKeyIndex_2 = 0

    //Coroutine
    private var notificationJob = Job() //若要把 Job 跟 Dispatchers 放置一起
    private var myScope = CoroutineScope(notificationJob + Dispatchers.Main)//共用一個 Scope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        enableEdgeToEdgeMode(binding.root, binding.mainContainerCL, EdgeMode.NEW_EDGE_TO_EDGE)
        setContentView(binding.root)


        iniNotificationManager()
        setNotificationBasic()
        setNotificationImportance()
        setUI()

        if (savedInstanceState != null) {
            Log.e("Noti-onCr-saved", "Not Null")
        } else {
            Log.e("Noti-onCr-saved", " Null")
        }

    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val detectNumber = ActivityLifeCycleHelper.getInstance(myApplication).numStarted
        val toActivityData = intent.extras?.getString(TO_ACTIVITY_DATA_KEY)

        Log.e("Noti-onNew-detectNumber", "$detectNumber")
        Log.e("Noti-onNew-data", "$toActivityData")
    }

    fun setUI() {
        binding.btnShowBasicNotification.setOnClickListener {
            val basicNotification = createBasicOrImportanceNotification(
                "Basic_$basicIDIndex",
                BASIC_CONTENT,
                CHANNEL_ID_BASIC,
                CLEAR_ALL_AND_TO_LAUNCHER_TAG
            )
            basicIDIndex++
            notificationManager.notify(Random.nextInt(), basicNotification)
        }

        binding.btnShowImportanceNotification.setOnClickListener {
            val importanceNotification = createBasicOrImportanceNotification(
                "Importance_$importanceIDIndex",
                IMPORTANCE_CONTENT,
                CHANNEL_ID_IMPORTANCE_HIGH,
                NOT_CLEAR_ALL_AND_TO_LAUNCHER_TAG
            )
            importanceIDIndex++
            notificationManager.notify(Random.nextInt(), importanceNotification)
        }

        binding.btnShowLoadingNotification.setOnClickListener { }
        binding.btnUpdateLoadingNotification.setOnClickListener { }

        binding.btnBigTextNotification.setOnClickListener {

            val contentText = "SystemStateRecoveryActivity"
            val bigTextStyle = NotificationCompat.BigTextStyle()
                .bigText("11_1111111_11_1111111_11_1111111_11_1111111_11_1111111")
            val systemStateRecoveryActivityData = NotificationActivityData(contentText, "GOGO")


            val bigTextNotification = createBigTextOrPictureNotification(
                "BigText_$importanceBigTextIDIndex",
                contentText,
                CHANNEL_ID_BASIC,
                bigTextStyle,
                INTENT_TO_ACTIVITY_TAG,
                systemStateRecoveryActivityData
            )

            importanceBigTextIDIndex++
            notificationManager.notify(Random.nextInt(), bigTextNotification)
        }
        binding.btnBigPictureNotification.setOnClickListener {
            val contentText = IMPORTANCE_CONTENT_HOME_ACTIVITY
            val bigPictureStyle = NotificationCompat.BigPictureStyle()
                .bigPicture(BitmapFactory.decodeResource(resources, R.drawable.desk_lands))
            val homeActivityData = NotificationActivityData("HomeActivity", "GOGO")
            val homeFragmentDataData = NotificationFragmentData("HomeFragment", FRAGMENT_DATA_1_KEY, "40")

            val bigPictureNotification = createBigTextOrPictureNotification(
                "BigPicture_$importanceBigPictureIDIndex",
                contentText,
                CHANNEL_ID_IMPORTANCE_HIGH,
                bigPictureStyle,
                INTENT_TO_ACTIVITY_TAG,
                homeActivityData,
                homeFragmentDataData
            )

            importanceBigPictureIDIndex++
            notificationManager.notify(Random.nextInt(), bigPictureNotification)
        }
        binding.btnShowNotificationGroup.setOnClickListener { }


        binding.btnShowNotificationTestFragment.setOnClickListener {
            val contentText = IMPORTANCE_CONTENT_HOME_ACTIVITY


            val importanceNotification = createBasicOrImportanceNotification(
                "Importance_$importanceIDIndex",
                IMPORTANCE_CONTENT,
                CHANNEL_ID_IMPORTANCE_HIGH,
                INTENT_TO_ACTIVITY_TAG
            )
        }


    }

    private fun setNotificationBasic() {
        setNotificationChannel(NotificationManager.IMPORTANCE_DEFAULT, CHANNEL_ID_BASIC)
    }

    private fun setNotificationImportance() {
        setNotificationChannel(NotificationManager.IMPORTANCE_HIGH, CHANNEL_ID_IMPORTANCE_HIGH)
    }

    //通知管理器初始化
    private fun iniNotificationManager() {
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    //建立通知頻道
    private fun setNotificationChannel(importanceLevel: Int, channelID: String) {
        //建立通知頻道
        if (Build.VERSION.SDK_INT >= ANDROID_VERSION_OREO_8) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)

            //頻道設置與建立
            val mChannel = NotificationChannel(channelID, name, importanceLevel).apply {
                this.description = descriptionText
                //(不太需要設定)開啟 LED light 與 color
//                this.enableLights(true)
//                this.lightColor = getColor(android.R.color.holo_red_light)
                //(不太需要設定)開啟圓角
//                this.setShowBadge(true)
                //鎖定螢幕是否顯示
                this.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                //設置通知震動與大小
                this.enableVibration(true)
                this.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
                //設置音效 讀取 音效檔案有需要再測試
                //this.setSound()
            }

            //頻道註冊
            notificationManager.createNotificationChannel(mChannel)
            //頻道刪除，但建議不要使用，而是要好好控管通知
            //notificationManager.deleteNotificationChannel(CHANNEL_ID)
        }
    }

    //製作基本或重要的簡易訊息
    private fun createBasicOrImportanceNotification(
        title: String,
        content: String,
        channelID: String,
        lifecycleBehavior: String,
        activityData: NotificationActivityData? = null,
        fragmentData: NotificationFragmentData? = null
    ): Notification {
        val notificationInstance = NotificationCompat.Builder(this, channelID).let {
            it.setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(content)
                //時間(不設定等於 抓系統時間)
//                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentIntent(addPendingIntent(content, lifecycleBehavior, activityData, fragmentData))
                .setVibrate(longArrayOf(0, 1000, 500, 1000))
            if (Build.VERSION.SDK_INT < ANDROID_VERSION_Q_10) {
                it.setPriority(NotificationCompat.PRIORITY_HIGH)
            }
            it.build()
        }

        return notificationInstance
    }

    //製作 BigText 或 BigPicture 的訊息
    private fun createBigTextOrPictureNotification(
        title: String,
        content: String,
        channelID: String,
        styleData: NotificationCompat.Style,
        lifecycleBehavior: String,
        activityData: NotificationActivityData? = null,
        fragmentData: NotificationFragmentData? = null
    ): Notification {
        val notificationInstance = NotificationCompat.Builder(this, channelID).let {
            it.setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(content)
                .setStyle(styleData)
                //時間(不設定等於 抓系統時間)
//                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentIntent(
                    addPendingIntent(
                        content,
                        lifecycleBehavior,
                        activityData,
                        fragmentData
                    )
                )
                .setVibrate(longArrayOf(0, 1000, 500, 1000))
            if (Build.VERSION.SDK_INT < ANDROID_VERSION_Q_10) {
                it.setPriority(NotificationCompat.PRIORITY_HIGH)
            }

            it.build()
        }

        return notificationInstance
    }

    //加入點擊事件
    private fun addPendingIntent(
        data: String,
        lifecycleBehavior: String,
        activityData: NotificationActivityData? = null,
        fragmentData: NotificationFragmentData? = null
    ): PendingIntent {
        //Intent setting
        val intent = Intent().let {
            val bundle = Bundle()
            bundle.putSerializable(TO_ACTIVITY_DATA_KEY, activityData)
            bundle.putString(LIFECYCLE_BEHAVIOR_KEY, lifecycleBehavior)
            fragmentData?.apply {
                bundle.putSerializable(TO_FRAGMENT_DATA_KEY, this)
            }

            it.putExtras(bundle)
            it.setClass(this@NotificationActivity, SingleActivity::class.java)
        }

        //PendingIntent setting
        val flag =
            if (Build.VERSION.SDK_INT >= ANDROID_VERSION_S_12) {
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }
        return PendingIntent.getActivity(this, Random.nextInt(), intent, flag)
    }


    private fun createNotificationAll(
        title: String,
        content: String,
        groupKey: String? = null,
        styleData: NotificationCompat.Style? = null,
        isSummary: Boolean = false,
        channelID: String = CHANNEL_ID_BASIC
    ): Notification {
        val notificationInstance = NotificationCompat.Builder(this, channelID).let {
            it.setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(content)
                //時間
                .setWhen(System.currentTimeMillis())
                //點擊後事件
                //.setContentIntent()
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE);
            //可延展-新增一大段文字
            if (styleData != null) {
                it.setStyle(styleData)
            }
            //設置為 GroupKey
            groupKey?.apply {
                it.setGroup(groupKey)
            }
            //設置是否為 Summary
            if (isSummary) {
                it.setGroupSummary(true)
                it.setStyle(
                    NotificationCompat.InboxStyle()
                        .addLine("AlexCheck this out")
                        .addLine("Jeff Chang Launch Party")
                        .setBigContentTitle("2 new messages")
                        .setSummaryText("janedoe@example.com")
                )
            }
            it.build()
        }
        return notificationInstance
    }

    private fun showNotificationGroup(channelID: String) {
        //推播管理容器 Instance
//        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        //建立通知頻道
        if (Build.VERSION.SDK_INT >= ANDROID_VERSION_Q_10) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            //頻道設置與建立
            val mChannel = NotificationChannel(channelID, name, importance).apply {
                this.description = descriptionText
                //設置通知震動與大小
                this.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                this.vibrationPattern = longArrayOf(0, 10)
            }

            //頻道註冊
            notificationManager.createNotificationChannel(mChannel)
        }

        //setting Notification
        val n1StyleBigTextStyle = NotificationCompat.BigTextStyle().bigText("11_1111111")
        val nSummaryStyleBigTextStyle = NotificationCompat.BigTextStyle().bigText("Group")
        val notification1 = createNotificationAll("Title1", "11", GROUP_KEY_1, n1StyleBigTextStyle)
        val notificationSummary =
            createNotificationAll("TitleS", "Group", GROUP_KEY_1, nSummaryStyleBigTextStyle, true)


        //show Notification
//        notificationManager.notify(1, notification)
//        notificationManager.notify(Random.nextInt(), notification.build())
        notificationManager.apply {
            if (ActivityCompat.checkSelfPermission(
                    this@NotificationActivity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
//            notify(0, notification1)
            notify(99, notificationSummary)
        }

    }

    private fun addNotificationGroup() {
        notificationManager.apply {
            val notificationAdd =
                createNotificationAll("Title1", "11_${groupKeyIndex_1}", GROUP_KEY_1)
            notificationManager.apply {
                if (ActivityCompat.checkSelfPermission(
                        this@NotificationActivity,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                notify(groupKeyIndex_1, notificationAdd)
                groupKeyIndex_1++
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        notificationJob.cancel()
    }
}