package com.account.number.db

import android.os.Parcel
import android.os.Parcelable


data class AccountList(var account_type_name: String,
                       var application_name: String,
                       var account: String,
                       var password: String,
                       var phone_number: String,
                       var associated_mailbox: String,
                       var repository_password: String
                       ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(account_type_name)
        parcel.writeString(application_name)
        parcel.writeString(account)
        parcel.writeString(password)
        parcel.writeString(phone_number)
        parcel.writeString(associated_mailbox)
        parcel.writeString(repository_password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AccountList> {
        override fun createFromParcel(parcel: Parcel): AccountList {
            return AccountList(parcel)
        }

        override fun newArray(size: Int): Array<AccountList?> {
            return arrayOfNulls(size)
        }
    }
}
