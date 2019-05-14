package com.account.number.ui.fingerprint

import android.os.Bundle
import android.view.View
import com.account.number.R
import com.account.number.base.BaseFragment
import com.account.number.ui.main.MainActivity
import com.tencent.soter.wrapper.SoterWrapperApi
import kotlinx.android.synthetic.main.fragment_finger_print.*
import org.jetbrains.anko.toast
import javax.inject.Inject

class FingerPrientFragment @Inject constructor() : BaseFragment(), FingerPrientContract.View {


    @Inject
    lateinit var fingerPrientPresenter: FingerPrientPresenter
    override val contentViewId: Int = R.layout.fragment_finger_print

    override fun onCreatingView(view: View, savedInstanceState: Bundle?) {
        fingerPrientPresenter.attach(this)
        fingerPrientPresenter.fingerPrintVerify()
    }

    override fun showWarm(warmStr: String) {
        showDialog(warmStr, "知道了", "")
        root.postDelayed({
            if(warmStr.contains("退出应用")){
                System.exit(0)
            }
        },2000)
    }

    override fun verifySuccess() {
        MainActivity.start(activity!!)
    }

    override fun showError(status: String) {
        activity!!.toast(status)
    }

    override fun onDetach() {
        super.onDetach()
        fingerPrientPresenter.detach()
    }

    override fun onDestroy() {
        super.onDestroy()
        SoterWrapperApi.release()
    }
}