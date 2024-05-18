package com.example.apiClient.models

data class Person(
    val id: Int,
    var verietyID: Int,
    var statusID: Int,
    var inn: String,
    var type: String,
    var shifer: String,
    var data: String,
    val phones: List<PhonePerson>,
    val emails: List<EmailPerson>
)
