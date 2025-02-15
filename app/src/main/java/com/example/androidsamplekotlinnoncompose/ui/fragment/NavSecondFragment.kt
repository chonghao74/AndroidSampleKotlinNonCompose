package com.example.androidsamplekotlinnoncompose.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.androidsamplekotlinnoncompose.R
import com.example.androidsamplekotlinnoncompose.databinding.FragmentNavSecondBinding
import com.example.androidsamplekotlinnoncompose.ui.activity.BaseNaviActivity
import com.example.androidsamplekotlinnoncompose.viewmodel.NavigationTestViewModel

class NavSecondFragment : BaseNavFragmentAdjust<FragmentNavSecondBinding>() {
    private val viewModel: NavigationTestViewModel by navGraphViewModels(R.id.navigation_graph)
//    private val viewModel: NavigationTestViewModel by navGraphViewModels(R.id.navigation_graph_test)

    override fun onResume() {
        super.onResume()
        activityViewModels.recordeItemFirstId(R.id.navSecondFragment)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding == null) {
            _binding = FragmentNavSecondBinding.inflate(inflater, container, false)
        }
        backId = R.id.navFirstFragment
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUI(savedInstanceState == null)
    }

    private fun setUI(isSavedInstanceNull: Boolean) {
        if (isSavedInstanceNull) {
            binding.tvBtnText.text = viewModel.getBtnText()
        } else {
            val dataOriginal = viewModel.getBtnText()
            val dartRecover = viewModel.getBtnTextRecover()
            Log.e("NavSecondFragment", "dataOriginal id $dataOriginal")
            Log.e("NavSecondFragment", "dartRecover id $dartRecover")
            viewModel.setBtnText(dartRecover)
        }

        binding.btnChange.setOnClickListener {
            viewModel.setBtnText("I wanna change!!!")
            findNavController().navigate(R.id.action_navSecondFragment_to_navThirdFragment)
        }

        viewModel.btnText.observe(viewLifecycleOwner) {
            it?.apply {
                binding.tvBtnText.text = it
            }
        }
    }
}