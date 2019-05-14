package com.account.number.ui

import android.os.Bundle
import com.account.number.MyApplication
import com.account.number.R
import com.account.number.base.BaseActivity
import com.account.number.ui.fingerprint.FingerPrintActivity
import com.tencent.soter.wrapper.SoterWrapperApi
import com.tencent.soter.wrapper.wrap_task.InitializeParam
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {

    override val contentViewId: Int = R.layout.activity_splash

    override fun onCreating(savedInstanceState: Bundle?) {
        splash.postDelayed({ initFingerPrint() }, 100)
        splash.postDelayed({ gotoOther() }, 1900)
    }

    private fun gotoOther() {
        FingerPrintActivity.start(this)
        finish()
    }

    //初始化指纹解锁的相关参数
    private fun initFingerPrint() {
        initSoter()
        prepareSoterAuthKey()
    }


    private fun initSoter() {
        val param = InitializeParam.InitializeParamBuilder()
            .setScenes(0) // 场景值常量，后续使用该常量进行密钥生成或指纹认证
            .build()
        SoterWrapperApi.init(MyApplication.instance, { result -> log("store 初始化完成") }, param)
    }

    private fun prepareSoterAuthKey() {
        SoterWrapperApi.prepareAuthKey({ log("store 秘钥准备完成") }, false, true, 0, null, null)
    }
}
