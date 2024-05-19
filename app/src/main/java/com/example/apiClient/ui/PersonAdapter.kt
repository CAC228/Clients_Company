package com.example.apiClient.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apiClient.databinding.ItemPersonBinding
import com.example.apiClient.models.Person

class PersonAdapter(
    private val onEditClick: (Person) -> Unit,
    private val onLongClick: (Person) -> Unit
) : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    private var persons: List<Person> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val binding = ItemPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(persons[position], onEditClick, onLongClick)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(persons: List<Person>) {
        this.persons = persons
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = persons.size

    @SuppressLint("NotifyDataSetChanged")
    fun setPersons(persons: List<Person>) {
        this.persons = persons
        notifyDataSetChanged()
    }

    fun getPersonAt(position: Int): Person = persons[position]

    class PersonViewHolder(private val binding: ItemPersonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(person: Person, onEditClick: (Person) -> Unit, onLongClick: (Person) -> Unit) {
            binding.person = person
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                onEditClick(person)
            }
            binding.root.setOnLongClickListener {
                onLongClick(person)
                true
            }
        }
    }


}
