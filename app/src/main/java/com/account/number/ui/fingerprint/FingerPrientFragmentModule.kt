package com.account.number.ui.fingerprint

import com.account.number.dagger.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FingerPrientFragmentModule {

    @get:FragmentScoped
    @get:ContributesAndroidInjector
    val fingerPrientFragment: FingerPrientFragment

}