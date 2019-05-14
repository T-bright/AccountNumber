package com.account.number.utils

import android.content.Context
import android.content.SharedPreferences
import com.account.number.MyApplication
import java.lang.ClassCastException

object SPUtils {
    private val name = "SP_Data"
    private val sp: SharedPreferences by lazy {
        MyApplication.instance!!.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    fun getString(key: String, default: String = ""): String {
        return getValue(key, default) as String
    }

    fun getInt(key: String, default: Int = 0): Int {
        return getValue(key, default) as Int
    }

    fun getLong(key: String, default: Long = 0): Long {
        return getValue(key, default) as Long
    }

    fun getBoolean(key: String, default: Boolean = false): Boolean {
        return getValue(key, default) as Boolean
    }

    fun getFloat(key: String, default: Float = 0f): Float {
        return getValue(key, default) as Float
    }

    private fun getValue(key: String, value: Any): Any {
        return sp.run {
            when (value) {
                is Int -> getInt(key, value)
                is String -> getString(key, value)
                is Long -> getLong(key, value)
                is Float -> getFloat(key, value)
                is Boolean -> getBoolean(key, value)
                else -> throw ClassCastException("不支持类型")
            }
        }
    }

    fun save(key: String, value: Any) {
        return sp.edit().run {
            when (value) {
                is Long -> putLong(key, value)
                is Int -> putInt(key, value)
                is String -> putString(key, value)
                is Float -> putFloat(key, value)
                is Boolean -> putBoolean(key, value)
                else -> throw ClassCastException("不支持类型")
            }
        }.apply()
    }
}