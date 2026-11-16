package com.animalgame.data.model

import android.os.Parcel
import android.os.Parcelable

data class ApostaModel(
    val id: String,
    val modalidade: String,
    val extracaoCode: String,
    val extracaoName: String,
    val gruposSelecionados: List<Int> = emptyList(),
    val dezenasSelecionadas: List<String> = emptyList(),
    val numeroApostado: String = "",
    val combinacoesInvertidas: List<String> = emptyList(),
    val colocacao: String = "1º ao 5º Prêmio",
    val valorApostado: Double = 10.0,
    val nomeBicho: String = "",
    val status: String = "PENDENTE",
    val valorGanho: Double = 0.0,
    val porcentagemRetorno: Double = 0.0,
    val detalheResultado: String = ""
) : Parcelable {

    val descricaoAposta: String
        get() = when (modalidade) {
            "Dupla de Grupo" -> "Grupos: ${gruposSelecionados.joinToString(", ")}"
            "Terno de Grupo" -> "Grupos: ${gruposSelecionados.joinToString(", ")}"
            "Duque de Dezenas" -> "Dezenas: ${dezenasSelecionadas.joinToString(", ")}"
            "Terno de Dezenas" -> "Dezenas: ${dezenasSelecionadas.joinToString(", ")}"
            "Invertido" -> "Nº $numeroApostado (${combinacoesInvertidas.size} combinações)"
            else -> "Nº $numeroApostado ${if (nomeBicho.isNotBlank()) "($nomeBicho)" else ""}"
        }

    constructor(parcel: Parcel) : this(
        id = parcel.readString() ?: "",
        modalidade = parcel.readString() ?: "",
        extracaoCode = parcel.readString() ?: "",
        extracaoName = parcel.readString() ?: "",
        gruposSelecionados = parcel.createIntArray()?.toList() ?: emptyList(),
        dezenasSelecionadas = parcel.createStringArrayList() ?: emptyList(),
        numeroApostado = parcel.readString() ?: "",
        combinacoesInvertidas = parcel.createStringArrayList() ?: emptyList(),
        colocacao = parcel.readString() ?: "",
        valorApostado = parcel.readDouble(),
        nomeBicho = parcel.readString() ?: "",
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
        parcel.writeIntArray(gruposSelecionados.toIntArray())
        parcel.writeStringList(dezenasSelecionadas)
        parcel.writeString(numeroApostado)
        parcel.writeStringList(combinacoesInvertidas)
        parcel.writeString(colocacao)
        parcel.writeDouble(valorApostado)
        parcel.writeString(nomeBicho)
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
