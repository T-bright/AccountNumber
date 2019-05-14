package com.account.number.ui.accountlist

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.account.number.MyApplication
import com.account.number.R
import com.account.number.base.BaseFragment
import com.account.number.db.AccountList
import com.account.number.dialog.InputSaveDialog
import com.account.number.ui.MyAdapter
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.title_bar.*
import javax.inject.Inject

class AccountListFragment @Inject constructor() : BaseFragment(), AccountListContract.AccountListView {


    @Inject
    lateinit var accountListPresenter: AccountListPresenter


    @Inject
    lateinit var accountListDialog: InputSaveDialog

    override val contentViewId: Int = R.layout.fragment_main

    var account_type_name: String? = ""

    override fun onCreatingView(view: View, savedInstanceState: Bundle?) {
        accountListPresenter.attach(this)
        account_type_name = activity!!.intent!!.getStringExtra("account_type_name")
        val linearLayoutManager = LinearLayoutManager(MyApplication.instance)
        recycleview.layoutManager = linearLayoutManager

        title.text = account_type_name
        export.setImageResource(R.mipmap.back_ic)

        smart_refresh_layout.setEnableLoadMore(false)
        smart_refresh_layout.setEnableRefresh(true)
        smart_refresh_layout.setOnRefreshListener { accountListPresenter.loadData(account_type_name!!) }
        smart_refresh_layout.autoRefresh()

        add.setOnClickListener { showAccountListInputDialog(null) }
        export.setOnClickListener { activity!!.finish() }
        accountListDialog!!.setDialogOperationSuccessListener(object : InputSaveDialog.DialogOperationSuccessListener {
            override fun onOperationSuccess() {
                smart_refresh_layout.autoRefresh()
            }
        })
    }


    var myAdapter: MyAdapter? = null
    override fun loadDataSuccess(results: ArrayList<AccountList>) {
        smart_refresh_layout.finishRefresh()
        setAdapterDatas(results)
        myAdapter!!.setOnItemClickListener(object : MyAdapter.ItemClickListener {
            override fun onItemRedact(t: Any) {
                if (t is AccountList) {
                    val accountList = t as AccountList
                    showAccountListInputDialog(accountList)
                }
            }

            override fun onItemDelete(t: Any) {
                if (t is AccountList) {
                    val accountList = t as AccountList
                    accountListPresenter.deleteData(accountList)
                }
            }

            override fun onItemClick(t: Any) {

            }
        })
    }

    private fun setAdapterDatas(results: ArrayList<AccountList>) {
        if (myAdapter == null) {
            myAdapter = MyAdapter(R.layout.accountlist_item_recycle, results, MyAdapter.ACCOUNTLIST_OBJECT)
            recycleview.adapter = myAdapter
        } else {
            myAdapter!!.setData(results)
        }
    }

    override fun deleteDataSuccess() {
        smart_refresh_layout.autoRefresh()
    }

    private fun showAccountListInputDialog(accountList: AccountList?) {
        accountListDialog!!
            .showDialog(fragmentManager!!, "accountListDialog")
            .arguments = InputSaveDialog.setDialogData(InputSaveDialog.ACCOUTN_DIALOG, accountList = accountList, accountTypeName = account_type_name!!)
    }

    override fun onDetach() {
        super.onDetach()
        accountListPresenter.detach()
    }
}