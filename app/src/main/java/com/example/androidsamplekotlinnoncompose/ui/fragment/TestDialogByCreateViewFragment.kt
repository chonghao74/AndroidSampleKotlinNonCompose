package com.example.androidsamplekotlinnoncompose.ui.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.androidsamplekotlinnoncompose.databinding.FragmentTestDialogBinding
import com.example.androidsamplekotlinnoncompose.utility.dpToPx

private const val ARG_TRUE_TEXT = "trueText"
private const val ARG_CANCEL_TEXT = "cancelText"

//1 繼承 DialogFragment()
class TestDialogByCreateViewFragment() : BaseDialogFragment() {
    //2.1 建立兩個 binding
    private var _binding: FragmentTestDialogBinding? = null
    private val binding get() = _binding!!

    //2.2
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        Log.e(javaClass.simpleName, "onCreateView")

        _binding = FragmentTestDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    //2.3
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUI()
    }

    //3 畫面調整
    override fun onStart() {
        super.onStart()
        val orientationMode = resources.configuration.orientation
        val window = dialog?.window

        if (arguments?.getBoolean(IS_ORIENTATION_MODE) == true) {
            when (orientationMode) {
                Configuration.ORIENTATION_LANDSCAPE -> {
                    window?.setLayout(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }

                else -> {
                    // 設置寬度滿版
                    window?.setLayout(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }
            }
        } else {
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    //4 是否帶入參數
    companion object {
        @JvmStatic
        fun newInstance(content: String,
                        btnTrueText: String = "True",
                        btnCancelText: String = "Cancel",
                        fragmentName: String? = null,
                        isOrientationMode: Boolean = false) =
            TestDialogByCreateViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_CONTENT, content)
                    putString(ARG_TRUE_TEXT, btnTrueText)
                    putString(ARG_CANCEL_TEXT, btnCancelText)
                    putString(ARG_FRAGMENT_NAME, fragmentName)
                    putBoolean(IS_ORIENTATION_MODE, isOrientationMode)
                }
            }
    }

    //5 事件監聽
    interface DialogClickListener{
        fun onTestDialogOnCreateViewTrueListener(dialogTag:String?)
        fun onTestDialogOnCreateViewCancelListener(dialogTag:String?)
    }

    //6 透過 _binding 銷毀
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUI(){
        binding.btnTrue.text = arguments?.getString(ARG_TRUE_TEXT)
        binding.btnCancel.text = arguments?.getString(ARG_CANCEL_TEXT)

        binding.btnTrue.setOnClickListener {
            val fragmentName = arguments?.getString(ARG_FRAGMENT_NAME)
            val clickEventTrueListener =  checkDialogClickListener<DialogClickListener>(fragmentName)
            clickEventTrueListener.onTestDialogOnCreateViewTrueListener(this@TestDialogByCreateViewFragment.tag)
            dismiss()
        }

        binding.btnCancel.setOnClickListener{
            val fragmentName = arguments?.getString(ARG_FRAGMENT_NAME)
            val clickEventCancelListener =  checkDialogClickListener<DialogClickListener>(fragmentName)
            clickEventCancelListener.onTestDialogOnCreateViewCancelListener(this@TestDialogByCreateViewFragment.tag)
            dismiss()
        }
    }
}