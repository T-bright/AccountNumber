package com.account.number.extension

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.account.number.R


/**
 * 将mFragment添加到R.id.contentFrame 资源中
 */
fun FragmentActivity.addToActivity(mFragment: Fragment): Fragment {
    var fragment = findFragmentById(R.id.contentFrame)
    if (fragment == null) {
        fragment = mFragment
        addFragment(R.id.contentFrame, fragment)
    }
    return fragment
}

fun FragmentActivity.addFragment(@IdRes id: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().add(id, fragment).commit()
}

fun FragmentActivity.replaceFragment(@IdRes id: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().replace(id, fragment).commit()
}

fun Fragment.replaceFragment(@IdRes id: Int, fragment: Fragment) {
    childFragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        .replace(id, fragment).commit()
}

fun FragmentActivity.findFragmentByTag(tag: String): Fragment? =
    supportFragmentManager.findFragmentByTag(tag)

fun FragmentActivity.findFragmentById(tag: Int): Fragment? =
    supportFragmentManager.findFragmentById(tag)
