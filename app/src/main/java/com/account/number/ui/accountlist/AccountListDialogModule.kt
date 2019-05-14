package com.account.number.ui.accountlist

import com.account.number.dagger.FragmentScoped
import com.account.number.dialog.InputSaveDialog
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface AccountListDialogModule {

    @get:FragmentScoped
    @get:ContributesAndroidInjector
    val inputSaveDialogFragment: InputSaveDialog


    @get:FragmentScoped
    @get:ContributesAndroidInjector
    val accountListFragment: AccountListFragment
}