package com.example.androidsamplekotlinnoncompose.ui.fragment

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.androidsamplekotlinnoncompose.R
import com.example.androidsamplekotlinnoncompose.databinding.FragmentNavFirstBinding
import com.example.androidsamplekotlinnoncompose.ui.activity.BaseNaviActivity
import com.example.androidsamplekotlinnoncompose.viewmodel.NavigationTestViewModel

class NavFirstFragment : BaseNavFragmentAdjust<FragmentNavFirstBinding>() {

//    private var _binding: FragmentNavFirstBinding? = null
//    private val binding get() = _binding!!

        private val viewModel: NavigationTestViewModel by navGraphViewModels(R.id.navigation_graph)
//    private val viewModel by navGraphViewModels<NavigationTestViewModel>(R.id.navigation_graph_test)

    override fun onResume() {
        super.onResume()
        setFirstFragment(true)//預設為 false，只有第一個 item 需要覆寫改成 true
        setToolBarNavigationIconEvent(false)//預設為 true，只有第一個 item 需要覆寫改成 false
        activityViewModels.recordeItemFirstId(R.id.navFirstFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if (_binding == null) {
            _binding = FragmentNavFirstBinding.inflate(inflater, container, false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUI(savedInstanceState == null)
    }

    private fun setInit() {
        (requireActivity() as BaseNaviActivity).setToolBarNavigationIconEvent(false)
    }

    private fun setUI(isSavedInstanceNull: Boolean) {
        if (isSavedInstanceNull) {
            viewModel.setBtnText("恢復資料")
        } else {
            val dataOriginal = viewModel.getBtnText()
            val dartRecover = viewModel.getBtnTextRecover()
            Log.e("NavFirstFragment", "dataOriginal id $dataOriginal")
            Log.e("NavFirstFragment", "dartRecover id $dartRecover")
//            viewModel.setBtnText(dartRecover)
            viewModel.getBtnTextLiveData()
        }

        binding.buttonFirst.setOnClickListener {
            val navController = (requireActivity() as BaseNaviActivity).navController
//            navController.navigate(R.id.navSecondFragment)
            navController.navigate(R.id.action_navFirstFragment_to_navSecondFragment)
//            findNavController().navigate(R.id.action_navFirstFragment_to_navSecondFragment)
        }

        viewModel.btnText.observe(viewLifecycleOwner) {
            it?.apply {
                binding.buttonFirst.text = it
            }
        }
    }

}