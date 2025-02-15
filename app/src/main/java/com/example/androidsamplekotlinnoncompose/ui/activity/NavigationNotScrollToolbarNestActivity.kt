package com.example.androidsamplekotlinnoncompose.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.example.androidsamplekotlinnoncompose.EdgeMode
import com.example.androidsamplekotlinnoncompose.R
import com.example.androidsamplekotlinnoncompose.databinding.ActivityNavigationNotScrollToolbarNestBinding
import com.example.androidsamplekotlinnoncompose.utility.NavHostFragmentAdjustAddHideShow
import com.example.androidsamplekotlinnoncompose.viewmodel.ActivityNavigationCommonViewModel
import com.google.android.material.badge.BadgeDrawable

class NavigationNotScrollToolbarNestActivity : BaseNaviActivity() {
    lateinit var binding: ActivityNavigationNotScrollToolbarNestBinding

    //    private val navigationViewModel by viewModels<NavigationTestViewModel>()
    private val activityNavigationViewModel by viewModels<ActivityNavigationCommonViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavigationNotScrollToolbarNestBinding.inflate(layoutInflater)
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
        setNavigation()
    }

    private fun setNavigation() {
        setNavigationItemInit()
        setNavigationBadge()
    }

    private fun setNavigationItemInit() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragmentAdjustAddHideShow
        navController = navHostFragment.navController
//        NavigationUI.setupWithNavController(binding.navView, navController)

        //透過 NavigationUI 切換 (切換就會 replace 且最多只能保留第一個 item Fragment)
//        binding.navView.setOnItemSelectedListener { item ->
//            NavigationUI.onNavDestinationSelected(item, navController)
//            true
//        }

//      自行切換
        binding.navView.setOnItemSelectedListener { item ->
            val itemData: Int?
            if (item.isChecked) {
                false
            } else {
                when (item.itemId) {
                    R.id.navFirstFragment -> {
                        itemData = activityNavigationViewModel.getItemFirstId()
                        if (itemData == null) {
                            navController.navigate(R.id.navigation_sub_first_graph, null)
                        } else {
                            navController.navigate(itemData, null)
                        }
                    }

                    R.id.navDashboardFragment -> {
                        itemData = activityNavigationViewModel.getItemSecondId()
                        if (itemData == null) {
                            navController.navigate(R.id.navDashboardFragment, null)
                        } else {
                            navController.navigate(itemData, null)
                        }

                    }

                    R.id.navListFragment -> {
                        itemData = activityNavigationViewModel.getItemThirdId()
                        if (itemData == null) {
                            navController.navigate(R.id.navListFragment, null)
                        } else {
                            navController.navigate(R.id.navListFragment, null)
                        }
                    }

                    R.id.navSettingFragment -> {
                        itemData = activityNavigationViewModel.getItemFourthId()
                        if (itemData == null) {
                            navController.navigate(R.id.navSettingFragment, null)
                        } else {
                            navController.navigate(R.id.navSettingFragment, null)
                        }
                    }
                }

                true
            }

        }
    }

    private fun setNavigationBadge() {
        binding.navView.getOrCreateBadge(R.id.navDashboardFragment).number = 2000
        val badge01 = binding.navView.getBadge(R.id.navDashboardFragment)
        val badgeAll = binding.navView.getOrCreateBadge(R.id.navDashboardFragment)//一定不會為 null
        badgeAll.apply {
            backgroundColor = getColor(R.color.black_white)
            badgeTextColor = getColor(R.color.white_black)
            isVisible = true //是否顯示
            //maxNumber = 100  //最大數字，若超過就會 (maxNumber+舉例 100+)
            number = 2000
            maxCharacterCount = 4
            badgeGravity = BadgeDrawable.TOP_START
        }
    }


}