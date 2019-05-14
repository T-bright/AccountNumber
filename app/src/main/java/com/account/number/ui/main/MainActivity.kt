package com.account.number.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.account.number.R
import com.account.number.base.BaseActivity
import com.account.number.extension.addToActivity
import javax.inject.Inject

class MainActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    @Inject
    lateinit var mainFragment: MainFragment

    override val contentViewId: Int = R.layout.activity_common

    override fun onCreating(savedInstanceState: Bundle?) {
        addToActivity(mainFragment)
    }

}
