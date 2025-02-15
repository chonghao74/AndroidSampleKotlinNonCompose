package com.example.androidsamplekotlinnoncompose.utility

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.example.androidsamplekotlinnoncompose.BuildConfig


class ActivityLifeCycleHelper : Application.ActivityLifecycleCallbacks {
    private var onEnterForegroundListener: OnEnterForegroundListener? = null
    var numStarted: Int = 0; //Detect 前景或背景
    var activityName: String? = null; //Detect 目前 Activity
    var isExitMainActivity = false //如果沒有主頁，則都會先跳至主頁，再跳至其他頁面


    companion object {
        private var instance: ActivityLifeCycleHelper? = null

        fun getInstance(application: Application): ActivityLifeCycleHelper {
            if (instance == null) {
                instance = ActivityLifeCycleHelper()
                application.registerActivityLifecycleCallbacks(instance)
            }
            return instance!!
        }
    }

    fun setOnEnterForegroundListener(listener: OnEnterForegroundListener?) {
        this.onEnterForegroundListener = listener
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        val data = activity.localClassName.substring(activity.localClassName.lastIndexOf(".")+1)
        if( data !="SingleActivity"){
            activityName = data

            if (data == BuildConfig.MAIN_ACTIVITY_NAME) {
                isExitMainActivity = true
            }
        }
    }

    override fun onActivityStarted(activity: Activity) {
        if (numStarted == 0) {
            onEnterForegroundListener?.onEnterForeground()
        }

        numStarted++
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
        numStarted--
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

    interface OnEnterForegroundListener {
        fun onEnterForeground()
    }
}

