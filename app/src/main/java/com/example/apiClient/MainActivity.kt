package com.example.apiClient

import android.os.Bundle
import android.view.LayoutInflater
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
import com.example.apiClient.databinding.DialogAddEditPersonBinding
import com.example.apiClient.models.Person
import com.example.apiClient.network.ApiService
import com.example.apiClient.ui.PersonViewModel
import com.example.apiClient.ui.PersonViewModelFactory
import com.example.apiClient.ui.PersonListFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

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

        setupDrawer()

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.fragment_container, PersonListFragment())
            }
        }

        binding.btnAddPerson.setOnClickListener {
            showAddEditPersonDialog(null)
        }

        viewModel.fetchAllPersons()
    }

    private fun setupDrawer() {
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_add -> {
                    // Логика для добавления клиента
                }

                R.id.nav_view -> {
                    supportFragmentManager.commit {
                        replace(R.id.fragment_container, PersonListFragment())
                        addToBackStack(null)
                    }
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun showAddEditPersonDialog(person: Person?) {
        val dialogBinding: DialogAddEditPersonBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.dialog_add_edit_person,
            null,
            false
        )

        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setTitle(if (person == null) "Добавить человека" else "Редактировать человека")
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

        dialogBinding.btnSave.setOnClickListener {
            newPerson.verietyID = dialogBinding.etVerietyId.text.toString().toInt()
            newPerson.statusID = dialogBinding.etStatusId.text.toString().toInt()
            newPerson.inn = dialogBinding.etInn.text.toString()
            newPerson.type = dialogBinding.etType.text.toString()
            newPerson.shifer = dialogBinding.etShifer.text.toString()
            newPerson.data = dialogBinding.etData.text.toString()

            lifecycleScope.launch {
                try {
                    withContext(Dispatchers.IO) {
                        if (person == null) {
                            viewModel.addPerson(newPerson)
                            withContext(Dispatchers.Main) {
                                Toast.makeText(this@MainActivity, "Человек добавлен", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            viewModel.updatePerson(newPerson.id, newPerson)
                            withContext(Dispatchers.Main) {
                                Toast.makeText(this@MainActivity, "Человек обновлен", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } catch (e: Exception) {
                    // handle error
                }
            }
            dialog.dismiss()
        }

        dialog.show()
    }
}
