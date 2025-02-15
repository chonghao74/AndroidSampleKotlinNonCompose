package com.example.androidsamplekotlinnoncompose.utility

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator

/**
 * 使用 Add/Hide/Show 處理 Fragment，使Fragment 执行 onPause/onResume.
 * 避免重建.
 */
@Navigator.Name("fragment")
class FragmentNavigatorAdjustAddHideShow(
    private val mContext: Context,
    private val mFragmentManager: FragmentManager,
    private val mContainerId: Int
) : FragmentNavigator(mContext, mFragmentManager, mContainerId) {

    private var savedIds: MutableSet<String>? = null

    init {
        try {
            val field = FragmentNavigator::class.java.getDeclaredField("savedIds")
            field.isAccessible = true
            savedIds = field[this] as MutableSet<String>
        } catch (e: Exception) {
            Log.d(TAG, "Mapping SavedIds is Fail: $e")
        }
    }

    override fun navigate(
        entries: List<NavBackStackEntry>,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ) {
        if (mFragmentManager.isStateSaved) {
            Log.i(
                TAG, "Ignoring navigate() call: FragmentManager has already saved its state"
            )
            return
        }
        for (entry in entries) {
            navigate(entry, navOptions, navigatorExtras)
        }
    }

    private fun navigate(
        entry: NavBackStackEntry,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ) {
        val initialNavigation = state.backStack.value.isEmpty()
        val restoreState = (
                navOptions != null && !initialNavigation &&
                        navOptions.shouldRestoreState() &&
                        savedIds?.remove(entry.id) == true
                )
        if (restoreState) {
            mFragmentManager.restoreBackStack(entry.id)
            state.push(entry)
            return
        }
        val ft = createFragmentTransaction(entry, navOptions)


        if (!initialNavigation) {
            ft.addToBackStack(entry.id)
        }

        if (navigatorExtras is Extras) {
            for ((key, value) in navigatorExtras.sharedElements) {
                ft.addSharedElement(key, value)
            }
        }
        ft.commit()
        state.push(entry)
    }

    override fun onLaunchSingleTop(backStackEntry: NavBackStackEntry) {
        if (mFragmentManager.isStateSaved) {
            Log.i(
                TAG,
                "Ignoring onLaunchSingleTop() call: FragmentManager has already saved its state"
            )
            return
        }

        val ft = createFragmentTransaction(backStackEntry, null)
        if (state.backStack.value.size > 1) {
            mFragmentManager.popBackStack(
                backStackEntry.id,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
            ft.addToBackStack(backStackEntry.id)
        }
        ft.commit()
        state.onLaunchSingleTop(backStackEntry)
    }


    private fun createFragmentTransaction(
        entry: NavBackStackEntry,
        navOptions: NavOptions?
    ): FragmentTransaction {

        val destination = entry.destination as Destination
        val args = entry.arguments
        val ft = mFragmentManager.beginTransaction()
        var targetFragmentClassName = destination.className

        if (targetFragmentClassName[0] == '.') {
            targetFragmentClassName = mContext.packageName + targetFragmentClassName
        }

        var enterAnim = navOptions?.enterAnim ?: -1
        var exitAnim = navOptions?.exitAnim ?: -1
        var popEnterAnim = navOptions?.popEnterAnim ?: -1
        var popExitAnim = navOptions?.popExitAnim ?: -1

        if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
            enterAnim = if (enterAnim != -1) enterAnim else 0
            exitAnim = if (exitAnim != -1) exitAnim else 0
            popEnterAnim = if (popEnterAnim != -1) popEnterAnim else 0
            popExitAnim = if (popExitAnim != -1) popExitAnim else 0
            ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        }

        //重點 region
        var frag: Fragment? = mFragmentManager.primaryNavigationFragment //取得當前 fragment
        //判断是否需要重新重建一個新的 Fragment
        var needRecreate = false
        if (frag?.javaClass?.name == targetFragmentClassName) {
            needRecreate = true
        }
        //如果存在 Fragment，就 hide。
        if (frag != null) {
            ft.setMaxLifecycle(frag, Lifecycle.State.STARTED)
            ft.hide(frag)
        }

        val tag = destination.id.toString()
        frag = mFragmentManager.findFragmentByTag(tag)
        //判断是否需要重建，如果需要就 add 反之 show。
        if (frag != null && !needRecreate) {
            ft.setMaxLifecycle(frag, Lifecycle.State.RESUMED)
            ft.show(frag)
        } else {
            frag = mFragmentManager.fragmentFactory.instantiate(mContext.classLoader, targetFragmentClassName)
            frag.arguments = args//设置参数.
            ft.add(mContainerId, frag, tag)
        }
        //endregion

        //ft.replace(mContainerId, frag) //移除原先使用 replace
        ft.setPrimaryNavigationFragment(frag)//新 fragment 置頂 primaryNavigationFragment
        ft.setReorderingAllowed(true)
        return ft
    }


    companion object {
        private const val TAG = "AdjustFragmentNavigator"
    }
}