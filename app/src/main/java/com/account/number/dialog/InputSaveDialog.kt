package com.account.number.dialog

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentManager
import com.account.number.R
import com.account.number.base.BaseDialog
import com.account.number.db.AccountList
import com.account.number.db.AccountMain
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import javax.inject.Inject


class InputSaveDialog @Inject constructor() : BaseDialog(), DiaLogContract.DialogView {

    companion object {
        val MAIN_DIALOG = "0"//main主页的dialog
        val ACCOUTN_DIALOG = "1"//点击主页面的列表item进入的二级页面的弹框
        fun setDialogData(type: String, accountMain: AccountMain? = null, accountList: AccountList? = null, accountTypeName: String = ""): Bundle {
            val bundle = Bundle()
            bundle.putString("type", type)
            bundle.putParcelable("accountMain", accountMain)
            bundle.putParcelable("accountList", accountList)
            bundle.putString("accountTypeName", accountTypeName)
            return bundle
        }
    }

    @Inject
    lateinit var dialogPresenter: DialogPresenter

    private var accountMain: AccountMain? = null
    private var accountList: AccountList? = null
    private var type: String? = MAIN_DIALOG

    private var accountTypeName: String? = ""

    override fun initData() {
        if (type == MAIN_DIALOG) {
            initMainData()
        } else {
            initAccountListData()
        }
    }

    override fun setUpLayoutId(): Int {
        dialogPresenter.attach(this)
        type = arguments?.getString("type")
        accountMain = arguments?.getParcelable("accountMain")
        accountList = arguments?.getParcelable("accountList")
        accountTypeName = arguments?.getString("accountTypeName")
        if (type == MAIN_DIALOG) {
            return R.layout.dialog_input_save_main
        } else {
            return R.layout.dialog_input_save_account
        }
    }

    override fun creatingView(view: View) {
        if (type == MAIN_DIALOG) {
            convertMainView(view)
        } else {
            convertAccountView(view)
        }

    }

    var account_type_name: EditText? = null
    var account_type_describe: EditText? = null
    var make_sure: Button? = null
    fun convertMainView(view: View) {
        account_type_name = view.find<EditText>(R.id.account_type_name)
        account_type_describe = view.find<EditText>(R.id.account_type_describe)
        make_sure = view.find<Button>(R.id.make_sure)
        make_sure!!.setOnClickListener { replaceMainData() }
    }

    private fun replaceMainData() {
        var accountTypeName = account_type_name!!.text.toString()
        var accountTypeDescribe = account_type_describe!!.text.toString()
        if (accountMain == null) {//添加
            dialogPresenter.addDataMain(AccountMain(accountTypeName, accountTypeDescribe))
        } else {//修改
            dialogPresenter.updateDataMain(AccountMain(accountTypeName, accountTypeDescribe), accountMain)
        }
    }

    var application_name: EditText? = null
    var account_name: EditText? = null
    var password: EditText? = null
    var phone_number: EditText? = null
    var mailbox_name: EditText? = null
    var repertory_psw: EditText? = null
    fun convertAccountView(view: View) {
        application_name = view.find<EditText>(R.id.application_name)
        account_name = view.find<EditText>(R.id.account_name)
        password = view.find<EditText>(R.id.password)
        phone_number = view.find<EditText>(R.id.phone_number)
        mailbox_name = view.find<EditText>(R.id.mailbox_name)
        repertory_psw = view.find<EditText>(R.id.repertory_psw)
        make_sure = view.find<Button>(R.id.make_sure)
        make_sure!!.setOnClickListener { replaceAccountListData() }
    }


    fun replaceAccountListData() {
        var applicationName = application_name!!.text.toString()
        var account = account_name!!.text.toString()
        var password = password!!.text.toString()
        var phoneNumber = phone_number!!.text.toString()
        var mailboxName = mailbox_name!!.text.toString()
        var repertoryPsw = repertory_psw!!.text.toString()

        if (accountList == null) {//添加
            dialogPresenter.addDataList(AccountList(accountTypeName!!,applicationName, account, password, phoneNumber, mailboxName, repertoryPsw))
        } else {//修改
            dialogPresenter.updateDataList(AccountList(accountTypeName!!, applicationName, account, password, phoneNumber, mailboxName, repertoryPsw), accountList)
        }
    }

    fun showDialog(manager: FragmentManager, tag: String): InputSaveDialog {
        super.show(manager, "${tag}")
        return this
    }

    private fun initMainData() {
        val editTexts = arrayOf(account_type_name!!, account_type_describe!!)
        if (accountMain != null) {
            setEdittextNotInput(account_type_name!!)
            setText(editTexts, arrayOf(accountMain!!.account_type_name, accountMain!!.account_type_describe))
        } else {
            setEdittextCanInput(account_type_name!!)
            setText(editTexts, arrayOf("", ""))
        }
    }

    private fun initAccountListData() {
        val editTexts = arrayOf(application_name!!, account_name!!, password!!, phone_number!!, mailbox_name!!, repertory_psw!!)
        if (accountList != null) {
            setEdittextNotInput(application_name!!, account_name!!)
            setText(editTexts, arrayOf(accountList!!.application_name, accountList!!.account, accountList!!.password, accountList!!.phone_number, accountList!!.associated_mailbox, accountList!!.repository_password))
        } else {
            setEdittextCanInput(application_name!!, account_name!!)
            setText(editTexts, arrayOf("", "", "", "", "", ""))
        }
    }

    private fun setText(editTexts: Array<EditText>, texts: Array<String>) {
        for (index in editTexts.indices) {
            val editText = editTexts[index]
            editText.setText(texts[index])
        }
    }


    override fun showError(status: String) {
        super.showError(status)
        activity!!.toast(status)
    }

    override fun onDestroy() {
        super.onDestroy()
        dialogPresenter.detach()
    }

    override fun operationSuccess() {
        dismiss()
        dialogOperationSuccessListener?.onOperationSuccess()
    }

    private var dialogOperationSuccessListener: DialogOperationSuccessListener? = null

    interface DialogOperationSuccessListener {
        fun onOperationSuccess()
    }

    fun setDialogOperationSuccessListener(dialogOperationSuccessListener: DialogOperationSuccessListener?) {
        this.dialogOperationSuccessListener = dialogOperationSuccessListener
    }

    private fun setEdittextNotInput(vararg editTexts: EditText) {
        val length = editTexts.size
        for (i in 0 until length) {
            val editText = editTexts[i]
            editText.isFocusable = false
            editText.isFocusableInTouchMode = false
        }
    }

    private fun setEdittextCanInput(vararg editTexts: EditText) {
        val length = editTexts.size
        for (i in 0 until length) {
            val editText = editTexts[i]
            editText.isFocusable = true
            editText.isFocusableInTouchMode = true
            editText.requestFocus()
        }
    }
}