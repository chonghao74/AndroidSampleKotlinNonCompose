package com.example.androidsamplekotlinnoncompose.ui.activity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.os.LocaleListCompat
import com.example.androidsamplekotlinnoncompose.BuildConfig
import com.example.androidsamplekotlinnoncompose.EdgeMode
import com.example.androidsamplekotlinnoncompose.R
import com.example.androidsamplekotlinnoncompose.databinding.ActivityMainBinding


class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setBundle(this@MainActivity, intent, "Main-onNewIntent-Bundle", true)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdgeMode(binding.root, binding.mainContainerCL, EdgeMode.NEW_EDGE_TO_EDGE)
//        enableEdgeToEdgeModeByStep(binding.root, binding.mainContainerCL, EdgeMode.STEP1)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            Log.e("Main-onCreate-saved", "savedInstanceState != null")
        } else {
            Log.e("Main-onCreate-saved", "savedInstanceState is null")
        }

        setUI()
        setBundle(this@MainActivity, intent, "Main-onCreate-Bundle", true)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

//        val logData = when (AppCompatDelegate.getDefaultNightMode()) {
//            AppCompatDelegate.MODE_NIGHT_UNSPECIFIED -> "UNSPECIFIED"
//            AppCompatDelegate.MODE_NIGHT_YES -> "NIGHT_YES"
//            AppCompatDelegate.MODE_NIGHT_NO -> "NIGHT_NO"
//            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> "FOLLOW_SYSTEM"
//            else -> "Error"
//        }

        when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_UNSPECIFIED,
            AppCompatDelegate.MODE_NIGHT_YES,
            AppCompatDelegate.MODE_NIGHT_NO,
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> {
                //Background Color or Drawable Good
//                binding.main.setBackgroundColor(ContextCompat.getColor(this, R.color.black_white))
                binding.main.background = ContextCompat.getDrawable(this, R.color.black_white)
                binding.main.setBackgroundColor(
                    resources.getColor(
                        R.color.black_white,
                        theme
                    )
                )
//                binding.main.background = ContextCompat.getDrawable(this, R.drawable.baseline_close_24)

                //Background Color Good
//              binding.main.background = ContextCompat.getDrawable(this, R.drawable.baseline_close_24)//Background Drawable Good
                binding.btnHome.setTextColor(
                    resources.getColorStateList(
                        R.color.select_text_color_black_white,
                        theme
                    )
                )
                //Text Selector Color Good
//              binding.btnHome.setTextColor(ContextCompat.getColorStateList(this, R.color.select_text_color_black_white))//


                Log.e("Color", "${binding.main.background}")
                Log.e("TextColor", "${binding.btnHome.textColors}")
            }

            else -> {
                Log.e("MainActivity onConfigurationChanged", "Error")
            }
        }
    }

    private fun setUI() {
        binding.tvApplicationID.text = BuildConfig.APPLICATION_ID


        binding.btnTestProgress.setOnClickListener {
            startActivity(Intent(this, ProgressActivity::class.java))
        }

        binding.btnToRoomDemo.setOnClickListener {
            startActivity(Intent(this, RoomDemoActivity::class.java))
        }

        binding.btnToToolbar.setOnClickListener {
            startActivity(Intent(this, CoordinatorLAppBarLToolbarNestSActivity::class.java))
        }

        binding.btnToCollapseToolbar.setOnClickListener {
            startActivity(Intent(this, CoordinatorLAppBarLCollapseToolbarNestSActivity::class.java))
        }

        binding.btnTestDarkMode.setOnClickListener {
            binding.btnTestDarkMode.text = "YES"
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        binding.btnTestNotDarkMode.setOnClickListener {
            binding.btnTestNotDarkMode.text = "NO"
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        binding.btnUnspecifiedMode.setOnClickListener {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_UNSPECIFIED)
            binding.btnUnspecifiedMode.text = "FOLLOW"
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }

        binding.btnHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        binding.btnDialogFragment.setOnClickListener {
            startActivity(Intent(this, DialogFragmentActivity::class.java))
        }

        binding.btnViewModel.setOnClickListener {
            startActivity(Intent(this, ViewModelActivity::class.java))
        }

        binding.btnStateRecovery.setOnClickListener {
            startActivity(Intent(this, SystemStateRecoveryActivity::class.java))
        }

        binding.btnIntentNotification.setOnClickListener {
            startActivity(Intent(this, NotificationActivity::class.java))
        }

        binding.btnIntentNavigationToolBar.setOnClickListener {
            startActivity(Intent(this, NavigationScrollToolbarActivity::class.java))
        }

        binding.btnIntentNavigationNoToolBar.setOnClickListener {
            startActivity(Intent(this, NavigationNotScrollToolbarActivity::class.java))
        }

        binding.btnIntentNavigationNotScrollToolBarNest.setOnClickListener {
            startActivity(Intent(this, NavigationNotScrollToolbarNestActivity::class.java))
        }

        binding.btnTestLocaleZH.setOnClickListener {
            val locale = resources.configuration.locales[0]
            Log.e("MainActivity-Locale", locale.toString())
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("zh-TW"))
        }
        binding.btnTestLocaleEN.setOnClickListener {
            val locale = resources.configuration.locales[0]
            Log.e("MainActivity-Locale", locale.toString())
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
        }
        binding.btnTestLocaleDefault.setOnClickListener {
            val locale = resources.configuration.locales[0]
            Log.e("MainActivity-Locale", locale.toString())
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.getDefault())
        }

        onBackPressedDispatcher.addCallback {
            finish()
        }

        setPhoneOrTabletOrientation()
    }

}