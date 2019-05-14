package com.account.number.ui

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.account.number.MyApplication
import com.account.number.R
import com.account.number.db.AccountList
import com.account.number.db.AccountMain
import com.scwang.smartrefresh.layout.util.DensityUtil
import javax.inject.Inject

class MyAdapter @Inject constructor(
    var layoutID: Int,
    var result: ArrayList<out Any>,
    var type: String = ACCOUNTMAIN_OBJECT
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    private val typeStrs = arrayOf("", "用  户  名：", "密        码：", "电       话：", "邮        箱：", "仓库密码：")
    companion object {
        val ACCOUNTMAIN_OBJECT = "1"//表示主頁的
        val ACCOUNTLIST_OBJECT = "2"
    }

    fun setData(result: ArrayList<out Any>) {
        this.result = result
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(MyApplication.instance).inflate(layoutID, parent, false)
        return MyViewHolder(view)
    }


    override fun getItemCount(): Int = result.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if (position == 0) setLayoutParams(15f, holder) else setLayoutParams(0f, holder)

        if (type == ACCOUNTMAIN_OBJECT) bindMainViewHolder(holder, position) else bindMAccountListViewHolder(holder, position)

    }


    private fun setLayoutParams(margin: Float, myViewHolder: MyViewHolder) {
        val layoutParams = myViewHolder.root.layoutParams as RecyclerView.LayoutParams
        layoutParams.topMargin = DensityUtil.dp2px(margin)
        myViewHolder.root.layoutParams = layoutParams
    }

    fun bindMainViewHolder(holder: MyViewHolder, position: Int) {
        val accountMain = result.get(position) as AccountMain
        with(holder) {
            name.text = accountMain.account_type_name
            describe.text = accountMain.account_type_describe
            root.setOnClickListener { itemClickListener?.onItemClick(accountMain) }
            redact.setOnClickListener { itemClickListener?.onItemRedact(accountMain) }
            delete.setOnClickListener { itemClickListener?.onItemDelete(accountMain) }
        }
    }

    fun bindMAccountListViewHolder(holder: MyViewHolder, position: Int) {
        val accountList = result.get(position) as AccountList
        val accountLists = convertToList(accountList)
        for (i in 0..5) {
            setText(typeStrs[i], accountLists[i], holder.texts[i]!!, holder.lineViews[i]!!, false)
        }
        with(holder) {
            root.setOnClickListener {
                setText(typeStrs[2], accountLists.get(2), texts[2]!!, lineViews[2]!!, true)
                setText(typeStrs[5], accountLists.get(5), texts[5]!!, lineViews[5]!!, true)
            }
            redact.setOnClickListener { itemClickListener?.onItemRedact(accountList) }
            delete.setOnClickListener { itemClickListener?.onItemDelete(accountList) }
        }
    }

    private fun convertToList(accountList: AccountList): MutableList<String> {
        val list = mutableListOf<String>()
        list.add(accountList.application_name)
        list.add(accountList.account)
        list.add(accountList.password)
        list.add(accountList.phone_number)
        list.add(accountList.associated_mailbox)
        list.add(accountList.repository_password)
        return list
    }

    /**
     * @param typeStr
     * @param content
     * @param textView
     * @param lineView
     * @param isShowPas : 是否显示密码
     */
    private fun setText(typeStr: String, content: String, textView: TextView, lineView: View, isShowPas: Boolean) {
        var content = content
        if (TextUtils.isEmpty(content)) {
            textView.visibility = View.GONE
            lineView.visibility = View.GONE
        } else {
            textView.visibility = View.VISIBLE
            lineView.visibility = View.VISIBLE
            if (!isShowPas && typeStr.contains("密") && typeStr.contains("码")) {
                content = "********"
            }
            textView.text = typeStr + content
        }
    }

    lateinit var itemClickListener: ItemClickListener

    fun setOnItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(t: Any)

        fun onItemRedact(t: Any)

        fun onItemDelete(t: Any)
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var root: RelativeLayout
        lateinit var redact: ImageView
        lateinit var delete: ImageView

        lateinit var name: TextView
        lateinit var describe: TextView

        var application_name: TextView? = null
        var account_name: TextView? = null
        var password: TextView? = null
        var phone_number: TextView? = null
        var mailbox_name: TextView? = null
        var repertory_psw: TextView? = null

        var application_name_line: View? = null
        var account_name_line: View? = null
        var password_line: View? = null
        var phone_number_line: View? = null
        var mailbox_name_line: View? = null
        var repertory_psw_line: View? = null
        val textIds = intArrayOf(
            R.id.application_name,
            R.id.account_name,
            R.id.password,
            R.id.phone_number,
            R.id.mailbox_name,
            R.id.repertory_psw
        )
        val texts =
            arrayOf<TextView?>(application_name, account_name, password, phone_number, mailbox_name, repertory_psw)
        val lineIds = intArrayOf(
            R.id.application_name_line,
            R.id.account_name_line,
            R.id.password_line,
            R.id.phone_number_line,
            R.id.mailbox_name_line,
            R.id.repertory_psw_line
        )
        val lineViews = arrayOf<View?>(
            application_name_line,
            account_name_line,
            password_line,
            phone_number_line,
            mailbox_name_line,
            repertory_psw_line
        )

        init {
            root = view.findViewById<RelativeLayout>(R.id.root)
            redact = view.findViewById<ImageView>(R.id.redact)
            delete = view.findViewById<ImageView>(R.id.delete)
            if (type == ACCOUNTMAIN_OBJECT) {
                name = view.findViewById<TextView>(R.id.name)
                describe = view.findViewById<TextView>(R.id.describe)
            } else {
                for (i in textIds.indices) {
                    texts[i] = view.findViewById<TextView>(textIds[i])
                }
                for (i in lineIds.indices) {
                    lineViews[i] = itemView.findViewById(lineIds[i])
                }
            }
        }
    }

}