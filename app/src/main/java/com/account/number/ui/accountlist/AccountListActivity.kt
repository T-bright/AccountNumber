package com.account.number.ui.accountlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.account.number.R
import com.account.number.base.BaseActivity
import com.account.number.extension.addToActivity
import javax.inject.Inject

class AccountListActivity : BaseActivity() {
    companion object {
        fun start(context: Context, account_type_name: String) {
            val intent = Intent(context, AccountListActivity::class.java)
            intent.putExtra("account_type_name", account_type_name)
            context.startActivity(intent)
        }
    }

    override val contentViewId: Int = R.layout.activity_common

    @Inject
    lateinit var accountListFragment: AccountListFragment

    override fun onCreating(savedInstanceState: Bundle?) {
        addToActivity(accountListFragment)
    }
}
