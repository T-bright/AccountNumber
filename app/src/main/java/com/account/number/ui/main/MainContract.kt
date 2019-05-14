package com.account.number.ui.main

import androidx.fragment.app.FragmentActivity
import com.account.number.base.BasePresenter
import com.account.number.base.BaseView
import com.account.number.db.AccountMain

interface MainContract {
    interface MainView : BaseView {
        fun loadDataSuccess(results: ArrayList<AccountMain>)

        fun deleteDataSuccess()

        fun permissionIsGranted(granted: Boolean)

        fun saveDataResult(isSaveSuccess: Boolean)
    }

    abstract class MainPresenter : BasePresenter<MainView>() {
        abstract fun loadData()

        abstract fun deleteData(accountTypeName: String)

        abstract fun saveData(results: ArrayList<out Any>, activity: FragmentActivity)
    }
}