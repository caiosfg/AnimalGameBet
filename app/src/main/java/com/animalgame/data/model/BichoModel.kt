package com.animalgame.data.model

import android.os.Parcel
import android.os.Parcelable
import java.util.Locale

data class BichoAnimal(
    val group: Int,
    val name: String,
    val dozenas: List<Int>
) : Parcelable {
    val formattedGroup: String
        get() = String.format(Locale.US, "%02d", group)

    constructor(parcel: Parcel) : this(
        group = parcel.readInt(),
        name = parcel.readString() ?: "",
        dozenas = parcel.createIntArray()?.toList() ?: emptyList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(group)
        parcel.writeString(name)
        parcel.writeIntArray(dozenas.toIntArray())
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<BichoAnimal> {
        override fun createFromParcel(parcel: Parcel): BichoAnimal = BichoAnimal(parcel)
        override fun newArray(size: Int): Array<BichoAnimal?> = arrayOfNulls(size)
    }
}

data class BichoItemResult(
    val position: String,
    val milhar: String,
    val grupo: Int,
    val animalName: String
) : Parcelable {
    val centena: String
        get() = if (milhar.length >= 3) milhar.takeLast(3) else milhar

    val dezena: String
        get() = if (milhar.length >= 2) milhar.takeLast(2) else milhar

    constructor(parcel: Parcel) : this(
        position = parcel.readString() ?: "",
        milhar = parcel.readString() ?: "",
        grupo = parcel.readInt(),
        animalName = parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(position)
        parcel.writeString(milhar)
        parcel.writeInt(grupo)
        parcel.writeString(animalName)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<BichoItemResult> {
        override fun createFromParcel(parcel: Parcel): BichoItemResult = BichoItemResult(parcel)
        override fun newArray(size: Int): Array<BichoItemResult?> = arrayOfNulls(size)
    }
}

data class BichoDrawResult(
    val drawName: String,
    val drawCode: String,
    val time: String,
    val date: String,
    val items: List<BichoItemResult>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        drawName = parcel.readString() ?: "",
        drawCode = parcel.readString() ?: "",
        time = parcel.readString() ?: "",
        date = parcel.readString() ?: "",
        items = parcel.createTypedArrayList(BichoItemResult.CREATOR) ?: emptyList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(drawName)
        parcel.writeString(drawCode)
        parcel.writeString(time)
        parcel.writeString(date)
        parcel.writeTypedList(items)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<BichoDrawResult> {
        override fun createFromParcel(parcel: Parcel): BichoDrawResult = BichoDrawResult(parcel)
        override fun newArray(size: Int): Array<BichoDrawResult?> = arrayOfNulls(size)
    }
}

data class BichoRecommendation(
    val animalGroup: Int,
    val animalName: String,
    val dozenas: List<Int>,
    val confidencePercentage: Int,
    val reasoning: String,
    val suggestedMilhar: String,
    val suggestedCentena: String,
    val puxadas: List<String>
) : Parcelable {
    val formattedGroup: String
        get() = String.format(Locale.US, "%02d", animalGroup)

    constructor(parcel: Parcel) : this(
        animalGroup = parcel.readInt(),
        animalName = parcel.readString() ?: "",
        dozenas = parcel.createIntArray()?.toList() ?: emptyList(),
        confidencePercentage = parcel.readInt(),
        reasoning = parcel.readString() ?: "",
        suggestedMilhar = parcel.readString() ?: "",
        suggestedCentena = parcel.readString() ?: "",
        puxadas = parcel.createStringArrayList() ?: emptyList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(animalGroup)
        parcel.writeString(animalName)
        parcel.writeIntArray(dozenas.toIntArray())
        parcel.writeInt(confidencePercentage)
        parcel.writeString(reasoning)
        parcel.writeString(suggestedMilhar)
        parcel.writeString(suggestedCentena)
        parcel.writeStringList(puxadas)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<BichoRecommendation> {
        override fun createFromParcel(parcel: Parcel): BichoRecommendation = BichoRecommendation(parcel)
        override fun newArray(size: Int): Array<BichoRecommendation?> = arrayOfNulls(size)
    }
}
