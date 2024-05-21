package com.example.apiClient.adapters

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apiClient.models.PhonePerson

import android.view.LayoutInflater
import android.view.View

class PhoneAdapter(private val phones: MutableList<PhonePerson>) : RecyclerView.Adapter<PhoneAdapter.PhoneViewHolder>() {

    inner class PhoneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val phoneTextView: TextView = itemView.findViewById(android.R.id.text1)

        fun bind(phone: PhonePerson) {
            phoneTextView.text = phone.phone
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneViewHolder {
        val textView = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return PhoneViewHolder(textView)
    }

    override fun onBindViewHolder(holder: PhoneViewHolder, position: Int) {
        holder.bind(phones[position])
    }

    override fun getItemCount() = phones.size

    fun updatePhones(newPhones: List<PhonePerson>) {
        phones.clear()
        phones.addAll(newPhones)
        notifyDataSetChanged()
    }

    fun addPhone(newPhone: PhonePerson) {
        phones.add(newPhone)
        notifyItemInserted(phones.size - 1)
    }
}