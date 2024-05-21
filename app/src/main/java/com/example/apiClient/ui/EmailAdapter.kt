package com.example.apiClient.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apiClient.models.EmailPerson

class EmailAdapter(private val emails: MutableList<EmailPerson>) : RecyclerView.Adapter<EmailAdapter.EmailViewHolder>() {

    inner class EmailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val emailTextView: TextView = itemView.findViewById(android.R.id.text1)

        fun bind(email: EmailPerson) {
            emailTextView.text = email.email
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val textView = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return EmailViewHolder(textView)
    }

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        holder.bind(emails[position])
    }

    override fun getItemCount() = emails.size

    fun updateEmails(newEmails: List<EmailPerson>) {
        emails.clear()
        emails.addAll(newEmails)
        notifyDataSetChanged()
    }

    fun addEmail(newEmail: EmailPerson) {
        emails.add(newEmail)
        notifyItemInserted(emails.size - 1)
    }
}
