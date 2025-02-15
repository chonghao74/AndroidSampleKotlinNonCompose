package com.example.androidsamplekotlinnoncompose.ui.fragment

import androidx.fragment.app.Fragment
import com.example.androidsamplekotlinnoncompose.databinding.FragmentHomeBinding
import com.example.androidsamplekotlinnoncompose.ui.activity.BaseActivity

open class BaseFragment<T> : Fragment()  {
    var _binding: T? = null
    val binding get() = _binding!!

    override fun onResume() {
        super.onResume()
        //為了推播切換
        (requireActivity() as BaseActivity).myApplication.fragmentName = javaClass.simpleName
    }

}