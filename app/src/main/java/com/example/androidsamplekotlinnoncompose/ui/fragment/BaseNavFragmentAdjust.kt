package com.example.androidsamplekotlinnoncompose.ui.fragment

import android.app.Application
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.SavedStateViewModelFactory
import com.example.androidsamplekotlinnoncompose.ui.activity.BaseNaviActivity
import com.example.androidsamplekotlinnoncompose.viewmodel.ActivityNavigationCommonViewModel

open class BaseNavFragmentAdjust<T>  : BaseFragment<T>() {
    private var isCloseBinding = false
    private var isFirstFragment = false
    var backId = 0 //儲存告至返回的 fragment id
    val activityViewModels by activityViewModels<ActivityNavigationCommonViewModel> {
        SavedStateViewModelFactory(activity?.application as Application, this)
    }

    override fun onResume() {
        super.onResume()
        isCloseBinding = false
        setFirstFragment(false)//只有第一個 item 需要覆寫改成 true
        setToolBarNavigationIconEvent(true)//只有第一個 item 需要覆寫改成 false
        (requireActivity() as BaseNaviActivity).setToolBarTitle(javaClass.simpleName)
        requireActivity().onBackPressedDispatcher.addCallback {
            navOnBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (isCloseBinding) {
            _binding = null
        }
    }

    fun setFirstFragment(isFirstFragment: Boolean){
        this.isFirstFragment = isFirstFragment
    }

    fun setToolBarNavigationIconEvent(isShow:Boolean){
        (requireActivity() as BaseNaviActivity).setToolBarNavigationIconEvent(isShow)
    }

    private fun navOnBackPressed(){
        isCloseBinding = true

        if(isFirstFragment){
            requireActivity().finish()
        }
        else{
            val navController = (requireActivity() as BaseNaviActivity).navController
            navController.popBackStack(backId, false)
        }
    }
}