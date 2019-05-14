package com.account.number.db

import android.database.sqlite.SQLiteDatabase
import com.account.number.MyApplication
import org.jetbrains.anko.db.*
import javax.inject.Inject

public class DatabaseOpenHelper @Inject constructor() :
    ManagedSQLiteOpenHelper(MyApplication.instance!!.applicationContext, DB_NAME, null, DB_VERSION) {

    companion object {
        val DB_NAME = "person"
        val DB_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.createTable(
            TableAccountMain.TABLE_NAME, true,
            TableAccountMain.ACCOUNT_TYPE_NAME to TEXT + UNIQUE,
            TableAccountMain.ACCOUNT_TYPE_DESCRIBE to TEXT
        )

        var sqlStr = "CREATE TABLE ${TableAccountList.TABLE_NAME} (" +
                "${TableAccountList.ACCOUNT_TYPE_NAME} TEXT," +
                "${TableAccountList.APPLICATION_NAME} TEXT," +
                "${TableAccountList.ACCOUNT } TEXT," +
                "${TableAccountList.PASSWORD } TEXT," +
                "${TableAccountList.PHONE_NUMBER } TEXT," +
                "${TableAccountList.ASSOCIATED_MAILBOX } TEXT," +
                "${TableAccountList.REPOSITORY_PASSWORD } TEXT," +
                " PRIMARY KEY (" +
                "${TableAccountList.ACCOUNT_TYPE_NAME}," +
                "${TableAccountList.APPLICATION_NAME}," +
                "${TableAccountList.ACCOUNT} )" +
                ")"
        db.execSQL(sqlStr)

//        db.createTable(
//            TableAccountList.TABLE_NAME, true,
//            TableAccountList.ACCOUNT_TYPE_NAME to TEXT ,
//            TableAccountList.APPLICATION_NAME to TEXT ,
//            TableAccountList.ACCOUNT to TEXT ,
//            TableAccountList.PASSWORD to TEXT,
//            TableAccountList.PHONE_NUMBER to TEXT,
//            TableAccountList.ASSOCIATED_MAILBOX to TEXT,
//            TableAccountList.REPOSITORY_PASSWORD to TEXT
//        )
//        TODO 这种的混合主键怎么写
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }


    object TableAccountMain {
        val TABLE_NAME = "account_main"
        val ACCOUNT_TYPE_NAME = "account_type_name"
        val ACCOUNT_TYPE_DESCRIBE = "account_type_describe"
    }

    object TableAccountList {
        val TABLE_NAME = "account_list"
        val ACCOUNT_TYPE_NAME = "account_type_name"
        val APPLICATION_NAME = "application_name"
        val ACCOUNT = "account"
        val PASSWORD = "password"
        val PHONE_NUMBER = "phone_number"
        val ASSOCIATED_MAILBOX = "associated_mailbox"
        val REPOSITORY_PASSWORD = "repository_password"
    }
}