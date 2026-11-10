package com.animalgame.data.model

import android.os.Parcel
import android.os.Parcelable

data class ApostaModel(
    val id: String,
    val modalidade: String,
    val extracaoCode: String,
    val extracaoName: String,
    val numeroApostado: String,
    val nomeBicho: String,
    val colocacao: String,
    val valorApostado: Double,
    val status: String = "PENDENTE",
    val valorGanho: Double = 0.0,
    val porcentagemRetorno: Double = 0.0,
    val detalheResultado: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        id = parcel.readString() ?: "",
        modalidade = parcel.readString() ?: "",
        extracaoCode = parcel.readString() ?: "",
        extracaoName = parcel.readString() ?: "",
        numeroApostado = parcel.readString() ?: "",
        nomeBicho = parcel.readString() ?: "",
        colocacao = parcel.readString() ?: "",
        valorApostado = parcel.readDouble(),
        status = parcel.readString() ?: "PENDENTE",
        valorGanho = parcel.readDouble(),
        porcentagemRetorno = parcel.readDouble(),
        detalheResultado = parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(modalidade)
        parcel.writeString(extracaoCode)
        parcel.writeString(extracaoName)
        parcel.writeString(numeroApostado)
        parcel.writeString(nomeBicho)
        parcel.writeString(colocacao)
        parcel.writeDouble(valorApostado)
        parcel.writeString(status)
        parcel.writeDouble(valorGanho)
        parcel.writeDouble(porcentagemRetorno)
        parcel.writeString(detalheResultado)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<ApostaModel> {
        override fun createFromParcel(parcel: Parcel): ApostaModel = ApostaModel(parcel)
        override fun newArray(size: Int): Array<ApostaModel?> = arrayOfNulls(size)
    }
}
