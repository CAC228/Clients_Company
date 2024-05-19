package com.example.apiClient.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apiClient.databinding.ItemStatusBinding
import com.example.apiClient.models.StatusPerson

class StatusAdapter(private val onAddClick: () -> Unit) :
    RecyclerView.Adapter<StatusAdapter.StatusViewHolder>() {

    private var statuses: List<StatusPerson> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusViewHolder {
        val binding = ItemStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StatusViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StatusViewHolder, position: Int) {
        val status = statuses[position]
        holder.bind(status)

        holder.itemView.setOnClickListener {
            if (status.id == 0) {
                onAddClick()
            }
        }
    }

    override fun getItemCount(): Int = statuses.size

    fun submitList(statuses: List<StatusPerson>) {
        this.statuses = statuses
        notifyDataSetChanged()
    }

    class StatusViewHolder(private val binding: ItemStatusBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(status: StatusPerson) {
            binding.status = status
            binding.executePendingBindings()
        }
    }
}
