package com.example.apiClient.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apiClient.MainActivity
import com.example.apiClient.R
import com.example.apiClient.databinding.FragmentPersonListBinding
import com.example.apiClient.models.Person
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class PersonListFragment : Fragment() {

    private val viewModel: PersonViewModel by viewModels(ownerProducer = { requireActivity() })
    private lateinit var binding: FragmentPersonListBinding
    private lateinit var adapter: PersonAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("PersonListFragment", "onCreateView called")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_person_list, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupRecyclerView()

        binding.btnAddPerson.setOnClickListener {
            (activity as MainActivity).showAddEditPersonDialog(null)
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = PersonAdapter(
            onEditClick = { person -> (activity as MainActivity).showAddEditPersonDialog(person) },
            onLongClick = { person -> showContextMenu(person) }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        viewModel.persons.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.fetchAllPersons()
    }

    private fun showContextMenu(person: Person) {
        val options = arrayOf("Инфо", "Редактировать", "Удалить")
        MaterialAlertDialogBuilder(requireContext())
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> (activity as MainActivity).showPersonInfoDialog(person)
                    1 -> (activity as MainActivity).showAddEditPersonDialog(person)
                    2 -> deletePerson(person)
                }
            }
            .show()
    }

    private fun deletePerson(person: Person) {
        viewModel.deletePerson(person.id)
        Toast.makeText(context, "Человек удален", Toast.LENGTH_SHORT).show()
    }
}
