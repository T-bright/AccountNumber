package com.account.number

import com.account.number.dagger.AppComponent
import com.account.number.dagger.DaggerAppComponent
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class MyApplication : DaggerApplication() {
    var appComponent: AppComponent? = null
    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? {
        appComponent = DaggerAppComponent.builder().application(this).build()
        return appComponent
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }


    companion object {

        @JvmStatic
        var instance: MyApplication? = null

        init {
            //设置全局的Header构建器
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
                layout.setPrimaryColorsId(R.color.app_backage, R.color.text_color)//全局设置主题颜色
                ClassicsHeader(context)
            }
        }
    }
}