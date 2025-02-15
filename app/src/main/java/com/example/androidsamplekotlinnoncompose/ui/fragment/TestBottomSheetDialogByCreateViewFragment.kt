package com.example.androidsamplekotlinnoncompose.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import com.example.androidsamplekotlinnoncompose.R
import com.example.androidsamplekotlinnoncompose.databinding.FragmentTestBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class TestBottomSheetDialogByCreateViewFragment : BottomSheetDialogFragment() {

    interface DialogClickListener {
        fun onTestBottomSheetDialogBtn1Listener()
//        fun onTestBottomSheetDialogBtn2Listener()
    }

    //0 建立兩個 binding
    private var _binding: FragmentTestBottomSheetDialogBinding? = null
    private val binding get() = _binding!!
    val androidVersionQ10 = 29

    //1 透過 _binding create
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTestBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    //2 設定行為
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUI()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        object : BottomSheetDialog(requireContext(), R.style.MyBottomSheetDialogTheme) {
            override fun onAttachedToWindow() {
                super.onAttachedToWindow()

                val window = window
                    ?: error("window not attached?!")
                val sheetView = findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                    ?: error("bottom sheet design not found")

                WindowInsetsControllerCompat(window, sheetView)
                    .isAppearanceLightNavigationBars = true

                ViewCompat.setOnApplyWindowInsetsListener(sheetView) { _, windowInsets ->
//                    val systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

//                    if (systemBars.left > 0 || systemBars.right > 0) {
//                        val controller =
//                            WindowCompat.getInsetsController(window, sheetView)
//
//                        controller.hide(WindowInsetsCompat.Type.navigationBars())
//                        controller.systemBarsBehavior =
//                            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//                    }

                    val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
                    sheetView.updatePadding(
                        bottom = 0
                    )
                    sheetView.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                        bottomMargin = 0
                    }
                    WindowInsetsCompat.CONSUMED


                }
            }
        }

    private fun setUI() {
        _binding?.btnClose?.setOnClickListener {
            dialog?.dismiss()
            val clickEvent = activity as DialogClickListener
            clickEvent.onTestBottomSheetDialogBtn1Listener()

        }
    }

    //4 透過 _binding 銷毀
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}