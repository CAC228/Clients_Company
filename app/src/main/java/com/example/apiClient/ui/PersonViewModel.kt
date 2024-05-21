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

    private val _personDetails = MutableLiveData<Person>()
    val personDetails: LiveData<Person> get() = _personDetails

    private val _statusPersons = MutableLiveData<List<StatusPerson>>()
    val statusPersons: LiveData<List<StatusPerson>> get() = _statusPersons

    private val _verietyPersons = MutableLiveData<List<VerietyPerson>>()
    val verietyPersons: LiveData<List<VerietyPerson>> get() = _verietyPersons

    private val _personWithContacts = MutableLiveData<List<Person>>()
    val personWithContacts = MutableLiveData<Person>()

    private val _currentPerson = MutableLiveData<Person?>()
    val currentPerson: LiveData<Person?> get() = _currentPerson

    private val _filteredPersons = MutableLiveData<List<Person>>()
    val filteredPersons: LiveData<List<Person>> get() = _filteredPersons

    private val _isFiltering = MutableLiveData<Boolean>(false)
    val isFiltering: LiveData<Boolean> get() = _isFiltering

    private val _updateResult = MutableLiveData<Boolean>()
    val updateResult: LiveData<Boolean> get() = _updateResult

    private val _updatedPerson = MutableLiveData<Person>()
    val updatedPerson: LiveData<Person> get() = _updatedPerson

    fun setCurrentPerson(person: Person?) {
        _currentPerson.value = person
    }

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

    // Fetch filtered persons
    fun filterPersons(verietyId: Int, statusId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val filteredList = repository.getFilteredPersons(verietyId, statusId)
                withContext(Dispatchers.Main) {
                    _filteredPersons.value = filteredList
                    _isFiltering.value = true
                }
            } catch (e: Exception) {
                // handle error
            }
        }
    }

    // Reset filtering
    fun resetFiltering() {
        _filteredPersons.value = emptyList()
        _isFiltering.value = false
        fetchAllPersons()
    }

    fun getPersonById(id: Int) {
        viewModelScope.launch {
            val person = repository.getPersonById(id)
            _updatedPerson.postValue(person)
        }
    }

    fun fetchPersonById(id: Int): LiveData<Person?> {
        val personData = MutableLiveData<Person?>()
        viewModelScope.launch {
            try {
                val person = repository.getPersonById(id)
                personData.postValue(person)
            } catch (e: Exception) {
                personData.postValue(null)
            }
        }
        return personData
    }

    // Add new person
    fun addPerson(person: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.addPerson(person)
                withContext(Dispatchers.Main) {
                    val currentList = _persons.value ?: emptyList()
                    _persons.value = currentList + person
                    Log.d("PersonViewModel", "Added person: $person")
                }
            } catch (e: Exception) {
                Log.e("PersonViewModel", "Error adding person", e)
            }
        }
    }

    // Функция для обновления данных пользователя
    fun updatePerson(person: Person) {
        viewModelScope.launch {
            val result = repository.updatePerson(person)
            _updateResult.postValue(result)
        }
    }

    // Delete person by ID
    fun deletePerson(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.deletePerson(id)
                fetchAllPersons()
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

    // Fetch contacts of a specific person by ID
    fun fetchPersonContacts(personId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val personDetails = repository.getPersonContacts(personId)
                personWithContacts.postValue(personDetails)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun addPhone(phonePerson: PhonePerson) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.addPhone(phonePerson)
                fetchPersonContacts(phonePerson.personID)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun addEmail(emailPerson: EmailPerson) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.addEmail(emailPerson)
                fetchPersonContacts(emailPerson.personID)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }


}
