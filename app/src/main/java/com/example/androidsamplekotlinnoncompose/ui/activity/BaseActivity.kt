package com.example.androidsamplekotlinnoncompose.ui.activity

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.graphics.Insets
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.androidsamplekotlinnoncompose.EdgeMode
import com.example.androidsamplekotlinnoncompose.repository.model.bundle.NotificationActivityData
import com.example.androidsamplekotlinnoncompose.repository.model.bundle.NotificationFragmentData
import com.example.androidsamplekotlinnoncompose.utility.MyApplication
import com.google.android.material.bottomnavigation.BottomNavigationView


const val IMPORTANCE_CONTENT_HOME_ACTIVITY = "HomeActivity"

//Notification Intent Mode
const val CLEAR_ALL_AND_TO_LAUNCHER_TAG = "1" //全部清除並重新產生 Main。
const val NOT_CLEAR_ALL_AND_TO_LAUNCHER_TAG = "2" //不清除，但先回主頁，並都由此頁跳至全新要去的頁面。
const val INTENT_TO_ACTIVITY_TAG = "3" //不清除，先判斷是否是當下頁面，是就跳過去;不是，就先跳回主頁，並都由此頁跳至全新要去的頁面。

//Bundle
const val TO_FRAGMENT_DATA_KEY = "toFragmentDataKey"
const val FRAGMENT_DATA_1_KEY = "fragmentData1Key"
const val TO_ACTIVITY_DATA_KEY = "toActivityDataKey"
const val ACTIVITY_DATA_1_KEY = "activityData1Key"
const val LIFECYCLE_BEHAVIOR_KEY = "lifecycleBehaviorNotification"

//Android Version
const val ANDROID_VERSION_OREO_8 = 26
const val ANDROID_VERSION_Q_10 = 29
const val ANDROID_VERSION_S_12 = 31
const val ANDROID_VERSION_TIRAMISU_13 = 33


open class BaseActivity : AppCompatActivity() {
    lateinit var myApplication: MyApplication


