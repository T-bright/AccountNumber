package com.account.number.base

interface BaseView {

    fun showLoading()

    fun hideLoading()

    fun showError(status: String)
}