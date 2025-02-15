package com.example.androidsamplekotlinnoncompose.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import com.example.androidsamplekotlinnoncompose.R
import com.example.androidsamplekotlinnoncompose.databinding.FragmentNavListBinding
import com.example.androidsamplekotlinnoncompose.databinding.FragmentNavSettingBinding
import com.example.androidsamplekotlinnoncompose.viewmodel.NavigationTestViewModel

class NavSettingFragment: BaseNavFragmentAdjust<FragmentNavSettingBinding>() {
//    private var _binding: FragmentNavSettingBinding? = null
//    private val binding get() = _binding!!
    private val viewModel: NavigationTestViewModel by navGraphViewModels(R.id.navigation_graph)

    override fun onResume() {
        super.onResume()
        setFirstFragment(true)//預設為 false，只有第一個 item 需要覆寫改成 true
        setToolBarNavigationIconEvent(false)//預設為 true，只有第一個 item 需要覆寫改成 false
        activityViewModels.recordeItemFourthId(R.id.navSettingFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if(_binding == null){
            _binding = FragmentNavSettingBinding.inflate(inflater, container, false)
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUI(savedInstanceState == null)
    }

    private fun setUI(isSavedInstanceNull: Boolean) {

    }
}