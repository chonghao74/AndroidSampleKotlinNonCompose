package com.example.androidsamplekotlinnoncompose.ui.fragment

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateViewModelFactory
import com.example.androidsamplekotlinnoncompose.databinding.FragmentHome2Binding
import com.example.androidsamplekotlinnoncompose.repository.model.user.LocalUser
import com.example.androidsamplekotlinnoncompose.repository.model.user.User
import com.example.androidsamplekotlinnoncompose.ui.activity.BaseActivity
import com.example.androidsamplekotlinnoncompose.ui.activity.HomeActivity
import com.example.androidsamplekotlinnoncompose.viewmodel.UserBySaveStateViewModel
import com.example.androidsamplekotlinnoncompose.viewmodel.UserViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment2.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment2 : BaseFragment<FragmentHome2Binding>() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
//    private var _binding: FragmentHome2Binding? = null
//    private val binding get() = _binding!!

    //    private val userViewModel by viewModels<UserViewModel>()
//    private val userActivityViewModel by activityViewModels<UserViewModel>()
    private val userActivityBySaveStateViewModel by activityViewModels<UserBySaveStateViewModel> {
        SavedStateViewModelFactory(activity?.application as Application, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home2, container, false)
        _binding = FragmentHome2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as BaseActivity).myApplication.fragmentName = javaClass.simpleName
//        setUI()
        setUIBySaveState(savedInstanceState == null)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    parentFragmentManager.popBackStack()
//                    parentFragmentManager.commit {
//                        remove(this@HomeFragment2)
//                    }
                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment2.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment2().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        userActivityBySaveStateViewModel.getUserRecover()?.let {
            binding.tvTestActivityViewModel.text = it.age.toString()
        }
    }

    private fun setUI() {
//        binding.tvTestViewModel.text = userViewModel.getUser()?.age.toString()
//        binding.tvTestActivityViewModel.text = userActivityViewModel.getUser()?.age.toString()
//        binding.btnChangeData.setOnClickListener {
//            val edAge = binding.edUserAge.text.toString().toInt()
//            userActivityViewModel.setUser(
//                LocalUser(
//                    age = edAge
//                )
//            )
//
//        }

//        userActivityViewModel.user.observe(viewLifecycleOwner) {
//            if (it != null) {
//                binding.tvTestActivityViewModel.text = it.age.toString()
//            }
//        }

//        binding.edUserAge.apply {
//            setSelection(this.text.length)
//        }


//        CoroutineScope(Dispatchers.IO).launch {
//            val json = URL("http://192.168.131.245:1001/api/user/test-params/23123123")
//                .readText()
//            withContext(Dispatchers.Main) {
//                binding.tvTestActivityViewModel.text = json.toString()
//            }
//        }
    }

    private fun setUIBySaveState(isSavedState: Boolean) {
        if (isSavedState) {
//            binding.tvTestViewModel.text = userViewModel.getUser()?.age.toString()
            binding.tvTestActivityViewModel.text =
                userActivityBySaveStateViewModel.getUser()?.age.toString()
            binding.btnChangeData.setOnClickListener {
                val edAge = binding.edUserAge.text.toString().toInt()
                userActivityBySaveStateViewModel.setUser(
                    LocalUser(
                        age = edAge
                    )
                )

            }

            userActivityBySaveStateViewModel.user.observe(viewLifecycleOwner) {
                if (it != null) {
                    binding.tvTestActivityViewModel.text = it.age.toString()
                }
            }

            binding.edUserAge.apply {
                setSelection(this.text.length)
            }
        }

    }


}