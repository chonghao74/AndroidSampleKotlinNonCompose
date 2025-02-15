package com.example.androidsamplekotlinnoncompose.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.androidsamplekotlinnoncompose.EdgeMode
import com.example.androidsamplekotlinnoncompose.R
import com.example.androidsamplekotlinnoncompose.databinding.ActivityNavigationNotScrollToolbarBinding
import com.example.androidsamplekotlinnoncompose.viewmodel.ActivityNavigationCommonViewModel

class NavigationNotScrollToolbarActivity : BaseNaviActivity() {
    lateinit var binding: ActivityNavigationNotScrollToolbarBinding
    private val activityNavigationViewModel by viewModels<ActivityNavigationCommonViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationNotScrollToolbarBinding.inflate(layoutInflater)
        enableEdgeToEdgeMode(binding.root, binding.navView, EdgeMode.NEW_EDGE_TO_EDGE, binding.toolbar)
        setContentView(binding.root)
        setUI()
    }

    private fun setUI() {
        setToolBar(binding.toolbar)
        setNavigationUIInit()
    }

    private fun setNavigationUIInit() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController
//        NavigationUI.setupWithNavController(binding.navView, navController)
        binding.navView.setOnItemSelectedListener { item ->
            val itemData: Int?
            if (item.isChecked) {
                false
            }
            else{
                when (item.itemId) {
                    R.id.navigation_sub_first_graph -> {
                        itemData = activityNavigationViewModel.getItemFirstId()
                        if (itemData == null) {
                            navController.navigate(R.id.navigation_sub_first_graph, null)
                        } else {
                            navController.popBackStack(itemData, false)
                        }
                    }

                    R.id.navDashboardFragment -> {
                        navController.navigate(R.id.navDashboardFragment, null)
                    }

                    R.id.navListFragment -> {
                        navController.navigate(R.id.navListFragment, null)
                    }

                    R.id.navSettingFragment -> {
                        navController.navigate(R.id.navSettingFragment, null)
                    }
                }

                true
            }
        }
    }
}