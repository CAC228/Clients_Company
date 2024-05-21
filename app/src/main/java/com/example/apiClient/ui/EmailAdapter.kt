package com.example.apiClient.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apiClient.models.EmailPerson

class EmailAdapter(private val emails: MutableList<EmailPerson>) :
    RecyclerView.Adapter<EmailAdapter.EmailViewHolder>() {

    inner class EmailViewHolder(private val textView: TextView) : RecyclerView.ViewHolder(textView) {
        fun bind(email: EmailPerson) {
            textView.text = email.email
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val textView = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false) as TextView
        return EmailViewHolder(textView)
    }

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        holder.bind(emails[position])
    }

    override fun getItemCount(): Int = emails.size

    fun updateData(newEmails: List<EmailPerson>?) {
        emails.clear()
        if (newEmails != null) {
            emails.addAll(newEmails)
        }
        notifyDataSetChanged()
    }

    fun addEmail(email: EmailPerson) {
        emails.add(email)
        notifyItemInserted(emails.size - 1)
    }
}
