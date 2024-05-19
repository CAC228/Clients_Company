package com.example.apiClient.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apiClient.databinding.ItemVerietyBinding
import com.example.apiClient.models.VerietyPerson

class VerietyAdapter(private val onAddClick: () -> Unit) :
    RecyclerView.Adapter<VerietyAdapter.VerietyViewHolder>() {

    private var verieties: List<VerietyPerson> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerietyViewHolder {
        val binding = ItemVerietyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VerietyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VerietyViewHolder, position: Int) {
        val veriety = verieties[position]
        holder.bind(veriety)

        holder.itemView.setOnClickListener {
            if (veriety.id == 0) {
                onAddClick()
            }
        }
    }

    override fun getItemCount(): Int = verieties.size

    fun submitList(verieties: List<VerietyPerson>) {
        this.verieties = verieties
        notifyDataSetChanged()
    }

    class VerietyViewHolder(private val binding: ItemVerietyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(veriety: VerietyPerson) {
            binding.veriety = veriety
            binding.executePendingBindings()
        }
    }
}
