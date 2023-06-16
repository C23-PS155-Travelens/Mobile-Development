package com.app.travellens.model.response


import com.google.gson.annotations.SerializedName

data class ResponseGetWisataItem(
    @SerializedName("alamat")
    val alamat: String,
    @SerializedName("deskripsi")
    val deskripsi: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("lokasi")
    val lokasi: String,
    @SerializedName("nama")
    val nama: String
)