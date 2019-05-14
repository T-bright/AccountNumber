package com.account.number.base


abstract class BasePresenter<T : BaseView> {
    var mView: T? = null

    open fun attach(view: T) {
        mView = view
    }

    open fun detach() {
        mView?.hideLoading()
        mView = null
    }

}