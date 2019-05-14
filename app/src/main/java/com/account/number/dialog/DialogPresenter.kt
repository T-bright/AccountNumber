package com.account.number.dialog

import android.text.TextUtils
import com.account.number.db.AccountList
import com.account.number.db.AccountMain
import com.account.number.db.DBManager
import javax.inject.Inject

class DialogPresenter @Inject constructor() : DiaLogContract.DialogPresenter() {

    @Inject
    lateinit var dbManager: DBManager

    override fun addDataMain(newData: AccountMain) {
        mView!!.showLoading()
        if (checkMainDataIsNull(newData)) return
        val type = dbManager.addMainData(newData)
        if (type != -1L) {
            mView!!.hideLoading()
            mView!!.operationSuccess()
        } else {
            mView!!.showError("您插入的账号类型已存在")
        }
    }

    override fun updateDataMain(newData: AccountMain, oldData: AccountMain?) {
        mView!!.showLoading()
        if (checkMainDataIsNull(newData)) return
        val type = dbManager.updateMainData(oldData, newData)
        if (type != -1) {
            mView!!.hideLoading()
            mView!!.operationSuccess()
        } else {
            mView!!.showError("未知错误")
        }
    }

    private fun checkMainDataIsNull(newData: AccountMain): Boolean {
        if (TextUtils.isEmpty(newData.account_type_name)) {
            mView!!.showError("账号类型不能为空")
            return true
        } else if (TextUtils.isEmpty(newData.account_type_describe)) {
            mView!!.showError("账号类型描述不能为空")
            return true
        }
        return false
    }


    override fun addDataList(accountList: AccountList) {
        mView!!.showLoading()
        if (checkAccountListIsNull(accountList)) return
        val type = dbManager.addAccountListData(accountList)
        if (type != -1L) {
            mView!!.hideLoading()
            mView!!.operationSuccess()
        } else {
            mView!!.showError("您插入的账号已存在")
        }
    }

    override fun updateDataList(newData: AccountList, oldData: AccountList?) {
        mView!!.showLoading()
        if (checkAccountListIsNull(newData)) return
        val type = dbManager.updateAccountListData(oldData, newData)
        if (type != -1) {
            mView!!.hideLoading()
            mView!!.operationSuccess()
        } else {
            mView!!.showError("未知错误")
        }
    }

    private fun checkAccountListIsNull(accountList: AccountList): Boolean {
        if (TextUtils.isEmpty(accountList.account_type_name)) {
            mView!!.showError("账号类型不能为空")
            return true
        } else if (TextUtils.isEmpty(accountList.application_name)) {
            mView!!.showError("软件应用名称不能为空")
            return true
        } else if (TextUtils.isEmpty(accountList.account)) {
            mView!!.showError("用户名不能为空")
            return true
        } else if (TextUtils.isEmpty(accountList.password)) {
            mView!!.showError("密码不能为空")
            return true
        }
        return false
    }

}