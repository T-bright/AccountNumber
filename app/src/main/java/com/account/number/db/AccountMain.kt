package com.account.number.db

import android.os.Parcel
import android.os.Parcelable


/**
 * 主页的bean类
 * @param account_type_name :密码的类型,主键。密码的类型不能相同，如account_type_name="银行"，就表示银行类型密码
 * @param account_type_describe ：密码类型的说明。
 */
data class AccountMain(var account_type_name: String, var account_type_describe: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(account_type_name)
        parcel.writeString(account_type_describe)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AccountMain> {
        override fun createFromParcel(parcel: Parcel): AccountMain {
            return AccountMain(parcel)
        }

        override fun newArray(size: Int): Array<AccountMain?> {
            return arrayOfNulls(size)
        }
    }
}

