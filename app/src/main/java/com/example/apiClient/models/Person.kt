package com.example.apiClient.models

import android.os.Parcelable
import java.io.Serializable


data class Person(
    val id: Int,
    var verietyID: Int,
    var statusID: Int,
    var inn: String,
    var type: String,
    var shifer: String,
    var data: String,
    val phones: List<PhonePerson> = emptyList(),
    val emails: List<EmailPerson> = emptyList()
): Serializable
