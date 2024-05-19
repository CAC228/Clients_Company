package com.example.apiClient.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.apiClient.R
import com.example.apiClient.databinding.DialogAddStatusBinding

class AddStatusFragment : Fragment() {

    private val viewModel: PersonViewModel by viewModels(ownerProducer = { requireActivity() })
    private lateinit var binding: DialogAddStatusBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogAddStatusBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }
}
