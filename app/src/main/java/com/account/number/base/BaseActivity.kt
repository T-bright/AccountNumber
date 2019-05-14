package com.account.number.base

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.account.number.R
import com.jaeger.library.StatusBarUtil
import dagger.android.support.DaggerAppCompatActivity
import org.jetbrains.anko.alert
import org.jetbrains.anko.indeterminateProgressDialog

abstract class BaseActivity : DaggerAppCompatActivity(), BaseView {

    val TAG: String by lazy { javaClass.simpleName }
    protected abstract val contentViewId: Int
    var loadingDialog: ProgressDialog? = null
    var alertDialog: AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentViewId)
        onCreating(savedInstanceState)
        setStatusBar()
    }

    open fun setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.colorPrimary))
    }

    abstract fun onCreating(savedInstanceState: Bundle?)

    override fun showLoading() {
        showLoading("玩命加载中...")
    }

    fun showLoading(text: String) {
        if (loadingDialog == null) {
            loadingDialog = indeterminateProgressDialog(text)
        }
        loadingDialog!!.show()
    }


    override fun hideLoading() {
        loadingDialog?.dismiss()
    }

    override fun showError(status: String) {
        loadingDialog?.dismiss()
    }

    fun showDialog(message: String) {
        showDialog(message, "确认", "取消")
    }

    fun showDialog(message: String, positive: String, negative: String) {
        showDialog(message, positive, negative, null)
    }

    fun showDialog(message: String, positive: String, negative: String, showDialogInterface: ShowDialogInterface?) {
        if (alertDialog == null) {
            alertDialog = alert(message) {}.build()
        }
        alertDialog!!.setMessage(message)

        if (!TextUtils.isEmpty(positive))
            alertDialog!!.setButton(DialogInterface.BUTTON_POSITIVE, positive) { dialog, which ->
            showDialogInterface?.positiveButton(dialog)
            positiveDialogButton(dialog)
        }
        if (!TextUtils.isEmpty(negative))
            alertDialog!!.setButton(DialogInterface.BUTTON_NEGATIVE, negative) { dialog, which ->
            showDialogInterface?.negativeButton(dialog)
            negativeDialogButton(dialog)
        }
        alertDialog!!.show()
    }

    fun positiveDialogButton(dialog: DialogInterface) {

    }

    fun negativeDialogButton(dialog: DialogInterface) {

    }

    interface ShowDialogInterface {
        fun positiveButton(dialog: DialogInterface)
        fun negativeButton(dialog: DialogInterface)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun log(message: String) {
        Log.e(TAG, message)
    }
}