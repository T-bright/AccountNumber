package com.account.number.dialog

import com.account.number.base.BasePresenter
import com.account.number.base.BaseView
import com.account.number.db.AccountList
import com.account.number.db.AccountMain

interface DiaLogContract {
    interface DialogView : BaseView {
        fun operationSuccess()
    }

    abstract class DialogPresenter : BasePresenter<DialogView>() {
        abstract fun addDataMain(newData: AccountMain)

        abstract fun updateDataMain(newData: AccountMain, oldData: AccountMain?)

        abstract fun addDataList(newData: AccountList)

        abstract fun updateDataList(newData: AccountList, oldData: AccountList?)
    }
}