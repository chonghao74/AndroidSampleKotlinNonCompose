package com.example.androidsamplekotlinnoncompose.ui.activity

import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.example.androidsamplekotlinnoncompose.EdgeMode
import com.example.androidsamplekotlinnoncompose.databinding.ActivityClAblClTbNlBinding
import com.example.androidsamplekotlinnoncompose.utility.dpToPx


class CoordinatorLAppBarLCollapseToolbarNestSActivity : BaseActivity() {
    private lateinit var binding: ActivityClAblClTbNlBinding

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        binding.tvTitle.text = "UUU"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClAblClTbNlBinding.inflate(layoutInflater)
        enableEdgeToEdgeMode(binding.root, binding.mainContainerCL, EdgeMode.NEW_EDGE_TO_EDGE, binding.toolbar)
        setContentView(binding.root)
    }

    private fun setUI() {

    }
}