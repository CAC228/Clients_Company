package com.example.apiClient.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apiClient.models.PhonePerson

class PhoneAdapter(private var phones: MutableList<PhonePerson>) : RecyclerView.Adapter<PhoneAdapter.PhoneViewHolder>() {

    inner class PhoneViewHolder(private val binding: ItemPhoneBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(phone: PhonePerson) {
            binding.phone = phone
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneViewHolder {
        val binding = ItemPhoneBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhoneViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhoneViewHolder, position: Int) {
        holder.bind(phones[position])
    }

    override fun getItemCount() = phones.size

    fun setPhones(newPhones: List<PhonePerson>) {
        phones.clear()
        phones.addAll(newPhones)
        notifyDataSetChanged()
    }
}
