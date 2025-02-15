package com.example.androidsamplekotlinnoncompose.ui.fragment

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import com.example.androidsamplekotlinnoncompose.R
import com.example.androidsamplekotlinnoncompose.databinding.FragmentStateRecoveryBinding
import com.example.androidsamplekotlinnoncompose.viewmodel.UserStateRecoveryViewModel
import com.example.androidsamplekotlinnoncompose.viewmodel.UserViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val FRAGMENT_TAG_TV: String = "FRAGMENT_TAG_TV"

class StateRecoveryFragment : BaseFragment<FragmentStateRecoveryBinding>() {
    private var param1: String? = null
    private var param2: String? = null
//    private var _binding: FragmentStateRecoveryBinding? = null
//    private val binding get() = _binding!!


    //Old
    private var userViewModelOld: UserStateRecoveryViewModel? = null
    private var userActivityViewModelOld: UserStateRecoveryViewModel? = null

    //Suggest
    //無使用 SavedState
//    private val userViewModel by viewModels<UserViewModel>()
    private val userViewModel by viewModels<UserStateRecoveryViewModel> {
        SavedStateViewModelFactory(activity?.application as Application, this)
    }

    //無使用 SavedState
//    private val userActivityViewModel by activityViewModels<UserViewModel>()
    private val userActivityViewModel by activityViewModels<UserStateRecoveryViewModel> {
        SavedStateViewModelFactory(activity?.application as Application, this)
    }

//    private val stateFactory =
//        SavedStateViewModelFactory(activity?.applicationContext as Application, this)

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }

                Log.e("StateRecoveryFragment", "onSaveInstanceState: ")


            }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(FRAGMENT_TAG_TV, binding.tvTestState1.text.toString())
        Log.e("System-State-Recovery-Fragment", "onSaveInstanceState")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        //use savedInstanceState
        savedInstanceState?.apply {
            this.getString(FRAGMENT_TAG_TV)?.apply {
                binding.tvTestState1.text = this
            }
            Log.e("System-State-Recovery-Fragment", "onViewStateRestored")
        }

        //use ViewModel SavedStateHandle
        userActivityViewModel.getStateText()?.let {
            binding.tvTestStateNonLiveData1.apply {
                setTextColor(resources.getColor(R.color.brown1, requireContext().theme))
                text = it
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStateRecoveryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModelInit()// Old instance 跟 使用 SavedState ，若只使用新的就把此刪除
        setUI(savedInstanceState != null)
        if (savedInstanceState == null) {
            Log.e("System-State-Recovery-Fragment", "onViewCreated-savedInstanceState == null")
        }else{
            Log.e("System-State-Recovery-Fragment", "onViewCreated-savedInstanceState != null")
        }
    }

    override fun onResume() {
        super.onResume()
        Log.e("System-State-Recovery-Fragment", "onResume")
    }

    private fun setUI(isRebuilding: Boolean) {
        binding.btnChangeData.setOnClickListener {
            binding.tvTestState1.text = "Go 100"
            binding.tvTestState2.text = "Go 200"
        }

        binding.btnChangeData2.setOnClickListener {
            userActivityViewModel.setStateText("Non LiveData Go 300")
        }


        userActivityViewModel.tvStateNonLiveData1.observe(viewLifecycleOwner) {
            it?.apply {
                binding.tvTestStateNonLiveData1.apply {
                    setTextColor(resources.getColor(R.color.brown1, requireContext().theme))
                    text = it
                }
            }
        }
    }

    //Old instance 跟 使用 SavedState
    private fun setViewModelInit() {

        activity?.application?.let {
            val stateFactory = SavedStateViewModelFactory(it, this)
            userViewModelOld = ViewModelProvider(
                this,
                stateFactory
            )[UserStateRecoveryViewModel::class.java]

            userActivityViewModelOld = ViewModelProvider(
                this,
                stateFactory
            )[UserStateRecoveryViewModel::class.java]
        }
    }

}