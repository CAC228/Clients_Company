package com.example.apiClient.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apiClient.MainActivity
import com.example.apiClient.R
import com.example.apiClient.databinding.FragmentPersonListBinding
import com.example.apiClient.models.Person
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PersonListFragment : Fragment() {

    private val viewModel: PersonViewModel by viewModels(ownerProducer = { requireActivity() })
    private lateinit var binding: FragmentPersonListBinding
    private lateinit var adapter: PersonAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_person_list, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupRecyclerView()
        setupSpinners()
        setupAddPersonButton()
        setupObservers()
        setupToolbar()
        setupFilterButton()

        viewModel.persons.observe(viewLifecycleOwner, Observer { persons ->
            adapter.submitList(persons)
        })

        return binding.root
    }

    // PersonListFragment.kt
    private fun setupSpinners() {
        lifecycleScope.launch {
            viewModel.fetchAllVerietyPersons()
            viewModel.fetchAllStatusPersons()

            viewModel.verietyPersons.observe(viewLifecycleOwner) { verieties ->
                val verietyNames = verieties.map { it.veriety }
                val verietyIdAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    verietyNames
                )
                verietyIdAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerFilterVeriety.adapter = verietyIdAdapter
            }

            viewModel.statusPersons.observe(viewLifecycleOwner) { statuses ->
                val statusNames = statuses.map { it.status }
                val statusIdAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    statusNames
                )
                statusIdAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerFilterStatus.adapter = statusIdAdapter
            }
        }
    }

    private fun setupFilterButton() {
        binding.btnApplyFilters.setOnClickListener {
            if (viewModel.isFiltering.value == true) {
                resetFilters()
            } else {
                applyFilters()
            }
        }
    }

    private fun applyFilters() {
        val selectedVerietyId = viewModel.verietyPersons.value?.firstOrNull {
            it.veriety == binding.spinnerFilterVeriety.selectedItem
        }?.id ?: 0
        val selectedStatusId = viewModel.statusPersons.value?.firstOrNull {
            it.status == binding.spinnerFilterStatus.selectedItem
        }?.id ?: 0

        viewModel.filterPersons(selectedVerietyId, selectedStatusId)
    }

    private fun resetFilters() {
        viewModel.resetFiltering()
    }

    private fun setupObservers() {
        viewModel.filteredPersons.observe(viewLifecycleOwner) { persons ->
            persons?.let {
                adapter.submitList(it)
            } ?: run {
                Toast.makeText(requireContext(), "Ошибка загрузки данных", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun setupAddPersonButton() {
        binding.btnAddPerson.setOnClickListener {
            (activity as MainActivity).showAddEditPersonDialog(null)
        }
    }
    private fun showEditPersonDialog(person: Person) {
        (activity as MainActivity).showAddEditPersonDialog(person)
    }

    private fun showInfoDialog(person: Person) {
        (activity as MainActivity).showPersonInfoDialog(person)
    }

    private fun showContextMenu(person: Person) {
        val options = arrayOf("Инфо", "Редактировать", "Удалить")
        MaterialAlertDialogBuilder(requireContext())
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> showInfoDialog(person)
                    1 -> showEditPersonDialog(person)
                    2 -> deletePerson(person)
                }
            }
            .show()
    }

    private fun setupRecyclerView() {
        adapter = PersonAdapter(
            onEditClick = { person -> (activity as MainActivity).showPersonDetailsFragment(person) },
            onLongClick = { person -> showContextMenu(person) }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
    }

    private fun deletePerson(person: Person) {
        viewModel.deletePerson(person.id)
        Toast.makeText(context, "Человек удален", Toast.LENGTH_SHORT).show()
    }
    private fun setupToolbar() {
        (activity as MainActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }
    }
}
