package com.example.apiClient.models

data class StatusPerson(
    val id: Int,
    val status: String
) {
    constructor(status: String) : this(0,"")
}
