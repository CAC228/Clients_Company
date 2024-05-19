package com.example.apiClient

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.example.apiClient.data.PersonRepository
import com.example.apiClient.databinding.ActivityMainBinding
import com.example.apiClient.databinding.DialogAddPersonBinding
import com.example.apiClient.databinding.DialogPersonInfoBinding
import com.example.apiClient.models.Person
import com.example.apiClient.network.ApiService
import com.example.apiClient.ui.*
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val viewModel: PersonViewModel by viewModels {
        PersonViewModelFactory(PersonRepository(ApiService.apiService))
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setSupportActionBar(binding.toolbar)
        binding.drawerLayout.addDrawerListener(
            ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
            ).apply { syncState() }
        )

        binding.navView.setNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.fragment_container, PersonListFragment())
            }
        }

        binding.btnAddPerson.setOnClickListener {
            showAddEditPersonDialog(null)
        }

        viewModel.personDetails.observe(this, { person ->
            person?.let { showContactsDialog(it) }
        })

        viewModel.fetchAllPersons()
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        Log.d("MainActivity", "onNavigationItemSelected: ${menuItem.title}")
        when (menuItem.itemId) {
            R.id.nav_home -> {
                Log.d("MainActivity", "Loading PersonListFragment")
                supportFragmentManager.commit {
                    replace(R.id.fragment_container, PersonListFragment())
                    addToBackStack(null)
                }
            }
            R.id.nav_contacts -> {
                Log.d("MainActivity", "Loading ContactDetailsFragment")
                supportFragmentManager.commit {
                    replace(R.id.fragment_container, ContactDetailsFragment())
                    addToBackStack(null)
                }
            }
            R.id.nav_manage_veriety_status -> {
                Log.d("MainActivity", "Loading ManageVerietyStatusFragment")
                supportFragmentManager.commit {
                    replace(R.id.fragment_container, ManageVerietyStatusFragment())
                    addToBackStack(null)
                }
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun showPersonInfoDialog(person: Person) {
        val dialogBinding: DialogPersonInfoBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.dialog_person_info,
            null,
            false
        )

        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setTitle("Информация о пользователе")
            .setNegativeButton("Закрыть", null)
            .create()

        dialogBinding.person = person
        dialogBinding.viewModel = viewModel

        dialogBinding.btnShowContacts.setOnClickListener {
            viewModel.fetchPersonContacts(person.id)
        }

        dialog.show()
    }

    private fun showContactsDialog(person: Person) {
        val contactsMessage = buildString {
            append("Телефоны:\n")
            val phones = person.phones ?: emptyList()
            if (phones.isNotEmpty()) {
                phones.forEach { append("${it.phone}\n") }
            } else {
                append("Нет данных\n")
            }
            append("\nEmails:\n")
            val emails = person.emails ?: emptyList()
            if (emails.isNotEmpty()) {
                emails.forEach { append("${it.email}\n") }
            } else {
                append("Нет данных\n")
            }
        }

        AlertDialog.Builder(this)
            .setTitle("Контакты")
            .setMessage(contactsMessage)
            .setPositiveButton("Закрыть", null)
            .show()
    }

    fun showAddEditPersonDialog(person: Person?) {
        val dialogBinding: DialogAddPersonBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.dialog_add_person,
            null,
            false
        )

        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setTitle(if (person == null) "Добавить Человека" else "Редактировать Человека")
            .setNegativeButton("Отмена", null)
            .create()

        val newPerson = person ?: Person(
            id = 0,
            verietyID = 0,
            statusID = 0,
            inn = "",
            type = "",
            shifer = "",
            data = "",
            phones = emptyList(),
            emails = emptyList()
        )

        dialogBinding.person = newPerson
        dialogBinding.viewModel = viewModel

        lifecycleScope.launch {
            viewModel.fetchAllVerietyPersons()
            viewModel.fetchAllStatusPersons()

            viewModel.verietyPersons.observe(this@MainActivity) { verieties ->
                val verietyNames = verieties.map { it.veriety }
                val verietyIdAdapter = ArrayAdapter(
                    this@MainActivity,
                    android.R.layout.simple_spinner_item,
                    verietyNames
                )
                verietyIdAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                dialogBinding.spinnerVerietyId.adapter = verietyIdAdapter

                if (person != null) {
                    val verietyIndex = verieties.indexOfFirst { it.id == person.verietyID }
                    if (verietyIndex >= 0) {
                        dialogBinding.spinnerVerietyId.setSelection(verietyIndex)
                    }
                }
            }

            viewModel.statusPersons.observe(this@MainActivity) { statuses ->
                val statusNames = statuses.map { it.status }
                val statusIdAdapter = ArrayAdapter(
                    this@MainActivity,
                    android.R.layout.simple_spinner_item,
                    statusNames
                )
                statusIdAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                dialogBinding.spinnerStatusId.adapter = statusIdAdapter

                if (person != null) {
                    val statusIndex = statuses.indexOfFirst { it.id == person.statusID }
                    if (statusIndex >= 0) {
                        dialogBinding.spinnerStatusId.setSelection(statusIndex)
                    }
                }
            }

            dialogBinding.btnSave.setOnClickListener {
                val selectedVeriety = dialogBinding.spinnerVerietyId.selectedItem as String
                val selectedStatus = dialogBinding.spinnerStatusId.selectedItem as String

                newPerson.verietyID = viewModel.verietyPersons.value?.first { it.veriety == selectedVeriety }?.id ?: 0
                newPerson.statusID = viewModel.statusPersons.value?.first { it.status == selectedStatus }?.id ?: 0
                newPerson.inn = dialogBinding.etInn.text.toString()
                newPerson.type = dialogBinding.etType.text.toString()
                newPerson.shifer = dialogBinding.etShifer.text.toString()
                newPerson.data = dialogBinding.etData.text.toString()

                if (person == null) {
                    viewModel.addPerson(newPerson)
                } else {
                    viewModel.updatePerson(person.id, newPerson)
                }
                dialog.dismiss()
            }

            dialog.show()
        }
    }
}
