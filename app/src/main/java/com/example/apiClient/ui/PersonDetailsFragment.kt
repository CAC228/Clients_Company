package com.example.apiClient.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apiClient.R
import com.example.apiClient.adapters.PhoneAdapter
import com.example.apiClient.databinding.FragmentPersonDetailsBinding
import com.example.apiClient.models.EmailPerson
import com.example.apiClient.models.Person
import com.example.apiClient.models.PhonePerson

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


        person = arguments?.getSerializable("person") as? Person ?: savedInstanceState?.getSerializable("person") as? Person

        if (person == null) {
            throw IllegalArgumentException("Person required")
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_person_details, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        if (person == null) {
            Toast.makeText(requireContext(), "Ошибка загрузки данных пользователя", Toast.LENGTH_SHORT).show()
        } else {
            bindPersonDetails(person!!)
        }

        setupRecyclerViews()
        setupButtons(binding)


        return binding.root

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("person", person)
    }

    private fun setupRecyclerViews() {
        phoneAdapter = PhoneAdapter(mutableListOf())
        binding.rvPhones.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = phoneAdapter
        }

        emailAdapter = EmailAdapter(mutableListOf())
        binding.rvEmails.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = emailAdapter
        }

        // Fetch person contacts when the view is created
        person?.let {
            viewModel.fetchPersonContacts(it.id)
            viewModel.personWithContacts.observe(viewLifecycleOwner) { personDetails ->
                personDetails?.let {
                    phoneAdapter.updatePhones(it.phones ?: emptyList())
                    emailAdapter.updateEmails(it.emails ?: emptyList())
                }
            }
        }
    }

    private fun setupButtons(binding: FragmentPersonDetailsBinding) {
        binding.btnAddPhone.setOnClickListener {
            val phone = binding.etPhone.text.toString()
            if (phone.isNotEmpty()) {
                person?.let {
                    val newPhone = PhonePerson(phone = phone, personID = it.id)
                    viewModel.addPhone(newPhone)
                    phoneAdapter.addPhone(newPhone)
                    binding.etPhone.text.clear()
                } ?: Toast.makeText(context, "Person is null", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Введите телефон", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnAddEmail.setOnClickListener {
            val email = binding.etEmail.text.toString()
            if (email.isNotEmpty()) {
                person?.let {
                    val newEmail = EmailPerson(email = email, personID = it.id)
                    viewModel.addEmail(newEmail)
                    emailAdapter.addEmail(newEmail)
                    binding.etEmail.text.clear()
                } ?: Toast.makeText(context, "Person is null", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Введите email", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSave.setOnClickListener {
            savePersonDetails()
        }
    }
    private fun bindPersonDetails(person: Person) {
        Log.d("PersonDetailsFragment", "Binding person details: $person")
        // Убедимся, что элементы EditText инициализированы и установим текст
        binding.etInn.post {
            binding.etInn.setText(person.inn)
        }
        binding.etType.post {
            binding.etType.setText(person.type)
        }
        binding.etShifer.post {
            binding.etShifer.setText(person.shifer)
        }
        binding.etData.post {
            binding.etData.setText(person.data)
        }


        // Инициализация и установка значений для Spinner
        viewModel.verietyPersons.observe(viewLifecycleOwner) { verieties ->
            val verietyNames = verieties.map { it.veriety }
            val verietyIdAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                verietyNames
            )
            verietyIdAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerVerietyId.adapter = verietyIdAdapter

            val verietyIndex = verieties.indexOfFirst { it.id == person.verietyID }
            if (verietyIndex >= 0) {
                binding.spinnerVerietyId.setSelection(verietyIndex)
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

            val statusIndex = statuses.indexOfFirst { it.id == person.statusID }
            if (statusIndex >= 0) {
                binding.spinnerStatusId.setSelection(statusIndex)
            }
        }
    }

    private fun savePersonDetails() {
        person?.let {
            it.inn = binding.etInn.text.toString()
            it.type = binding.etType.text.toString()
            it.shifer = binding.etShifer.text.toString()
            it.data = binding.etData.text.toString()
            // Save spinner selections here if needed

            // Call ViewModel to update person details on the server
            viewModel.updatePerson(it)

            // Call ViewModel to update person details on the server
            viewModel.updateResult.observe(viewLifecycleOwner) { result ->
                result?.let {
                    if (it) {
                        Toast.makeText(context, "Успешно сохранено", Toast.LENGTH_SHORT).show()
                        navigateBackToPersonList()
                        hideKeyboard()
                    } else {
                        Toast.makeText(context, "Ошибка при сохранении", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun navigateBackToPersonList() {
        // Обновляем список пользователей
        viewModel.fetchAllPersons()

        // Переход назад к PersonListFragment
        parentFragmentManager.popBackStack()
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }
}
