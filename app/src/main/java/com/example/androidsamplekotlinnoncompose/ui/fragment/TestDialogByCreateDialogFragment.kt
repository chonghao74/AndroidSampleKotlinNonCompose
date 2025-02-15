package com.example.androidsamplekotlinnoncompose.ui.fragment

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.res.Configuration
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

private const val ARG_TRUE_TEXT = "trueText"
private const val ARG_CANCEL_TEXT = "cancelText"

//1 繼承 DialogFragment()
class TestDialogByCreateDialogFragment : BaseDialogFragment() {

    //2 透過 AlertDialog.Builder 產生
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialogBuilder = AlertDialog.Builder(activity)
            .setTitle("ddd")
            .setMessage(arguments?.getString(ARG_CONTENT))
            .setPositiveButton(arguments?.getString(ARG_TRUE_TEXT), object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    val fragmentName = arguments?.getString(ARG_FRAGMENT_NAME)
                    val dialogTrueListener =  checkDialogClickListener<DialogClickListener>(fragmentName)
                    dialogTrueListener.onTestDialogOnCreateDialogTrueListener(this@TestDialogByCreateDialogFragment.tag)
                }

            })
            .setNegativeButton(arguments?.getString(ARG_CANCEL_TEXT), object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    val fragmentName = arguments?.getString(ARG_FRAGMENT_NAME)
                    val dialogCancelListener =  checkDialogClickListener<DialogClickListener>(fragmentName)
                    dialogCancelListener.
                    onTestDialogOnCreateDialogCancelListener(this@TestDialogByCreateDialogFragment.tag)
                }
            })
//        return super.onCreateDialog(savedInstanceState)
        val alertDialog = alertDialogBuilder.create().apply {
            this.show()
            this.getButton(DialogInterface.BUTTON_POSITIVE).isAllCaps = false
            this.getButton(DialogInterface.BUTTON_NEGATIVE).isAllCaps = false
        }

        return alertDialog
    }

    //3畫面寬長調整
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

    //4
    companion object {
        @JvmStatic
        fun newInstance(content: String,
                        btnTrueText: String = "True",
                        btnCancelText: String = "Cancel" ,
                        fragmentName: String? = null,
                        isOrientationMode: Boolean = false) =
            TestDialogByCreateDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_CONTENT, content)
                    putString(ARG_TRUE_TEXT, btnTrueText)
                    putString(ARG_CANCEL_TEXT, btnCancelText)
                    putString(ARG_FRAGMENT_NAME, fragmentName)
                    putBoolean(IS_ORIENTATION_MODE, isOrientationMode)
                }
            }
    }

    var gg:DialogClickListener? = null
    //5
    interface DialogClickListener {
        fun onTestDialogOnCreateDialogTrueListener(dialogTag: String?)
        fun onTestDialogOnCreateDialogCancelListener(dialogTag: String?)
    }

}