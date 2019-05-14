package com.account.number.base

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseFragment : Fragment(), HasSupportFragmentInjector, BaseView {

    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return childFragmentInjector
    }

    protected abstract val contentViewId: Int
    val TAG: String by lazy { javaClass.simpleName }
    var baseActivity: BaseActivity? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (contentViewId != 0) {
            inflater.inflate(contentViewId, container, false)
        } else {
            super.onCreateView(inflater, container, savedInstanceState)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreatingView(view, savedInstanceState)
    }

    abstract fun onCreatingView(view: View, savedInstanceState: Bundle?)


    override fun onAttach(context: Context?) {
        inject()
        super.onAttach(context)
        baseActivity = context as BaseActivity
    }

    protected open fun inject() {
        AndroidSupportInjection.inject(this)
    }

    override fun showLoading() {
        showLoading("玩命加载中...")
    }

    fun showLoading(text: String) {
        baseActivity!!.showLoading(text)
    }


    override fun hideLoading() {
        baseActivity!!.hideLoading()
    }

    override fun showError(status: String) {
        baseActivity!!.hideLoading()
    }


    fun showDialog(message: String) {
        showDialog(message, "确认", "取消")
    }

    fun showDialog(message: String, positive: String, negative: String) {
        baseActivity!!.showDialog(message, positive, negative, object : BaseActivity.ShowDialogInterface {
            override fun positiveButton(dialog: DialogInterface) {
                positiveDialogButton(dialog)
            }

            override fun negativeButton(dialog: DialogInterface) {
                negativeDialogButton(dialog)
            }
        })
    }

    open fun positiveDialogButton(dialog: DialogInterface) {

    }

    open fun negativeDialogButton(dialog: DialogInterface) {

    }

    fun log(message: String) {
        Log.e(TAG, message)
    }
}