package com.example.androidsamplekotlinnoncompose.utility

import android.app.Application
import android.util.Log
import com.example.androidsamplekotlinnoncompose.BuildConfig
import timber.log.Timber

class MyApplication : Application() {
    var fragmentName: String? = null; //Detect 目前 Activity
    private val tag: String = "-${javaClass.simpleName}-"

    override fun onCreate() {
        super.onCreate()
        // 若在非正式版狀態，才初始化 Timber 套件
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        //註冊
        ActivityLifeCycleHelper.getInstance(this).setOnEnterForegroundListener(
            object : ActivityLifeCycleHelper.OnEnterForegroundListener {
                override fun onEnterForeground() {
                    Timber.tag(tag).e("ActivityLifeCycleHelper - onEnterForeground")
//                    Log.e(tag, "ActivityLifeCycleHelper - onEnterForeground")
                }
            }
        )


    }

    override fun onTerminate() {
        super.onTerminate()
        //移除註冊
        unregisterActivityLifecycleCallbacks(ActivityLifeCycleHelper.getInstance(this))
    }
}