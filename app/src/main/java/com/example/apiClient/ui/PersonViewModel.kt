package com.example.apiClient.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiClient.data.PersonRepository
import com.example.apiClient.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PersonViewModel(private val repository: PersonRepository) : ViewModel() {

    private val _persons = MutableLiveData<List<Person>>()
    val persons: LiveData<List<Person>> get() = _persons

    private val _statusPersons = MutableLiveData<List<StatusPerson>>()
    val statusPersons: LiveData<List<StatusPerson>> get() = _statusPersons

    private val _verietyPersons = MutableLiveData<List<VerietyPerson>>()
    val verietyPersons: LiveData<List<VerietyPerson>> get() = _verietyPersons

    private val _personWithContacts = MutableLiveData<List<Person>>()
    val personWithContacts: LiveData<List<Person>> get() = _personWithContacts

    // Fetch all persons
    fun fetchAllPersons() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val personsList = repository.getAllPersons()
                withContext(Dispatchers.Main) {
                    _persons.value = personsList
                }
            } catch (e: Exception) {
                Log.e("PersonViewModel", "Error fetching persons", e)
            }
        }
    }

    // Fetch person by ID
    fun fetchPersonById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val person = repository.getPersonById(id)
                // Handle the fetched person
            } catch (e: Exception) {
                // handle error
            }
        }
    }

    // Add new person
    fun addPerson(person: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newPerson = repository.addPerson(person)
                withContext(Dispatchers.Main) {
                    val updatedList = _persons.value?.toMutableList() ?: mutableListOf()
                    updatedList.add(newPerson)
                    _persons.value = updatedList
                    Log.d("PersonViewModel", "Added person: $newPerson")
                }
            } catch (e: Exception) {
                Log.e("PersonViewModel", "Error adding person", e)
            }
        }
    }

    // Update existing person
    fun updatePerson(id: Int, person: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updatedPerson = repository.updatePerson(id, person)
                // Handle the updated person
            } catch (e: Exception) {
                // handle error
            }
        }
    }

    // Delete person by ID
    fun deletePerson(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.deletePerson(id)
                // Handle the deletion
            } catch (e: Exception) {
                // handle error
            }
        }
    }

    // Fetch all status persons
    fun fetchAllStatusPersons() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val statusesList = repository.getAllStatusPersons()
                withContext(Dispatchers.Main) {
                    _statusPersons.postValue(statusesList)
                }
            } catch (e: Exception) {
                // handle error
            }
        }
    }

    // Fetch status person by ID
    fun fetchStatusPersonById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val statusPerson = repository.getStatusPersonById(id)
                // Handle the fetched status person
            } catch (e: Exception) {
                // handle error
            }
        }
    }

    // Add new status person
    fun addStatusPerson(statusPerson: StatusPerson) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newStatusPerson = repository.addStatusPerson(statusPerson)
                // Handle the added status person
            } catch (e: Exception) {
                // handle error
            }
        }
    }

    // Update existing status person
    fun updateStatusPerson(id: Int, statusPerson: StatusPerson) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updatedStatusPerson = repository.updateStatusPerson(id, statusPerson)
                // Handle the updated status person
            } catch (e: Exception) {
                // handle error
            }
        }
    }

    // Delete status person by ID
    fun deleteStatusPerson(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.deleteStatusPerson(id)
                // Handle the deletion
            } catch (e: Exception) {
                // handle error
            }
        }
    }

    // Fetch all veriety persons
    fun fetchAllVerietyPersons() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val verietiesList = repository.getAllVerietyPersons()
                withContext(Dispatchers.Main) {
                    _verietyPersons.postValue(verietiesList)
                }
            } catch (e: Exception) {
                // handle error
            }
        }
    }

    // Fetch veriety person by ID
    fun fetchVerietyPersonById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val verietyPerson = repository.getVerietyPersonById(id)
                // Handle the fetched veriety person
            } catch (e: Exception) {
                // handle error
            }
        }
    }

    // Add new veriety person
    fun addVerietyPerson(verietyPerson: VerietyPerson) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newVerietyPerson = repository.addVerietyPerson(verietyPerson)
                // Handle the added veriety person
            } catch (e: Exception) {
                // handle error
            }
        }
    }

    // Update existing veriety person
    fun updateVerietyPerson(id: Int, verietyPerson: VerietyPerson) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updatedVerietyPerson = repository.updateVerietyPerson(id, verietyPerson)
                // Handle the updated veriety person
            } catch (e: Exception) {
                // handle error
            }
        }
    }

    // Delete veriety person by ID
    fun deleteVerietyPerson(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.deleteVerietyPerson(id)
                // Handle the deletion
            } catch (e: Exception) {
                // handle error
            }
        }
    }

    // Fetch persons with contacts
    fun fetchPersonsWithContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val personsList = repository.getPersonsWithContacts()
                withContext(Dispatchers.Main) {
                    _personWithContacts.postValue(personsList)
                }
            } catch (e: Exception) {
                // handle error
            }
        }
    }

    // Handler for saving person
    fun onSaveClicked(person: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (person.id == 0) {
                    addPerson(person)
                } else {
                    updatePerson(person.id, person)
                }
            } catch (e: Exception) {
                // handle error
            }
        }
    }
}
