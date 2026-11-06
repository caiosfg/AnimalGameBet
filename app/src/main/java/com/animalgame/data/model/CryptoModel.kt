package com.animalgame.data.model

import android.os.Parcel
import android.os.Parcelable

data class CryptoModel(
    val id: String,
    val name: String,
    val symbol: String,
    val logoName: String,
    val currentPrice: Double,
    val changePercentage: Double,
    val highPrice: Double,
    val lowPrice: Double,
    val openPrice: Double,
    val closePrice: Double,
    val dailyChange: Double,
    val dailyVolume: String,
    val chartData1H: List<Float> = emptyList(),
    val chartData1D: List<Float> = emptyList(),
    val chartData1W: List<Float> = emptyList(),
    val chartData1M: List<Float> = emptyList(),
    val chartData1Y: List<Float> = emptyList(),
    val chartDataAll: List<Float> = emptyList(),
    val buyOrders: List<OrderBookItem> = emptyList(),
    val sellOrders: List<OrderBookItem> = emptyList(),
    val isBookmarked: Boolean = false
) : Parcelable {

    val isPositive: Boolean
        get() = changePercentage >= 0

    constructor(parcel: Parcel) : this(
        id = parcel.readString() ?: "",
        name = parcel.readString() ?: "",
        symbol = parcel.readString() ?: "",
        logoName = parcel.readString() ?: "",
        currentPrice = parcel.readDouble(),
        changePercentage = parcel.readDouble(),
        highPrice = parcel.readDouble(),
        lowPrice = parcel.readDouble(),
        openPrice = parcel.readDouble(),
        closePrice = parcel.readDouble(),
        dailyChange = parcel.readDouble(),
        dailyVolume = parcel.readString() ?: "",
        chartData1H = parcel.createFloatArray()?.toList() ?: emptyList(),
        chartData1D = parcel.createFloatArray()?.toList() ?: emptyList(),
        chartData1W = parcel.createFloatArray()?.toList() ?: emptyList(),
        chartData1M = parcel.createFloatArray()?.toList() ?: emptyList(),
        chartData1Y = parcel.createFloatArray()?.toList() ?: emptyList(),
        chartDataAll = parcel.createFloatArray()?.toList() ?: emptyList(),
        buyOrders = parcel.createTypedArrayList(OrderBookItem.CREATOR) ?: emptyList(),
        sellOrders = parcel.createTypedArrayList(OrderBookItem.CREATOR) ?: emptyList(),
        isBookmarked = parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(symbol)
        parcel.writeString(logoName)
        parcel.writeDouble(currentPrice)
        parcel.writeDouble(changePercentage)
        parcel.writeDouble(highPrice)
        parcel.writeDouble(lowPrice)
        parcel.writeDouble(openPrice)
        parcel.writeDouble(closePrice)
        parcel.writeDouble(dailyChange)
        parcel.writeString(dailyVolume)
        parcel.writeFloatArray(chartData1H.toFloatArray())
        parcel.writeFloatArray(chartData1D.toFloatArray())
        parcel.writeFloatArray(chartData1W.toFloatArray())
        parcel.writeFloatArray(chartData1M.toFloatArray())
        parcel.writeFloatArray(chartData1Y.toFloatArray())
        parcel.writeFloatArray(chartDataAll.toFloatArray())
        parcel.writeTypedList(buyOrders)
        parcel.writeTypedList(sellOrders)
        parcel.writeByte(if (isBookmarked) 1 else 0)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<CryptoModel> {
        override fun createFromParcel(parcel: Parcel): CryptoModel = CryptoModel(parcel)
        override fun newArray(size: Int): Array<CryptoModel?> = arrayOfNulls(size)
    }
}
