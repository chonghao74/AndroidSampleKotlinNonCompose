package com.example.androidsamplekotlinnoncompose.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TestBottomSheetDialogByCreateDialogFullFragment : BottomSheetDialogFragment() {
    interface DialogClickListener {
        fun onTestBottomSheetDialogBtn1Listener()
//        fun onTestBottomSheetDialogBtn2Listener()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        //滿版作法 尚未整理完畢
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let {
                val behaviour = BottomSheetBehavior.from(it)
//                setupFullHeight(it)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }
}