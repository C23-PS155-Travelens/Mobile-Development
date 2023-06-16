package com.app.travellens.model.response


import com.google.gson.annotations.SerializedName

data class ResponseRegister(
    @SerializedName("message")
    val message: String
)