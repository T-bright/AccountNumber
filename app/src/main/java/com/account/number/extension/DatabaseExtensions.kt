package com.account.number.extension

import org.jetbrains.anko.db.RowParser
import org.jetbrains.anko.db.SelectQueryBuilder


fun SelectQueryBuilder.parseList(): List<Array<Any?>> =
    parseList(object : RowParser<Array<Any?>> {
        override fun parseRow(columns: Array<Any?>): Array<Any?> {
            return columns
        }
    })
