package com.account.number.ui.main

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.account.number.MyApplication
import com.account.number.R
import com.account.number.base.BaseFragment
import com.account.number.db.AccountMain
import com.account.number.dialog.InputSaveDialog
import com.account.number.ui.MyAdapter
import com.account.number.ui.accountlist.AccountListActivity
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.title_bar.*
import javax.inject.Inject

class MainFragment @Inject constructor() : BaseFragment(), MainContract.MainView {


    @Inject
    lateinit var mainPresenter: MainPresenter

    @Inject
    lateinit var mainDialog: InputSaveDialog

    override val contentViewId: Int = R.layout.fragment_main

    override fun onCreatingView(view: View, savedInstanceState: Bundle?) {
        mainPresenter.attach(this)
        val linearLayoutManager = LinearLayoutManager(MyApplication.instance)
        recycleview.layoutManager = linearLayoutManager

        title.text = "密码保险柜"
        export.setImageResource(R.mipmap.export)

        smart_refresh_layout.setEnableLoadMore(false)
        smart_refresh_layout.setEnableRefresh(true)
        smart_refresh_layout.setOnRefreshListener { mainPresenter.loadData() }
        smart_refresh_layout.autoRefresh()
        add.setOnClickListener { showMainInputDialog(null) }
        export.setOnClickListener {
            dialogType = SAVING
            showDialog("是否将私有数据库加密的用户名密码导出到\nSD卡根目录文件密码保险柜.txt")
        }

        mainDialog!!.setDialogOperationSuccessListener(object : InputSaveDialog.DialogOperationSuccessListener {
            override fun onOperationSuccess() {
                smart_refresh_layout.autoRefresh()
            }
        })
    }

    var myAdapter: MyAdapter? = null
    override fun loadDataSuccess(results: ArrayList<AccountMain>) {
        smart_refresh_layout.finishRefresh()
        setAdapterDatas(results)

        myAdapter!!.setOnItemClickListener(object : MyAdapter.ItemClickListener {
            override fun onItemRedact(t: Any) {
                if (t is AccountMain) {
                    val accountMain = t as AccountMain
                    showMainInputDialog(accountMain)
                }
            }

            override fun onItemDelete(t: Any) {
                if (t is AccountMain) {
                    val accountMain = t as AccountMain
                    mainPresenter.deleteData(accountMain.account_type_name)
                }
            }

            override fun onItemClick(t: Any) {
                if (t is AccountMain) {
                    val accountMain = t as AccountMain
                    AccountListActivity.start(activity!!, accountMain.account_type_name)
                }
            }
        })
    }

    private fun showMainInputDialog(accountMain: AccountMain?) {
        mainDialog!!
            .showDialog(fragmentManager!!, "mainDialog")
            .arguments = InputSaveDialog.setDialogData(InputSaveDialog.MAIN_DIALOG, accountMain = accountMain)
    }

    private fun setAdapterDatas(results: ArrayList<AccountMain>) {
        if (myAdapter == null) {
            myAdapter = MyAdapter(R.layout.main_item_recycle, results)
            recycleview.adapter = myAdapter
        } else {
            myAdapter!!.setData(results)
        }
    }

    override fun permissionIsGranted(granted: Boolean) {
        if (!granted) {
            showDialog("请授权SD卡读写权限，否则无法保存到SD中")
        }
    }

    private var dialogType = 0
    val SAVING = 1//确定保存

    override fun saveDataResult(isSaveSuccess: Boolean) {
        showDialog("账号密码已导出，请及时处理，\n以免账号密码泄露!")
    }

    override fun deleteDataSuccess() {
        smart_refresh_layout.autoRefresh()
    }

    override fun positiveDialogButton(dialog: DialogInterface) {
        if (dialogType == SAVING) {
            mainPresenter.saveData(myAdapter!!.result, activity!!)
        }
        dialogType = 0
    }

    override fun onDetach() {
        super.onDetach()
        mainPresenter.detach()
    }
}