package com.animalgame.data.model

import android.os.Parcel
import android.os.Parcelable

data class OrderBookItem(
    val price: Double,
    val amount: Double
) : Parcelable {
    val total: Double
        get() = price * amount

    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(price)
        parcel.writeDouble(amount)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<OrderBookItem> {
        override fun createFromParcel(parcel: Parcel): OrderBookItem = OrderBookItem(parcel)
        override fun newArray(size: Int): Array<OrderBookItem?> = arrayOfNulls(size)
    }
}
