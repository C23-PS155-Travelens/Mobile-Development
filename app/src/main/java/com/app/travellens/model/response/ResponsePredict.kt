package com.app.travellens.model.response


import com.google.gson.annotations.SerializedName

data class ResponsePredict(
    @SerializedName("predicted_label")
    val predictedLabel: String
)