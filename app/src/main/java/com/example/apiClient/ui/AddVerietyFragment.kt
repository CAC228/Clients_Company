package com.example.apiClient.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.apiClient.R
import com.example.apiClient.databinding.DialogAddVerietyBinding

class AddVerietyFragment : Fragment() {

    private val viewModel: PersonViewModel by viewModels(ownerProducer = { requireActivity() })
    private lateinit var binding: DialogAddVerietyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogAddVerietyBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }
}
