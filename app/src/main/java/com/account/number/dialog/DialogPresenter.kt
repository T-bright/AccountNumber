package com.account.number.dialog

import android.annotation.SuppressLint
import android.text.TextUtils
import androidx.lifecycle.Lifecycle
import com.account.number.db.AccountList
import com.account.number.db.AccountMain
import com.account.number.db.DBManager
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.lifecycle.autoDisposable
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DialogPresenter @Inject constructor() : DiaLogContract.DialogPresenter() {

    @Inject
    lateinit var dbManager: DBManager

    @SuppressLint("CheckResult")
    override fun addDataMain(newData: AccountMain) {
        mView!!.showLoading()
        if (checkMainDataIsNull(newData)) return
        Observable.create(ObservableOnSubscribe<Long> { emitter ->
            val type = dbManager.addMainData(newData)
            emitter.onNext(type)
        }).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(AndroidLifecycleScopeProvider.from((mView as InputSaveDialog).activity, Lifecycle.Event.ON_DESTROY))
            .subscribe {
                if (it != -1L) {
                    mView!!.hideLoading()
                    mView!!.operationSuccess()
                } else {
                    mView!!.showError("您插入的账号类型已存在")
                }
            }
    }

    @SuppressLint("CheckResult")
    override fun updateDataMain(newData: AccountMain, oldData: AccountMain?) {
        mView!!.showLoading()
        if (checkMainDataIsNull(newData)) return
        Observable.create(ObservableOnSubscribe<Int> { emitter ->
            val type = dbManager.updateMainData(oldData, newData)
            emitter.onNext(type)
        }).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(AndroidLifecycleScopeProvider.from((mView as InputSaveDialog).activity, Lifecycle.Event.ON_DESTROY))
            .subscribe {
                if (it != -1) {
                    mView!!.hideLoading()
                    mView!!.operationSuccess()
                } else {
                    mView!!.showError("未知错误")
                }
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


    @SuppressLint("CheckResult")
    override fun addDataList(accountList: AccountList) {
        mView!!.showLoading()
        if (checkAccountListIsNull(accountList)) return

        Observable.create(ObservableOnSubscribe<Long> { emitter ->
            val type = dbManager.addAccountListData(accountList)
            emitter.onNext(type)
        }).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it != -1L) {
                    mView!!.hideLoading()
                    mView!!.operationSuccess()
                } else {
                    mView!!.showError("您插入的账号已存在")
                }
            }
    }

    @SuppressLint("CheckResult")
    override fun updateDataList(newData: AccountList, oldData: AccountList?) {
        mView!!.showLoading()
        if (checkAccountListIsNull(newData)) return
        Observable.create(ObservableOnSubscribe<Int> { emitter ->
            val type = dbManager.updateAccountListData(oldData, newData)
            emitter.onNext(type)
        }).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it != -1) {
                    mView!!.hideLoading()
                    mView!!.operationSuccess()
                } else {
                    mView!!.showError("未知错误")
                }
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