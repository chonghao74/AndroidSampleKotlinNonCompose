package com.example.androidsamplekotlinnoncompose.ui.activity

import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.androidsamplekotlinnoncompose.R

open class BaseNaviActivity : BaseActivity() {
    private var toolbar: Toolbar? = null
    lateinit var  navController: NavController
    fun setToolBar(toolbar: Toolbar?){
        this.toolbar = toolbar
    }

    fun setToolBarNavigationIconEvent(isShow: Boolean) {
        if (isShow) {
            toolbar?.setNavigationIcon(R.drawable.baseline_arrow_back_24)
            toolbar?.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        } else {
//            binding.toolbar.setNavigationIcon(null)
//            binding.toolbar.setNavigationIcon(ColorDrawable(Color.TRANSPARENT))
            toolbar?.setNavigationIcon(
                ContextCompat.getDrawable(
                    this,
                    R.color.select_text_color_background_pink_purple
                )
            )
            toolbar?.setNavigationOnClickListener(null)
        }
    }

    fun setToolBarTitle(titleName: String){
        toolbar?.title = titleName
    }


}