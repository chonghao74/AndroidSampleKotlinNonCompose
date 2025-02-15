package com.example.androidsamplekotlinnoncompose.ui.activity

import android.os.Bundle
import android.util.Log
import com.example.androidsamplekotlinnoncompose.EdgeMode
import com.example.androidsamplekotlinnoncompose.databinding.ActivityDialogFragmentBinding
import com.example.androidsamplekotlinnoncompose.ui.fragment.TestBottomSheetDialogByCreateViewFragment
import com.example.androidsamplekotlinnoncompose.ui.fragment.TestDialogByCreateDialogFragment
import com.example.androidsamplekotlinnoncompose.ui.fragment.TestDialogByCreateViewFragment

private const val DIALOG_ON_CREATE_VIEW ="Dialog_Create_View_0"
private const val DIALOG_ON_ALERT_1_TAG ="Dialog_Alert1"
private const val DIALOG_ON_ALERT_2_TAG ="Dialog_Alert2"
class DialogFragmentActivity : BaseActivity()
    , TestDialogByCreateDialogFragment.DialogClickListener
    , TestDialogByCreateViewFragment.DialogClickListener
    , TestBottomSheetDialogByCreateViewFragment.DialogClickListener{
    private lateinit var binding: ActivityDialogFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialogFragmentBinding.inflate(layoutInflater)
        enableEdgeToEdgeMode(binding.root, binding.mainContainerCL, EdgeMode.NEW_EDGE_TO_EDGE)
        setContentView(binding.root)
        setUI()

    }

    private fun setUI() {
        binding.btnShowDialogByOnCreateView.setOnClickListener {
            val testDialogFragment = TestDialogByCreateViewFragment.newInstance(
                "測試使用 DialogFragment OnCreateView",
                 isOrientationMode = true)
            testDialogFragment.show(supportFragmentManager, DIALOG_ON_CREATE_VIEW)
        }

        binding.btnShowBottomSheetByOnCreateDialog.setOnClickListener {
            val testDialogByCreateDialogFragment = TestDialogByCreateDialogFragment.newInstance(
                "測試使用 DialogFragment OnCreateDialog",
                "Yes")
            testDialogByCreateDialogFragment.show(supportFragmentManager, DIALOG_ON_ALERT_1_TAG)
        }

        binding.btnShowBottomSheetByOnCreateDialog2.setOnClickListener {
            val testDialogByCreateDialogFragment = TestDialogByCreateDialogFragment.newInstance(
                "測試使用 DialogFragment OnCreateDialog"
                ,isOrientationMode = true)
            testDialogByCreateDialogFragment.isCancelable = false
            testDialogByCreateDialogFragment.show(supportFragmentManager, DIALOG_ON_ALERT_2_TAG)
        }

        binding.btnShowBottomSheetDialogFragment.setOnClickListener {
            val bottomSheetDialogFragment = TestBottomSheetDialogByCreateViewFragment()

            bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.tag)
        }

    }

    override fun onTestBottomSheetDialogBtn1Listener() {
        Log.e("BottomSheetDialogBTnListener", "Close")
    }

    override fun onTestDialogOnCreateDialogTrueListener(dialogTag: String?) {
        Log.e("DialogOnCreateDialogTrueListener", "tag = $dialogTag")
    }

    override fun onTestDialogOnCreateDialogCancelListener(dialogTag: String?) {
        Log.e("DialogOnCreateDialogCancelListener", "tag = $dialogTag")
    }

    override fun onTestDialogOnCreateViewTrueListener(dialogTag: String?) {
        Log.e("DialogOnCreateViewTrueListener", "tag = $dialogTag")
    }

    override fun onTestDialogOnCreateViewCancelListener(dialogTag: String?) {
        Log.e("DialogOnCreateViewCancelListener", "tag = $dialogTag")
    }

}