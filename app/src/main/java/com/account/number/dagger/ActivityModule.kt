package com.account.number.dagger

import com.account.number.ui.fingerprint.FingerPrintActivity
import com.account.number.ui.SplashActivity
import com.account.number.ui.accountlist.AccountListActivity
import com.account.number.ui.accountlist.AccountListDialogModule
import com.account.number.ui.fingerprint.FingerPrientFragmentModule
import com.account.number.ui.main.MainActivity
import com.account.number.ui.main.MainInputDialogModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
public abstract class ActivityModule {

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun contributeSplashActivity(): SplashActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [FingerPrientFragmentModule::class])
    internal abstract fun contributeFingerPrintActivity(): FingerPrintActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainInputDialogModule::class])
    internal abstract fun contributeMainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [AccountListDialogModule::class])
    internal abstract fun contributeAccountListActivity(): AccountListActivity

}