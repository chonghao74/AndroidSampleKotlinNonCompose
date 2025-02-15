package com.example.androidsamplekotlinnoncompose.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.androidsamplekotlinnoncompose.R
import com.example.androidsamplekotlinnoncompose.databinding.FragmentNavSecondBinding
import com.example.androidsamplekotlinnoncompose.databinding.FragmentNavThirdBinding
import com.example.androidsamplekotlinnoncompose.ui.activity.BaseNaviActivity
import com.example.androidsamplekotlinnoncompose.viewmodel.NavigationTestViewModel

class NavThirdFragment : BaseNavFragmentAdjust<FragmentNavThirdBinding>() {
//    private var _binding: FragmentNavThirdBinding? = null
//    private val binding get() = _binding!!
    private val viewModel: NavigationTestViewModel by navGraphViewModels(R.id.navigation_graph)

    override fun onResume() {
        super.onResume()
        activityViewModels.recordeItemFirstId(R.id.navThirdFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding == null){
            _binding = FragmentNavThirdBinding.inflate(inflater, container, false)
        }
        backId = R.id.navSecondFragment
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInit(view)
        setUI(savedInstanceState == null)
    }

    private fun setInit(view: View){
        (requireActivity() as BaseNaviActivity).setToolBarNavigationIconEvent(true)
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

    private fun setUI(isSavedInstanceNull: Boolean) {
        if (isSavedInstanceNull) {
        }

        binding.btnBack.setOnClickListener {
            val navController = (requireActivity() as BaseNaviActivity).navController
            navController.navigate(R.id.action_navThirdFragment_to_navFirstFragment)
//            findNavController().navigate(R.id.action_navThirdFragment_to_navFirstFragment)
        }
    }

}