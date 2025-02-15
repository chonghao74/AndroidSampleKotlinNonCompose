package com.example.androidsamplekotlinnoncompose.ui.fragment

import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.viewbinding.ViewBinding
import com.example.androidsamplekotlinnoncompose.R
import com.example.androidsamplekotlinnoncompose.ui.activity.BaseNaviActivity
import com.example.androidsamplekotlinnoncompose.ui.activity.NavigationNotScrollToolbarNestActivity
import com.example.androidsamplekotlinnoncompose.viewmodel.ActivityNavigationCommonViewModel
import com.example.androidsamplekotlinnoncompose.viewmodel.NavigationTestViewModel
import com.example.androidsamplekotlinnoncompose.viewmodel.UserBySaveStateViewModel

open class BaseNavFragment<T> : BaseFragment<T>() {
    private var isCloseBinding = false
    private var isFirstFragment = false
    val activityViewModels by activityViewModels<ActivityNavigationCommonViewModel> {
        SavedStateViewModelFactory(activity?.application as Application, this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback {
            navOnBackPressed(view)
        }
    }

    override fun onResume() {
        super.onResume()
        isCloseBinding = false
        (requireActivity() as BaseNaviActivity).setToolBarTitle(javaClass.simpleName)
        (requireActivity() as BaseNaviActivity).setToolBarNavigationIconEvent(true)
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

    private fun navOnBackPressed(view: View){
        isCloseBinding = true

        if(isFirstFragment){
            requireActivity().finish()
        }
        else{
            Navigation.findNavController(view).popBackStack()
//            Navigation.findNavController(view).navigateUp()
        }
    }
}