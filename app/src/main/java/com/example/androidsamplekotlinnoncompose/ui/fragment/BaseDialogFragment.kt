package com.example.androidsamplekotlinnoncompose.ui.fragment

import androidx.fragment.app.DialogFragment

const val ARG_FRAGMENT_NAME  = "fragmentName"
const val ARG_CONTENT = "content"
const val IS_ORIENTATION_MODE = "isOrientationMode"

open class BaseDialogFragment : DialogFragment() {
    fun<T> checkDialogClickListener(fragmentName: String?):T{
        var dialogClickListener: T? = null

        if (fragmentName == null) {
            dialogClickListener = activity as T
        } else {
            val fragmentList = activity?.supportFragmentManager?.fragments
            val fragment = fragmentList?.get((fragmentList.size) - 2)
            dialogClickListener = fragment as T
        }
        return dialogClickListener
    }
}