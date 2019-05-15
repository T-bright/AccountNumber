package com.account.number.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import com.account.number.db.AccountMain
import com.account.number.db.DBManager
import com.account.number.utils.fileutils.FileIoUtils
import com.account.number.utils.fileutils.FilePathUtils
import com.account.number.utils.fileutils.FileUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.lifecycle.autoDisposable
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList
import javax.inject.Inject

class MainPresenter @Inject constructor() : MainContract.MainPresenter() {

    @Inject
    lateinit var dbManager: DBManager

    override fun loadData() {
        Observable.create(ObservableOnSubscribe<ArrayList<AccountMain>> { emitter ->
            val queryMainDatas = dbManager.queryMainData()
            emitter.onNext(queryMainDatas)
        }).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(AndroidLifecycleScopeProvider.from(mView as MainFragment, Lifecycle.Event.ON_DESTROY))
            .subscribe {
                mView!!.loadDataSuccess(it)
            }
    }

    override fun deleteData(accountTypeName: String) {
        mView!!.showLoading()
        Observable.create(ObservableOnSubscribe<Int> { emitter ->
            val type = dbManager.deleteMainData(accountTypeName)
            emitter.onNext(type)
        }).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(AndroidLifecycleScopeProvider.from(mView as MainFragment, Lifecycle.Event.ON_DESTROY))
            .subscribe {
                mView!!.hideLoading()
                mView!!.deleteDataSuccess()
            }
    }


    @SuppressLint("CheckResult")
    override fun saveData(results: ArrayList<out Any>, activity: FragmentActivity) {
        val rxPermissions = RxPermissions(activity)
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
            .subscribe { granted ->
                run {
                    if (granted) {//允许
                        writeToTxt(results)
                    } else {//不允许
                        mView!!.permissionIsGranted(false)
                    }
                }
            }
    }

    @SuppressLint("CheckResult")
    private fun writeToTxt(results: ArrayList<out Any>) {
        mView!!.showLoading()
        Observable
            .create(ObservableOnSubscribe<Boolean> {
                Thread.sleep(1000)
                val path = FilePathUtils.getExtStoragePath() + "/密码保险柜.txt"
                if (FileUtils.isFileExists(path)) {
                    FileUtils.deleteFile(path)
                }
                val content = StringBuilder()
                for (accountMains in results) {
                    val accountMain = accountMains as AccountMain
                    val accountTypeName = accountMain.account_type_name
                    val accountTypeDescribe = accountMain.account_type_describe
                    //写到txt卡里
                    content.append("账号类型   ：$accountTypeName").append("\r\n").append("账号类型描述   ：$accountTypeDescribe").append("\r\n\r\n")
                    val accountLists = dbManager.queryAccountListData(accountTypeName)
                    for (accountList in accountLists) {
                        content.append("\t").append("应用名   ：" + accountList.application_name).append("\r\n")
                        content.append("\t").append("用户名   ：" + accountList.account).append("\r\n")
                        content.append("\t").append("用户密码 ：" + accountList.password).append("\r\n")
                        content.append("\t").append("电话号码 ：" + accountList.phone_number).append("\r\n")
                        content.append("\t").append("邮箱     ：" + accountList.associated_mailbox).append("\r\n")
                        content.append("\t").append("仓库密码 ：" + accountList.repository_password).append("\r\n\r\n")
                        FileIoUtils.save(path, content.toString(), true)
                        content.delete(0, content.length)
                    }
                }
                it.onNext(true)
            })
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(AndroidLifecycleScopeProvider.from(mView as MainFragment, Lifecycle.Event.ON_DESTROY))
            .subscribe {
                mView!!.hideLoading()
                mView!!.saveDataResult(it)
            }
    }
}


