package com.account.number.ui.fingerprint

import com.account.number.base.BasePresenter
import com.account.number.base.BaseView

interface FingerPrientContract {
    interface View : BaseView {
        //提示
        fun showWarm(warmStr: String)

        fun verifySuccess()
    }

    abstract class Presenter : BasePresenter<View>() {
        abstract fun fingerPrintVerify()//验证指纹
    }
}