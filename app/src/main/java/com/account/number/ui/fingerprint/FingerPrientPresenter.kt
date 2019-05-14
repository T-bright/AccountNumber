package com.account.number.ui.fingerprint

import android.text.TextUtils
import android.util.Log
import com.account.number.MyApplication
import com.account.number.utils.SPUtils
import com.tencent.soter.wrapper.SoterWrapperApi
import com.tencent.soter.wrapper.wrap_callback.SoterProcessAuthenticationResult
import com.tencent.soter.wrapper.wrap_fingerprint.SoterFingerprintCanceller
import com.tencent.soter.wrapper.wrap_fingerprint.SoterFingerprintStateCallback
import com.tencent.soter.wrapper.wrap_task.AuthenticationParam
import javax.inject.Inject

class FingerPrientPresenter @Inject constructor() : FingerPrientContract.Presenter() {
    val KEY_FID = "fid"
    override fun fingerPrintVerify() {
        var fid = SPUtils.getString(KEY_FID, "")
        var varmStr = ""
        if (TextUtils.isEmpty(fid)) {
            varmStr = "请进行指纹验证，\n注意：第一次验证用的什么手指指纹，\n之后就必须用这个手指指纹"
        } else {
            varmStr = "请进行指纹验证"
        }
        mView!!.showWarm(varmStr)
        var param = AuthenticationParam.AuthenticationParamBuilder()
            .setScene(0)
            .setContext(MyApplication.instance)
            .setFingerprintCanceller(SoterFingerprintCanceller())
            .setPrefilledChallenge("tong_si_wei_i_love_you_so")
            .setSoterFingerprintStateCallback(object : SoterFingerprintStateCallback {
                override fun onAuthenticationCancelled() {
                }

                override fun onStartAuthentication() {
                }

                override fun onAuthenticationError(errorCode: Int, errorString: CharSequence?) {
                    mView!!.showError("指纹验证失败，请重试！")
                }

                override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
                }

                override fun onAuthenticationSucceed() {
                }

                override fun onAuthenticationFailed() {
                    mView!!.showError("指纹验证失败，请重试！")
                }
            }).build()
        SoterWrapperApi.requestAuthorizeAndSign({ requestResult(it) }, param);
    }

    private fun requestResult(result: SoterProcessAuthenticationResult) {
        Log.e("AAA",result.toString())
        val extData = result.extData
        if (extData == null || TextUtils.isEmpty(extData.fid)) {
            mView!!.showWarm("此手机不兼容本逻辑，拿不到指纹fid。\n请使用其他应用,即将退出应用！")
            return
        }
        val fid = extData.fid
        var fidCache = SPUtils.getString(KEY_FID, "")
        if (TextUtils.isEmpty(fidCache)) {//为空表示第一次验证
            SPUtils.save(KEY_FID, fid)
            mView!!.verifySuccess()
        } else {
            if (fidCache.equals(fid)) {//这里限定死只能使用第一次解锁的指纹。只能使用第一次验证的手指指纹才能解锁，以防手机掉了被别人拿到密码。
                mView!!.verifySuccess()
            } else {
                mView!!.showWarm("请使用第一次验证用的\n手指指纹进行验证")
            }
        }
    }
}