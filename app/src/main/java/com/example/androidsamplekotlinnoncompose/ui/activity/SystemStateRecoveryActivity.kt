package com.example.androidsamplekotlinnoncompose.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.example.androidsamplekotlinnoncompose.EdgeMode
import com.example.androidsamplekotlinnoncompose.R
import com.example.androidsamplekotlinnoncompose.databinding.ActivitySystemStateRecoveryBinding
import com.example.androidsamplekotlinnoncompose.ui.fragment.StateRecoveryFragment
import com.example.androidsamplekotlinnoncompose.utility.ActivityLifeCycleHelper
import com.example.androidsamplekotlinnoncompose.utility.MyApplication

private const val ACTIVITY_TAG_TV: String = "TagTVContent"
private const val ACTIVITY_TAG_BACK_LAUNCHER_STATE: String = "TagBAckLauncherState"
private const val FRAGMENT_DATA = "fragmentData"

class SystemStateRecoveryActivity : BaseActivity() {
    private lateinit var binding: ActivitySystemStateRecoveryBinding
    private var tvStateRecovery: String? = null

    //是否要執行 AMS 恢復流程
    private var isBackingLauncherActivity = false

    //是否要使用 fragment  add  若是使用 add，建議要使用 Viewmodel + SavedStateHandle
    //若是使用 replace 就直接取代所有現行的 fragment
    private val isUseAdd = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySystemStateRecoveryBinding.inflate(layoutInflater)
        enableEdgeToEdgeMode(binding.root, binding.mainContainerCL, EdgeMode.NEW_EDGE_TO_EDGE)
        setContentView(binding.root)


        setUI(savedInstanceState)
        if (savedInstanceState != null) {
            tvStateRecovery = savedInstanceState.getString(ACTIVITY_TAG_TV)
            tvStateRecovery?.apply {
                binding.tvTestState1.text = tvStateRecovery
            }
//            Log.e("State-onCreate", "System-onCreate: ${tvStateRecovery}")
            Log.e("System-State-Recovery-Activity", "onCreate-savedInstanceState != null")
        } else {
            Log.e("System-State-Recovery-Activity", "onCreate-savedInstanceState is null")
        }

        setBundle(this@SystemStateRecoveryActivity, intent, "State-onCreate-Bundle", false)

    }

    override fun onResume() {
        super.onResume()
        Log.e("System-State-Recovery-Activity", "onResume")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.e("System-State-Recovery-Activity", "onSaveInstanceState")
        outState.putString(ACTIVITY_TAG_TV, binding.tvTestState1.text.toString())
        outState.putBoolean(ACTIVITY_TAG_BACK_LAUNCHER_STATE, isBackingLauncherActivity)
//        Log.e("TAG20", "onSaveInstanceState: ${binding.tvTestState1.text}")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        tvStateRecovery = savedInstanceState.getString(ACTIVITY_TAG_TV)
        tvStateRecovery?.apply {
            binding.tvTestState1.text = tvStateRecovery
        }
        Log.e("System-State-Recovery-Activity", "onRestoreInstanceState")
//        Log.e("SystemS-", "onRestoreInstanceState: ${tvStateRecovery}")
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setBundle(this@SystemStateRecoveryActivity, intent, "State-onNewIntent-Bundle", false)
    }

//    private fun setBindingAndEdgeToEdgeUI() {
//        enableEdgeToEdge()
//        binding = ActivitySystemStateRecoveryBinding.inflate(layoutInflater)
//        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
//            //調整View
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, 0, systemBars.right, 0)
//            //調整底部元件距離、頂部元件距離 (由於此案例是剛好同一元件就改一個元件，若不同元件就分開處理)
//            val param = binding.LiveDataCL.layoutParams as ViewGroup.MarginLayoutParams
//            param.setMargins(0, systemBars.top, 0, systemBars.bottom)
//            binding.LiveDataCL.layoutParams = param
//
//            insets
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            window.isNavigationBarContrastEnforced = false
//        }
//    }

    private fun setUI(savedInstanceState: Bundle?) {
        val isSystemRecovery = savedInstanceState != null

        if (isSystemRecovery) {
            isBackingLauncherActivity =
                savedInstanceState!!.getBoolean(ACTIVITY_TAG_BACK_LAUNCHER_STATE)
        }

        if (isBackingLauncherActivity) {
            binding.tvTitle2.text = "Low Memory -> Return Launch"
        } else {
            binding.tvTitle2.text = "Low Memory -> State Recovery"
        }

        Log.e("TAG23-isSystemRecovery", "onCreate() setUI: $isSystemRecovery")
        Log.e("TAG23-isBackingLauncherActivity", "onCreate() setUI: $isBackingLauncherActivity")


        if (isBackingLauncherActivity && isSystemRecovery) {
            backToLauncherActivity()
            return
        }

        binding.btnChangeLowMemoryBehaviour.setOnClickListener {
            isBackingLauncherActivity = !isBackingLauncherActivity

            if (isBackingLauncherActivity) {
                binding.tvTitle2.text = "Low Memory -> Return Launch"
            } else {
                binding.tvTitle2.text = "Low Memory -> State Recovery"
            }
        }

        binding.btnChangeData.setOnClickListener {
            binding.tvTestState1.text = "Change GO GO"
        }


        when (isUseAdd) {
            true -> {
                if (!isSystemRecovery) {
                    supportFragmentManager.commit {
                        if (isUseAdd) {
                            if (!isSystemRecovery) {
                                //add
                                add(R.id.fragmentContainer, StateRecoveryFragment())
                                //允許重新排序片段狀態變更，FragmentTransaction 都應使用
                                setReorderingAllowed(true)
                                //addToBackStack(null)
                            }
                        } else {
                            //replace
                            replace(R.id.fragmentContainer, StateRecoveryFragment())
                            //允許重新排序片段狀態變更，FragmentTransaction 都應使用
                            setReorderingAllowed(true)
                            //addToBackStack(null)
                        }
                    }
                }
            }

            else -> {

            }
        }

        onBackPressedDispatcher.addCallback {
            finish()
        }
    }

    private fun backToLauncherActivity() {
        Intent(this, MainActivity::class.java).apply {
            this.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(this)
        }
    }
}