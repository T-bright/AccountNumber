package com.account.number.ui.fingerprint

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.account.number.R
import com.account.number.base.BaseActivity
import com.account.number.extension.addToActivity
import com.jaeger.library.StatusBarUtil
import javax.inject.Inject

class FingerPrintActivity : BaseActivity() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, FingerPrintActivity::class.java))
        }
    }

    @Inject
    lateinit var fingerPrientFragment: FingerPrientFragment

    override val contentViewId: Int = R.layout.activity_common

    override fun onCreating(savedInstanceState: Bundle?) {
        addToActivity(fingerPrientFragment)
    }
    override fun setStatusBar(){
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.white))
        StatusBarUtil.setLightMode(this)
    }
}
