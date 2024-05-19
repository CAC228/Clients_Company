package com.example.apiClient.models

data class VerietyPerson(
    val id: Int,
    val veriety: String
) {
    constructor(veriety: String) : this(0,"")
}
