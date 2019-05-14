package com.account.number.ui.main

import com.account.number.dagger.FragmentScoped
import com.account.number.dialog.InputSaveDialog
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainInputDialogModule {

    @get:FragmentScoped
    @get:ContributesAndroidInjector
    val inputSaveDialogFragment: InputSaveDialog

    @get:FragmentScoped
    @get:ContributesAndroidInjector
    val mainFragment: MainFragment

}