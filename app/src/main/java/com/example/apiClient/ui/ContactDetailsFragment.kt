package com.example.apiClient.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.apiClient.R
import com.example.apiClient.databinding.FragmentContactDetailsBinding

class ContactDetailsFragment : Fragment() {

    private val viewModel: PersonViewModel by viewModels(ownerProducer = { requireActivity() })
    private lateinit var binding: FragmentContactDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("ContactsListFragment", "onCreateView called")
        binding = FragmentContactDetailsBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.fetchPersonsWithContacts()

        return binding.root
    }
}
