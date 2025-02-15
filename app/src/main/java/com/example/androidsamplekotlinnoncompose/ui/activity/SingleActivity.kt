package com.example.androidsamplekotlinnoncompose.ui.activity

import android.app.ActivityManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.androidsamplekotlinnoncompose.BuildConfig
import com.example.androidsamplekotlinnoncompose.utility.ActivityLifeCycleHelper
import com.example.androidsamplekotlinnoncompose.utility.MyApplication


//private const val CLEAR_ALL_AND_TO_LAUNCHER_TAG = "1"
//private const val NOT_CLEAR_ALL_AND_TO_LAUNCHER_TAG = "2"
//private const val INTENT_TO_ACTIVITY = "3"

class SingleActivity : BaseActivity() {
    override fun onDestroy() {
        super.onDestroy()
//        intent.setAction("")
//        intent.setData(null)
//        intent.setFlags(0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val detectNumber = ActivityLifeCycleHelper.getInstance(myApplication).numStarted
        val detectActivityName = ActivityLifeCycleHelper.getInstance(myApplication).activityName

        Log.e("Single-onCreate", "ActivityLifeCycleHelper activityName is  - ${detectActivityName}")
        Log.e("Single-onCreate", "Intent data is  - ${intent.extras?.getString("data")}")
        if(detectNumber != 0) Log.e("Single-onCreate", "前景") else Log.e("Single-onCreate", "背景")


        if (((intent.flags and Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY)) == 0) {
            Log.e("Single-onCreate", "TRUE_FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY")

            if(intent.extras != null){
                intent.extras?.apply {
                    val activityData = getNotificationActivityBundleData(intent)
                    val behavior = this.getString(LIFECYCLE_BEHAVIOR_KEY)

                    when(behavior){
                        CLEAR_ALL_AND_TO_LAUNCHER_TAG ->{
                            Log.e("Single-onCreate", "CLEAR_ALL")
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            intent.setClass(this@SingleActivity, MainActivity::class.java)
                        }
                        NOT_CLEAR_ALL_AND_TO_LAUNCHER_TAG ->{
                            Log.e("Single-onCreate", "NOT_CLEAR_ALL")
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            intent.setClass(this@SingleActivity, MainActivity::class.java)
                        }
                        INTENT_TO_ACTIVITY_TAG->{
                            Log.e("Single-onCreate", "INTENT_TO")
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

                            //判斷 Memory 是否還存在，不管是 all clear 或者 部分 clear 都會為 null
                            //此 if ..else 是為了測試 Memory 是否還存在 寫的 Log
                            if(detectActivityName != null){
                                Log.e("Single-onCreate", "Record  與 AMS stack 都還存活")
                                if(activityData?.name == detectActivityName){
                                    Log.e("Single-onCreate", "前景 Activity 與 特定 Activity 相同")

                                    if(isExistActivity()){
                                        Log.e("Single-onCreate", "${BuildConfig.MAIN_ACTIVITY_NAME} 存在")
                                        Log.e("Single-onCreate", "直接跳至 ${activityData}")
                                    }
                                    else{
                                        Log.e("Single-onCreate", "${BuildConfig.MAIN_ACTIVITY_NAME} 不存在，可能被手動於滑掉")
                                        Log.e("Single-onCreate", "跳至 ${BuildConfig.MAIN_ACTIVITY_NAME} 並看 ${activityData}再處理")
                                    }
                                }
                                else{
                                    Log.e("Single-onCreate", "前景 Activity 與 特定 Activity 不相同")
                                    Log.e("Single-onCreate", "跳至 ${BuildConfig.MAIN_ACTIVITY_NAME} 並看 ${activityData}再處理")
                                }
                            }
                            else{
                                Log.e("Single-onCreate", "Record stack killer， 但 AMS stack 還存在 or 兩個都都 Killer ")
                                Log.e("Single-onCreate", "跳至 ${BuildConfig.MAIN_ACTIVITY_NAME}")
                                Log.e("Single-onCreate", "若 Record stack killer， 但 AMS stack 還存在")
                                Log.e("Single-onCreate", " ${BuildConfig.MAIN_ACTIVITY_NAME} 進入 onCreate-> onNewIntent")
                                Log.e("Single-onCreate", "若 兩個都都 Killer")
                                Log.e("Single-onCreate", " ${BuildConfig.MAIN_ACTIVITY_NAME} 進入 onCreate")
                            }

                            //此邊才是走流程
                            if(activityData?.name == detectActivityName && isExistActivity()){
                                when(activityData?.name){
                                    "NotificationActivity" ->
                                        intent.setClass(this@SingleActivity, NotificationActivity::class.java)
                                    "SystemStateRecoveryActivity"->
                                        intent.setClass(this@SingleActivity, SystemStateRecoveryActivity::class.java)
                                    "HomeActivity"->
                                        intent.setClass(this@SingleActivity, HomeActivity::class.java)
                                    else ->
                                        intent.setClass(this@SingleActivity, MainActivity::class.java)
                                }
                            }
                            else{
                                intent.setClass(this@SingleActivity, MainActivity::class.java)
                            }
                        }
                        else ->{
                            Log.e("Single-onCreate", "else")
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            intent.setClass(this@SingleActivity, MainActivity::class.java)
                        }
                    }
                }
            }
            else{
                Log.e("Single-onCreate", "Bundle is Null")
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.setClass(this@SingleActivity, MainActivity::class.java)
            }
        }
        else{
            Log.e("Single-onCreate", "FALSE_FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY")

            val bundleNew = Bundle()
            bundleNew.putString("data", null)
            intent.putExtras(bundleNew)

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.setClass(this@SingleActivity, MainActivity::class.java)
        }

        startActivity(intent)
        finish()
    }

    private fun isExistActivity():Boolean{
        val isExitActivity = ActivityLifeCycleHelper.getInstance(myApplication).isExitMainActivity

        if(isExitActivity){
            Log.e("Single-onCreate", "isExist Activity is true")
            return true
        }
        else{
            Log.e("Single-onCreate", "isExist Activity is false")
            return false
        }
    }


}