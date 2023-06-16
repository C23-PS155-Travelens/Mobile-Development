package com.app.travellens.model.response


import com.google.gson.annotations.SerializedName

data class ResponseUpdateProfileImage(
    @SerializedName("address")
    val address: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("username")
    val username: String
)