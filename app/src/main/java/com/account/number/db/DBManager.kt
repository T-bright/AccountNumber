package com.account.number.db

import android.content.ContentValues
import com.account.number.extension.parseList
import org.jetbrains.anko.db.select
import javax.inject.Inject

class DBManager @Inject constructor() {

    @Inject
    lateinit var databaseOpenHelper: DatabaseOpenHelper

    //添加数据
    fun addMainData(accountMain: AccountMain): Long {
        synchronized(databaseOpenHelper) {
            var type = databaseOpenHelper?.use {
                val contentValues = ContentValues()
                contentValues.put("account_type_name", accountMain.account_type_name)
                contentValues.put("account_type_describe", accountMain.account_type_describe)
                insert(DatabaseOpenHelper.TableAccountMain.TABLE_NAME, null, contentValues)
            }
            return type
        }
    }

    //删除数据
    fun deleteMainData(accountTypeName: String): Int {
        synchronized(databaseOpenHelper) {
            var type = databaseOpenHelper.use {
                delete(DatabaseOpenHelper.TableAccountMain.TABLE_NAME, "account_type_name = ?", Array<String>(1) { accountTypeName })
            }
            return type
        }
    }

    //修改数据
    fun updateMainData(oldData: AccountMain?, newData: AccountMain): Int {
        synchronized(databaseOpenHelper) {
            var type = databaseOpenHelper.use {
                val newContentValues = ContentValues()
                newContentValues.put("account_type_describe", newData.account_type_describe)
                update(DatabaseOpenHelper.TableAccountMain.TABLE_NAME, newContentValues, "account_type_name = ?  ", arrayOf<String>(oldData!!.account_type_name))
            }
            return type
        }
    }

    //查询数据
    fun queryMainData(): ArrayList<AccountMain> {
        synchronized(databaseOpenHelper) {
            return databaseOpenHelper?.use {
                val mutableList = arrayListOf<AccountMain>()

                val parseList = databaseOpenHelper.use {
                    select(DatabaseOpenHelper.TableAccountMain.TABLE_NAME).parseList()
                }
                parseList.forEach {
                    mutableList.add(AccountMain(it[0] as String, it[1] as String))
                }
                return@use mutableList
            }
        }
    }


    //添加数据
    fun addAccountListData(accountList: AccountList): Long {
        synchronized(databaseOpenHelper) {
            var type = databaseOpenHelper.use {
                val contentValues = ContentValues()
                contentValues.put("account_type_name", accountList.account_type_name)
                contentValues.put("application_name", accountList.application_name)
                contentValues.put("account", accountList.account)
                contentValues.put("password", accountList.password)
                contentValues.put("phone_number", accountList.phone_number)
                contentValues.put("associated_mailbox", accountList.associated_mailbox)
                contentValues.put("repository_password", accountList.repository_password)
                insert(DatabaseOpenHelper.TableAccountList.TABLE_NAME, null, contentValues)
            }
            return type
        }
    }

    //删除数据
    fun deleteAccountListData(accountList: AccountList): Int {
        synchronized(databaseOpenHelper) {
            var type = databaseOpenHelper.use {
                delete(DatabaseOpenHelper.TableAccountList.TABLE_NAME, "account_type_name = ?  and application_name = ? and account = ?", arrayOf<String>(accountList!!.account_type_name, accountList.application_name, accountList.account))
            }
            return type
        }
    }

    //修改数据
    fun updateAccountListData(oldData: AccountList?, newData: AccountList): Int {
        synchronized(databaseOpenHelper) {
            var type = databaseOpenHelper.use {
                val newContentValues = ContentValues()
                newContentValues.put("password", newData.password)
                newContentValues.put("phone_number", newData.phone_number)
                newContentValues.put("associated_mailbox", newData.associated_mailbox)
                newContentValues.put("repository_password", newData.repository_password)
                update(DatabaseOpenHelper.TableAccountList.TABLE_NAME, newContentValues, "account_type_name = ?  and application_name = ? and account = ?", arrayOf<String>(oldData!!.account_type_name, oldData.application_name, oldData.account))
            }
            return type
        }
    }

    //查询数据
    fun queryAccountListData(accountTypeName: String): ArrayList<AccountList> {
        synchronized(databaseOpenHelper) {
            return databaseOpenHelper?.use {
                val mutableList = arrayListOf<AccountList>()

                val parseList = databaseOpenHelper.use {
                    select(DatabaseOpenHelper.TableAccountList.TABLE_NAME).whereSimple("account_type_name = ?", accountTypeName).parseList()
                }
                parseList.forEach {
                    mutableList.add(AccountList(it[0] as String, it[1] as String, it[2] as String, it[3] as String, it[4] as String, it[5] as String, it[6] as String))
                }
                return@use mutableList
            }
        }
    }

}