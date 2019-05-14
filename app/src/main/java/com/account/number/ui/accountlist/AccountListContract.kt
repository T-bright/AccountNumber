package com.account.number.ui.accountlist

import com.account.number.base.BasePresenter
import com.account.number.base.BaseView
import com.account.number.db.AccountList
import com.account.number.db.AccountMain

interface AccountListContract {
    interface AccountListView : BaseView {
        fun loadDataSuccess(results: ArrayList<AccountList>)

        fun deleteDataSuccess()
    }

    abstract class AccountListPresenter : BasePresenter<AccountListView>() {
        abstract fun loadData(accountTypeName : String)

        abstract fun deleteData(accountList: AccountList)
    }
}