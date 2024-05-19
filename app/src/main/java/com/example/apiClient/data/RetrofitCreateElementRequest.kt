package com.example.apiClient.data

import android.provider.ContactsContract.RawContacts.Data
import com.google.gson.annotations.SerializedName
import java.util.Date

data class RetrofitCreateElementRequest (
    @SerializedName("data") val data: Date,
    @SerializedName("id") val id: Int,
    @SerializedName("inn") val inn: String,
    @SerializedName("shifer") val shifer: String,
    @SerializedName("statusID") val statusid: Int,
    @SerializedName("type") val type: String,
    @SerializedName("verietyID") val verietyid: Int,
)