    val stateBundleData = "activityNotificationData"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myApplication = application as MyApplication
        Log.e("BaseActivity",resources.configuration.screenLayout.toString())
    }

    /**
     * @param bindingView ViewBinding 的 View
     * @param toolBarLayout Toolbar 的 Layout 若有就要對上界處理
     * @param contentLayout 若有 Toolbar 則只做下界處理，若沒有 Toolbar 則上、下界處理
     * @param modeSelect EdgeMode 的選項
     */
    fun enableEdgeToEdgeMode(
        bindingView: View,
        contentLayout: ViewGroup,
        modeSelect: EdgeMode,
        toolBarLayout: ViewGroup? = null
    ) {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(bindingView) { v, insets ->
            val orientationMode = resources.configuration.orientation
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            when (orientationMode) {
                Configuration.ORIENTATION_LANDSCAPE -> {
                    //1調整 View 邊界延展 上下左右都延伸
                    v.setPadding(0, 0, 0, 0)
                    //2調整 View 內容多留邊界內容，內容不被 status bar 跟 navigation bar 擋住
                    //LANDSCAPE 的 navigation bar for Buttons 是在左右邊
                    //LANDSCAPE 的 navigation bar for Swipe Gestures 是在下方
                    //強制左右邊界為 0
                    adjustView(contentLayout, toolBarLayout, systemBars)
                    //3判斷是否為 Navigation for Buttons
                    if (systemBars.left > 0 || systemBars.right > 0) {
                        val controller =
                            WindowCompat.getInsetsController(window, bindingView)

                        controller.hide(WindowInsetsCompat.Type.navigationBars())
                        controller.systemBarsBehavior =
                            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                    }
                }

                Configuration.ORIENTATION_UNDEFINED,
                Configuration.ORIENTATION_PORTRAIT -> {
                    when (modeSelect) {
                        EdgeMode.NEW_ORIGINAL -> {
                            v.setPadding(
                                systemBars.left,
                                systemBars.top,
                                systemBars.right,
                                systemBars.bottom
                            )
                        }

                        EdgeMode.NEW_EDGE_TO_EDGE -> {
                            //1調整 View 邊界延展 上下左右都延伸
                            v.setPadding(0, 0, 0, 0)
                            //2調整 View 內容多留邊界內容，內容不被 status bar 跟 navigation bar 擋住
                            adjustView(contentLayout, toolBarLayout, systemBars)
                            //3 Navigation Bar 透明
                            if (Build.VERSION.SDK_INT >= ANDROID_VERSION_Q_10) {
                                window.isNavigationBarContrastEnforced = false
                            }
                        }

                        EdgeMode.NEW_EDGE_TO_EDGE_NAVIGATION_GRAY -> {
                            //1調整 View 邊界延展 上下左右都延伸
                            v.setPadding(0, 0, 0, 0)
                            //2調整 View 內容多留邊界內容，內容不被 status bar 跟 navigation bar 擋住
                            adjustView(contentLayout, toolBarLayout, systemBars)
                        }

                        else -> {}
                    }
                }

                else -> {}
            }

            insets
        }
    }

    /** 此為一步一步教學案例 使用 enableEdgeToEdgeMode 即可
     * @param bindingView ViewBinding 的 View
     * @param toolBarLayout Toolbar 的 Layout 若有就要對上界處理
     * @param contentLayout 若有 Toolbar 則只做下界處理，若沒有 Toolbar 則上、下界處理
     * @param modeSelect EdgeMode 的選項
     */
    fun enableEdgeToEdgeModeByStep(
        bindingView: View,
        contentLayout: ViewGroup,
        modeSelect: EdgeMode,
        toolBarLayout: ViewGroup? = null
    ) {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(bindingView) { v, insets ->
            val orientationMode = resources.configuration.orientation
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            when (orientationMode) {
                Configuration.ORIENTATION_LANDSCAPE -> {

                    when (modeSelect) {
                        EdgeMode.STEP4_1 -> {
                            v.setPadding(0, 0, 0, 0)
                            if (toolBarLayout != null) {
                                val paramToolBar =
                                    toolBarLayout.layoutParams as ViewGroup.MarginLayoutParams
                                paramToolBar.setMargins(
                                    systemBars.left,
                                    systemBars.top,
                                    systemBars.right,
                                    0
                                )
                                toolBarLayout.layoutParams = paramToolBar
                                val paramContent =
                                    contentLayout.layoutParams as ViewGroup.MarginLayoutParams
                                paramContent.setMargins(
                                    systemBars.left,
                                    0,
                                    systemBars.right,
                                    systemBars.bottom
                                )
                                contentLayout.layoutParams = paramContent

                            } else {
                                val param =
                                    contentLayout.layoutParams as ViewGroup.MarginLayoutParams
                                param.setMargins(
                                    systemBars.left,
                                    systemBars.top,
                                    systemBars.right,
                                    systemBars.bottom
                                )
                                contentLayout.layoutParams = param
                            }

                            if (Build.VERSION.SDK_INT >= ANDROID_VERSION_Q_10) {
                                window.isNavigationBarContrastEnforced = false
                            }
                        }

                        EdgeMode.STEP4_2 -> {
                            //1
                            v.setPadding(0, 0, 0, 0)
                            //2
                            if (toolBarLayout != null) {
                                val paramToolBar =
                                    toolBarLayout.layoutParams as ViewGroup.MarginLayoutParams
                                paramToolBar.setMargins(0, systemBars.top, 0, 0)
                                toolBarLayout.layoutParams = paramToolBar
                                val paramContent =
                                    contentLayout.layoutParams as ViewGroup.MarginLayoutParams
                                paramContent.setMargins(0, 0, 0, systemBars.bottom)
                                contentLayout.layoutParams = paramContent

                            } else {
                                val param =
                                    contentLayout.layoutParams as ViewGroup.MarginLayoutParams
                                param.setMargins(0, systemBars.top, 0, systemBars.bottom)
                                contentLayout.layoutParams = param
                            }

                            //失效 移除
//                            if (Build.VERSION.SDK_INT >= androidVersionQ10) {
//                                window.isNavigationBarContrastEnforced = false
//                            }

                            //3判斷是否為 Navigation for Buttons
                            if (systemBars.left > 0 || systemBars.right > 0) {
                                val controller =
                                    WindowCompat.getInsetsController(window, bindingView)

                                controller.hide(WindowInsetsCompat.Type.navigationBars())
                                controller.systemBarsBehavior =
                                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                            }

                        }

                        else -> {
                            //Final
                            //1調整 View 邊界延展 上下左右都延伸
                            v.setPadding(0, 0, 0, 0)
                            //2調整 View 內容多留邊界內容，內容不被 status bar 跟 navigation bar 擋住
                            //LANDSCAPE 的 navigation bar for Buttons 是在左右邊
                            //LANDSCAPE 的 navigation bar for Swipe Gestures 是在下方
                            //強制左右邊界為 0
                            adjustView(contentLayout, toolBarLayout, systemBars)
                            //3判斷是否為 Navigation for Buttons
                            if (systemBars.left > 0 || systemBars.right > 0) {
                                val controller =
                                    WindowCompat.getInsetsController(window, bindingView)

                                controller.hide(WindowInsetsCompat.Type.navigationBars())
                                controller.systemBarsBehavior =
                                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                            }
                        }
                    }
                }

                else -> {
                    //Configuration.ORIENTATION_UNDEFINED
                    //Configuration.ORIENTATION_PORTRAIT
                    when (modeSelect) {
                        EdgeMode.STEP1 -> {
                            v.setPadding(0, 0, 0, 0)
                        }

                        EdgeMode.STEP2 -> {
                            v.setPadding(0, 0, 0, 0)
                            adjustView(contentLayout, toolBarLayout, systemBars)
                        }

                        EdgeMode.STEP3 -> {
                            //1調整 View 邊界延展 上下左右都延伸
                            v.setPadding(0, 0, 0, 0)
                            //2調整 View 內容多留邊界內容，內容不被 status bar 跟 navigation bar 擋住
                            adjustView(contentLayout, toolBarLayout, systemBars)
                            //3 Navigation Bar 透明
                            if (Build.VERSION.SDK_INT >= ANDROID_VERSION_Q_10) {
                                window.isNavigationBarContrastEnforced = false
                            }
                        }

                        EdgeMode.NEW_ORIGINAL -> {
                            v.setPadding(
                                systemBars.left,
                                systemBars.top,
                                systemBars.right,
                                systemBars.bottom
                            )
                        }

                        EdgeMode.NEW_EDGE_TO_EDGE -> {
                            //1調整 View 邊界延展 上下左右都延伸
                            v.setPadding(0, 0, 0, 0)
                            //2調整 View 內容多留邊界內容，內容不被 status bar 跟 navigation bar 擋住
                            adjustView(contentLayout, toolBarLayout, systemBars)
                            //3 Navigation Bar 透明
                            if (Build.VERSION.SDK_INT >= ANDROID_VERSION_Q_10) {
                                window.isNavigationBarContrastEnforced = false
                            }
                        }

                        EdgeMode.NEW_EDGE_TO_EDGE_NAVIGATION_GRAY -> {
                            //1調整 View 邊界延展 上下左右都延伸
                            v.setPadding(0, 0, 0, 0)
                            //2調整 View 內容多留邊界內容，內容不被 status bar 跟 navigation bar 擋住
                            adjustView(contentLayout, toolBarLayout, systemBars)
                        }

                        else -> {}
                    }
                }

            }

            insets
        }
    }


    private fun adjustView(
        contentLayout: ViewGroup,
        toolBarLayout: ViewGroup?,
        systemBars: Insets
    ) {
        if (toolBarLayout != null) {
            val paramToolBar = toolBarLayout.layoutParams as ViewGroup.MarginLayoutParams
            paramToolBar.setMargins(0, systemBars.top, 0, 0)
            toolBarLayout.layoutParams = paramToolBar

            val paramContent = contentLayout.layoutParams as ViewGroup.MarginLayoutParams
            if (contentLayout is BottomNavigationView) {
                paramContent.setMargins(0, 0, 0, 0)
            } else {
                paramContent.setMargins(0, 0, 0, systemBars.bottom)
            }
            contentLayout.layoutParams = paramContent

        } else {
            val param = contentLayout.layoutParams as ViewGroup.MarginLayoutParams
            param.setMargins(0, systemBars.top, 0, systemBars.bottom)
            contentLayout.layoutParams = param
        }
    }

    fun setBundle(packContext: Context, intent: Intent, logContent: String, isIntent: Boolean) {
//        val activityData = intent.extras?.getString(toActivityData)
//        val fragmentData = intent.extras?.getString(toFragmentData)
        val activityData: NotificationActivityData? = getNotificationActivityBundleData(intent)
        val fragmentData: NotificationFragmentData? = getNotificationFragmentBundleData(intent)

        val intentNew = Intent()
        val bundle = Bundle()

        if (isIntent) {
            when (activityData?.name) {
                "SystemStateRecoveryActivity" -> {

                    Log.d(logContent, "Intent data is $activityData")
                    bundle.putSerializable(TO_ACTIVITY_DATA_KEY, activityData)
                    bundle.putSerializable(TO_FRAGMENT_DATA_KEY, fragmentData)

                    intentNew.putExtras(bundle)
                    intentNew.setClass(packContext, SystemStateRecoveryActivity::class.java)
                    startActivity(intentNew)
                }

                "NotificationActivity" -> {
                    Log.d(logContent, "Intent data is $activityData")
                    bundle.putSerializable(TO_ACTIVITY_DATA_KEY, activityData)
                    intentNew.putExtras(bundle)
                    intentNew.setClass(packContext, NotificationActivity::class.java)
                    startActivity(intentNew)
                }

                "HomeActivity" -> {
                    Log.d(logContent, "Intent data is $activityData")
                    bundle.putSerializable(TO_ACTIVITY_DATA_KEY, activityData)
                    bundle.putSerializable(TO_FRAGMENT_DATA_KEY, fragmentData)
                    intentNew.putExtras(bundle)
                    intentNew.setClass(packContext, HomeActivity::class.java)
                    startActivity(intentNew)
                }

                else -> {
                    Log.e(logContent, "Intent data is null")
                }
            }
        } else {
            if (activityData != null) {
                Log.d(logContent, "Intent data is $activityData")
            } else {
                Log.e(logContent, "Intent data is null")
            }
        }
    }

    fun getNotificationActivityBundleData(intent: Intent): NotificationActivityData? {
        var activityData: NotificationActivityData? = null
        try {
            if (Build.VERSION.SDK_INT >= ANDROID_VERSION_TIRAMISU_13) {
                val ff = 22
                activityData = intent.extras?.getSerializable(
                    TO_ACTIVITY_DATA_KEY,
                    NotificationActivityData::class.java
                )
            } else {
                if (intent.extras != null) {
                    activityData =
                        intent.extras?.getSerializable(TO_ACTIVITY_DATA_KEY) as NotificationActivityData
                }
            }
        } catch (E: Exception) {
            val fff = E.message.toString()
            Log.e("fff", fff)
        }


        return activityData
    }

    fun getNotificationFragmentBundleData(intent: Intent): NotificationFragmentData? {
        var fragmentData: NotificationFragmentData? = null
        if (Build.VERSION.SDK_INT >= ANDROID_VERSION_TIRAMISU_13) {
            fragmentData = intent.extras?.getSerializable(
                TO_FRAGMENT_DATA_KEY,
                NotificationFragmentData::class.java
            )
        } else {
            if (intent.extras != null) {
                fragmentData =
                    intent.extras?.getSerializable(TO_FRAGMENT_DATA_KEY) as NotificationFragmentData
            }
        }

        return fragmentData
    }

    fun setPhoneOrTabletOrientation() {
        val configuration = resources.configuration
        Log.e(
            "${javaClass.simpleName}-smallestScreenWidthDp",
            configuration.smallestScreenWidthDp.toString()
        )
        Log.e("${javaClass.simpleName}-screenWidthDp", configuration.screenWidthDp.toString())

        if (isTablet() && configuration.screenWidthDp >= 600) {
//            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        }
        else{
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    private fun isTablet():Boolean {
        val configuration = resources.configuration
        val isTabletCheck = (configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK)>= Configuration.SCREENLAYOUT_SIZE_LARGE
        return isTabletCheck
    }


}