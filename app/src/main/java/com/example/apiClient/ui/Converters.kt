package com.example.apiClient.ui

import androidx.databinding.InverseMethod

object Converters {

    @JvmStatic
    fun fromString(value: String?): Int {
        return value?.toIntOrNull() ?: 0
    }

    @JvmStatic
    fun toString(value: Int?): String {
        return value?.toString() ?: ""
    }
}
