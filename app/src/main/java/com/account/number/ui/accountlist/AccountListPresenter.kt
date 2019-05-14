package com.account.number.ui.accountlist

import com.account.number.db.AccountList
import com.account.number.db.DBManager
import javax.inject.Inject

class AccountListPresenter @Inject constructor(): AccountListContract.AccountListPresenter(){
    @Inject
    lateinit var dbManager: DBManager

    override fun loadData(accountTypeName : String) {
        mView!!.showLoading()
        mView!!.loadDataSuccess(dbManager.queryAccountListData(accountTypeName))
        mView!!.hideLoading()
    }

    override fun deleteData(accountList: AccountList) {
        mView!!.showLoading()
        dbManager.deleteAccountListData(accountList)
        mView!!.hideLoading()
        mView!!.deleteDataSuccess()
    }
}