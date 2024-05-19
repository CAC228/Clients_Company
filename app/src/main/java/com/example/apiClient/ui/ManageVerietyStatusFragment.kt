package com.example.apiClient.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apiClient.R
import com.example.apiClient.databinding.FragmentManageVerietyStatusBinding
import com.example.apiClient.models.StatusPerson
import com.example.apiClient.models.VerietyPerson

class ManageVerietyStatusFragment : Fragment() {

    private val viewModel: PersonViewModel by viewModels(ownerProducer = { requireActivity() })
    private lateinit var binding: FragmentManageVerietyStatusBinding
    private lateinit var verietyAdapter: VerietyAdapter
    private lateinit var statusAdapter: StatusAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("ManageVerietyStatusFragment", "onCreateView called")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_manage_veriety_status, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupRecyclerViews()

        viewModel.fetchAllVerietyPersons()
        viewModel.fetchAllStatusPersons()

        return binding.root
    }

    private fun setupRecyclerViews() {
        verietyAdapter = VerietyAdapter { showAddVerietyDialog() }
        binding.recyclerViewVeriety.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewVeriety.adapter = verietyAdapter

        viewModel.verietyPersons.observe(viewLifecycleOwner) {
            val verietiesWithAdd = it.toMutableList().apply { add(VerietyPerson(0, "Добавить новый")) }
            verietyAdapter.submitList(verietiesWithAdd)
        }

        statusAdapter = StatusAdapter { showAddStatusDialog() }
        binding.recyclerViewStatus.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewStatus.adapter = statusAdapter

        viewModel.statusPersons.observe(viewLifecycleOwner) {
            val statusesWithAdd = it.toMutableList().apply { add(StatusPerson(0, "Добавить новый")) }
            statusAdapter.submitList(statusesWithAdd)
        }
    }

    private fun showAddVerietyDialog() {
        // Logic to show dialog for adding new veriety
    }

    private fun showAddStatusDialog() {
        // Logic to show dialog for adding new status
    }
}
