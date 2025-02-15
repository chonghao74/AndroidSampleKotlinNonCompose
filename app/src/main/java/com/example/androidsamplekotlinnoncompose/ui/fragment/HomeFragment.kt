package com.example.androidsamplekotlinnoncompose.ui.fragment

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateViewModelFactory
import com.example.androidsamplekotlinnoncompose.R
import com.example.androidsamplekotlinnoncompose.databinding.FragmentHomeBinding
import com.example.androidsamplekotlinnoncompose.repository.model.user.LocalUser
import com.example.androidsamplekotlinnoncompose.ui.activity.HomeActivity
import com.example.androidsamplekotlinnoncompose.viewmodel.UserBySaveStateViewModel
import com.example.androidsamplekotlinnoncompose.viewmodel.UserViewModel

//// TODO: Rename parameter arguments, choose names that match
//// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val DIALOG_ON_VIEW_HOME_FRAGMENT_1_TAG = "Dialog_View_HomeFragment1"
private const val DIALOG_ON_ALERT_HOME_FRAGMENT_1_TAG = "Dialog_Alert_HomeFragment1"
private const val DIALOG_ON_ALERT_HOME_FRAGMENT_2_TAG = "Dialog_Alert_HomeFragment2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>(),
    TestDialogByCreateViewFragment.DialogClickListener,
    TestDialogByCreateDialogFragment.DialogClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
//    private var _binding: FragmentHomeBinding? = null
//    private val binding get() = _binding!!
    private val userViewModel by viewModels<UserViewModel>()
    private val userActivityViewModel by activityViewModels<UserViewModel>()

    private var userViewModelOld: UserViewModel? = null
    private var userActivityViewModelOld: UserViewModel? = null
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
//        return inflater.inflate(R.layout.fragment_home, container, false)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        (requireActivity() as BaseActivity).myApplication.fragmentName = javaClass.simpleName
        setViewModel()
        setUI(savedInstanceState == null)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        userActivityBySaveStateViewModel.getUserRecover()?.let {
            binding.tvAndroidViewModel.text = it.age.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setViewModel() {
        setTestData()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun setUI(isSavedInstanceNull: Boolean) {
        var bundleData = arguments?.getString(ARG_PARAM1)

        binding.btnToHome2.setOnClickListener {
//            userActivityViewModel.setText("${binding.btnToHome2.text} Change Color")

            val userViewModelAge = userViewModel.getUser()?.age?.plus(10)
            val userActivityViewModelAge = userActivityViewModel.getUser()?.age?.plus(10)
            val userActivityBySaveStateViewModelAge =
                userActivityBySaveStateViewModel.getUser()?.age?.plus(10)

            if (userViewModelAge != null) {
                userViewModel.setUser(LocalUser("Tim", userViewModelAge))
            }
//            else {
//                userViewModel.setUser(LocalUser())
//            }

            if (userActivityViewModelAge != null) {
                userActivityViewModel.setUser(LocalUser("Tim", userActivityViewModelAge))
            }
//            else {
//                userActivityViewModel.setUser(LocalUser())
//            }

            if (userActivityBySaveStateViewModelAge != null) {
                userActivityBySaveStateViewModel.setUser(
                    LocalUser(
                        "Tim",
                        userActivityBySaveStateViewModelAge.toInt()
                    )
                )
            }
//            else {
//                userActivityBySaveStateViewModel.setUser(LocalUser())
//            }


            parentFragmentManager.commit {
                add(R.id.fragmentContainer, HomeFragment2())
//                replace(R.id.fragmentContainer,HomeFragment2())
                setReorderingAllowed(true)
                addToBackStack(null)
            }

        }

        binding.btnShowDialog.setOnClickListener {
//            val testDialogByCreateDialogFragment = TestDialogByCreateDialogFragment.newInstance(
//                "測試使用 DialogFragment OnCreateDialog",
//                "Yes",
//                fragmentName = javaClass.simpleName)
//            testDialogByCreateDialogFragment.show(parentFragmentManager, DIALOG_ON_ALERT_HOME_FRAGMENT_1_TAG)

            val testDialogByCreateViewFragment = TestDialogByCreateViewFragment.newInstance(
                "測試使用 DialogFragment OnCreateView",
                "Yes",
                fragmentName = javaClass.simpleName
            )
            testDialogByCreateViewFragment.show(
                parentFragmentManager,
                DIALOG_ON_VIEW_HOME_FRAGMENT_1_TAG
            )

        }

        binding.btnShowBottomSheetDialog.setOnClickListener {
            var bottomSheetDialogFragment: TestBottomSheetDialogByCreateViewFragment? = null

            bottomSheetDialogFragment = TestBottomSheetDialogByCreateViewFragment()
            bottomSheetDialogFragment.let {
                it.isCancelable = false
                it.show(parentFragmentManager, bottomSheetDialogFragment.tag)
            }

//            bottomSheetDialogFragment.isCancelable = false
//            bottomSheetDialogFragment.show(parentFragmentManager, bottomSheetDialogFragment.tag)
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            }
        )

        if (isSavedInstanceNull) {
            //才執行
        }

    }

    private fun setTestData() {
        binding.tvViewModel.let {
            it.text = "${it.text}${userViewModel.getUser()?.age.toString()}"
        }

        binding.tvAndroidViewModel.let {
            it.text = "${it.text}${userActivityViewModel.getUser()?.age.toString()}"
        }

        binding.btnGetUserData.setOnClickListener {
            userActivityViewModel.getServerUserData()
        }

        userActivityViewModel.user.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(requireActivity(), it.age.toString(), Toast.LENGTH_SHORT).show()

                binding.tvAndroidViewModel.text = it.age.toString()
            }
        }

        userActivityViewModel.user2List.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(requireActivity(), it[30].body, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onTestDialogOnCreateViewTrueListener(dialogTag: String?) {
        Log.e("DialogHomeTrueListener", "tag = $dialogTag")
    }

    override fun onTestDialogOnCreateViewCancelListener(dialogTag: String?) {
        Log.e("DialogHomeCancelListener", "tag = $dialogTag")
    }

    override fun onTestDialogOnCreateDialogTrueListener(dialogTag: String?) {
        Log.e("DialogHomeTrueListener", "tag = $dialogTag")
    }

    override fun onTestDialogOnCreateDialogCancelListener(dialogTag: String?) {
        Log.e("DialogHomeCancelListener", "tag = $dialogTag")
    }

}