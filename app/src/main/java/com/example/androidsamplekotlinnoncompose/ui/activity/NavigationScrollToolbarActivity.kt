package com.example.androidsamplekotlinnoncompose.ui.activity

import android.os.Bundle
import androidx.core.widget.NestedScrollView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.androidsamplekotlinnoncompose.EdgeMode
import com.example.androidsamplekotlinnoncompose.R
import com.example.androidsamplekotlinnoncompose.databinding.ActivityNavigationScrollToolbarBinding
import com.example.androidsamplekotlinnoncompose.ui.fragment.NavFirstFragment

class NavigationScrollToolbarActivity : BaseNaviActivity() {
    lateinit var binding: ActivityNavigationScrollToolbarBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationScrollToolbarBinding.inflate(layoutInflater)
        enableEdgeToEdgeMode(
            binding.root,
            binding.navView,
            EdgeMode.NEW_EDGE_TO_EDGE,
            binding.toolbar
        )
        setContentView(binding.root)
        setUI()
    }

    private fun setUI() {
        setToolBar(binding.toolbar)
        setNavigationUIInit()
    }

    private fun setNavigationUIInit() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.navView, navController)
    }
}