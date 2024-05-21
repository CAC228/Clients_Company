package com.example.apiClient.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apiClient.R
import com.example.apiClient.databinding.FragmentPersonDetailsBinding
import com.example.apiClient.models.EmailPerson
import com.example.apiClient.models.Person
import com.example.apiClient.models.PhonePerson
import kotlinx.coroutines.launch

class PersonDetailsFragment : Fragment() {

    private val viewModel: PersonViewModel by viewModels(ownerProducer = { requireActivity() })
    private lateinit var binding: FragmentPersonDetailsBinding
    private lateinit var phoneAdapter: PhoneAdapter
    private lateinit var emailAdapter: EmailAdapter
    private var person: Person? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_person_details, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.currentPerson.observe(viewLifecycleOwner, { person ->
            this.person = person
            binding.person = person
            setupSpinners(person)
        })

        setupButtons()
        setupRecyclerViews()

        return binding.root
    }

    private fun setupRecyclerViews() {
        phoneAdapter = PhoneAdapter(mutableListOf())
        binding.rvPhones.layoutManager = LinearLayoutManager(context)
        binding.rvPhones.adapter = phoneAdapter

        emailAdapter = EmailAdapter(mutableListOf())
        binding.rvEmails.layoutManager = LinearLayoutManager(context)
        binding.rvEmails.adapter = emailAdapter

        person?.let {
            phoneAdapter.updateData(it.phones)
            emailAdapter.updateData(it.emails)
        }
    }

    private fun setupSpinners(person: Person?) {
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
                binding.spinnerVerietyId.adapter = verietyIdAdapter

                person?.let {
                    val verietyIndex = verieties.indexOfFirst { it.id == person.verietyID }
                    if (verietyIndex >= 0) {
                        binding.spinnerVerietyId.setSelection(verietyIndex)
                    }
                }
            }

            viewModel.statusPersons.observe(viewLifecycleOwner) { statuses ->
                val statusNames = statuses.map { it.status }
                val statusIdAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    statusNames
                )
                statusIdAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerStatusId.adapter = statusIdAdapter

                person?.let {
                    val statusIndex = statuses.indexOfFirst { it.id == person.statusID }
                    if (statusIndex >= 0) {
                        binding.spinnerStatusId.setSelection(statusIndex)
                    }
                }
            }
        }
    }

    private fun savePersonDetails() {
        person?.apply {
            verietyID = viewModel.verietyPersons.value?.first { it.veriety == binding.spinnerVerietyId.selectedItem as String }?.id ?: 0
            statusID = viewModel.statusPersons.value?.first { it.status == binding.spinnerStatusId.selectedItem as String }?.id ?: 0
            inn = binding.etInn.text.toString()
            type = binding.etType.text.toString()
            shifer = binding.etShifer.text.toString()
            data = binding.etData.text.toString()

            viewModel.updatePerson(id, this)
        }
    }

    private fun setupButtons() {
        binding.btnAddPhone.setOnClickListener {
            val phone = binding.etPhone.text.toString()
            if (phone.isNotEmpty()) {
                val newPhone = PhonePerson(phone = phone)
                phoneAdapter.addPhone(newPhone)
                binding.etPhone.text.clear()
            } else {
                Toast.makeText(context, "Введите телефон", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnAddEmail.setOnClickListener {
            val email = binding.etEmail.text.toString()
            if (email.isNotEmpty()) {
                val newEmail = EmailPerson(email = email)
                emailAdapter.addEmail(newEmail)
                binding.etEmail.text.clear()
            } else {
                Toast.makeText(context, "Введите email", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSave.setOnClickListener {
            savePersonDetails()
        }
    }

}
