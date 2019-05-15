package com.account.number.ui.accountlist

import android.util.Log
import androidx.lifecycle.Lifecycle
import com.account.number.db.AccountList
import com.account.number.db.DBManager
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.lifecycle.autoDisposable
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList
import javax.inject.Inject

class AccountListPresenter @Inject constructor() : AccountListContract.AccountListPresenter() {
    @Inject
    lateinit var dbManager: DBManager

    override fun loadData(accountTypeName: String) {
        Observable.create(ObservableOnSubscribe<ArrayList<AccountList>> { emitter ->
            val accountLists = dbManager.queryAccountListData(accountTypeName)
            emitter.onNext(accountLists)
        }).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(AndroidLifecycleScopeProvider.from(mView as AccountListFragment, Lifecycle.Event.ON_DESTROY))
            .subscribe {
                mView!!.loadDataSuccess(it)
            }
    }

    override fun deleteData(accountList: AccountList) {
        mView!!.showLoading()
        Observable.create(ObservableOnSubscribe<Int> { emitter ->
            val type = dbManager.deleteAccountListData(accountList)
            emitter.onNext(type)
        }).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(AndroidLifecycleScopeProvider.from(mView as AccountListFragment, Lifecycle.Event.ON_DESTROY))
            .subscribe {
                mView!!.hideLoading()
                mView!!.deleteDataSuccess()
            }
    }
}