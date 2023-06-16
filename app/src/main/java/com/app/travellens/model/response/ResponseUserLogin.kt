package com.app.travellens.model.response


import com.app.travellens.model.request.Data
import com.google.gson.annotations.SerializedName

data class ResponseUserLogin(
    @SerializedName("data")
    val data: Data,
    @SerializedName("status")
    val status: String
)