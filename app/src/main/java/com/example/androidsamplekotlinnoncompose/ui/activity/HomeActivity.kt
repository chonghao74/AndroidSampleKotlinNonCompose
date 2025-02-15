package com.example.androidsamplekotlinnoncompose.ui.activity

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import com.example.androidsamplekotlinnoncompose.EdgeMode
import com.example.androidsamplekotlinnoncompose.R
import com.example.androidsamplekotlinnoncompose.databinding.ActivityHomeBinding
import com.example.androidsamplekotlinnoncompose.repository.model.user.LocalUser
import com.example.androidsamplekotlinnoncompose.ui.fragment.HomeFragment
import com.example.androidsamplekotlinnoncompose.ui.fragment.HomeFragment2
import com.example.androidsamplekotlinnoncompose.ui.fragment.TestDialogByCreateDialogFragment
import com.example.androidsamplekotlinnoncompose.utility.MyApplication
import com.example.androidsamplekotlinnoncompose.viewmodel.UserBySaveStateViewModel
import com.example.androidsamplekotlinnoncompose.viewmodel.UserStateRecoveryViewModel
import com.example.androidsamplekotlinnoncompose.viewmodel.UserViewModel

class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val userViewModel by viewModels<UserViewModel>()
    private lateinit var userActivityBySaveStateViewModel: UserBySaveStateViewModel
//    private val userActivityBySaveStateViewModel by viewModels<UserBySaveStateViewModel>{
//        SavedStateViewModelFactory(application, this)
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        enableEdgeToEdgeMode(
            binding.root,
            binding.mainContainerCL,
            EdgeMode.NEW_EDGE_TO_EDGE,
            binding.toolbar
        )
        setContentView(binding.root)
        setViewModel(savedInstanceState == null)

        if (savedInstanceState == null) {
            val fragmentData = getNotificationFragmentBundleData(intent)
            val fragmentNow = myApplication.fragmentName
            if (fragmentData != null) {
                userViewModel.setUser(LocalUser(age = fragmentData.bundleData1.toInt()))
            }
            setUI(true)
        } else {
            //作法 1
            setUI(false)
            //作法 2
        }


    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        val fragmentData = getNotificationFragmentBundleData(intent)
        val dataGet = fragmentData?.bundleData1?.toInt()
        val fragmentNow = myApplication.fragmentName
        if(fragmentData?.name === fragmentNow){
            if (fragmentData != null) {
                userViewModel.setUser(LocalUser(age = dataGet!!))
            }
        }

    }

    private fun setViewModel(isSavedInstanceStateNull: Boolean) {
        userActivityBySaveStateViewModel = ViewModelProvider(
            this,
            SavedStateViewModelFactory(application, this)
        )[UserBySaveStateViewModel::class.java]


        if(isSavedInstanceStateNull){
            userViewModel.setUser(LocalUser("Time2", 30))
            userActivityBySaveStateViewModel.setUser(LocalUser("Time2", 30))
        }
    }

    private fun setUI(isSavedInstanceStateNull: Boolean) {
        setToolBar()
        if (isSavedInstanceStateNull) {
            val fragment = HomeFragment.newInstance("1","1")
            setFragment1(fragment)
//            setFragment1(HomeFragment())
//        if(data)setFragmentTest2()
//        setFragment3(HomeFragment())
        }
    }

    //若發現 memory 移除，此方法為系統字型恢復  (setFragment1 與 setFragment2 都可以)
    private fun setFragment1(fragment: Fragment) {
        supportFragmentManager.commit {
            //Animation
            //add
            add(R.id.fragmentContainer, fragment)
            //replace
//            replace(R.id.fragmentContainer, fragment)
            //允許重新排序片段狀態變更，FragmentTransaction 都應使用
            setReorderingAllowed(true)
//            addToBackStack(null)
        }
    }

    //若發現 memory 移除，此方法為自己叫出已存在的  (setFragment1 與 setFragment2 都可以)
    private fun setFragment2() {
        supportFragmentManager.commit {
            if (supportFragmentManager.fragments.size > 0) {
                when (supportFragmentManager.fragments.get(supportFragmentManager.fragments.size)) {
                    is HomeFragment -> show(HomeFragment())
                    is HomeFragment2 -> show(HomeFragment2())
                }
            } else {
                add(R.id.fragmentContainer, HomeFragment())
                setReorderingAllowed(true)
            }
        }
    }

    //若發現 memory 移除，此方法就是移除所有的 fragment 然後再 add 第一個 fragment
    private fun setFragment3(fragment: Fragment) {
        supportFragmentManager.commit {
            //Animation
            //add
            replace(R.id.fragmentContainer, fragment)
            Log.e("setUI_setFragment3", supportFragmentManager.fragments.size.toString())
            //replace
//            replace(R.id.fragmentContainer, fragment)
            //允許重新排序片段狀態變更，FragmentTransaction 都應使用
            setReorderingAllowed(true)
//            addToBackStack(null)
        }
    }

    private fun setToolBar() {
        binding.toolbar.setNavigationOnClickListener {
//            supportFragmentManager.findFragmentById(binding.fragmentContainer.id)
            when (supportFragmentManager.findFragmentById(binding.fragmentContainer.id)) {
                is HomeFragment -> finish()
                else -> supportFragmentManager.popBackStack()
            }

        }
    }

    private fun processBundle() {

    }

}