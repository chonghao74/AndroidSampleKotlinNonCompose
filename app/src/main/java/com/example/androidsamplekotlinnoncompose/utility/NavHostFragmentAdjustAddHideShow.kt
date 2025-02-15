package com.example.androidsamplekotlinnoncompose.utility

import android.view.View
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import com.example.androidsamplekotlinnoncompose.R

class NavHostFragmentAdjustAddHideShow : NavHostFragment() {

    /**
     * createFragmentNavigator 雖是 Deprecated 但內部依舊是靠此函示產生只是變成 private
     */
    @Deprecated("Use {@link #onCreateNavController(NavController)}")
    override fun createFragmentNavigator(): Navigator<out FragmentNavigator.Destination> {
        return FragmentNavigatorAdjustAddHideShow(requireContext(), childFragmentManager, containerId)
    }

    /**
     * 下方 id 請自行調整成使用的 FragmentContainerView 的 android:id
     */
    private val containerId: Int
        get() {
            val id = id
            return if (id != 0 && id != View.NO_ID) {
                id
            } else R.id.nav_host //請自行調整成使用的 FragmentContainerView 的 android:id
        }
}