package com.example.apiClient.data

import com.example.apiClient.models.*
import com.example.apiClient.network.ApiServiceInterface

class PersonRepository(private val apiService: ApiServiceInterface) {

    suspend fun getAllPersons(): List<Person> = apiService.getAllPersons()

    suspend fun getPersonById(id: Int): Person = apiService.getPersonById(id)

    suspend fun addPerson(person: Person): Person = apiService.addPerson(person)

    suspend fun updatePerson(id: Int, person: Person): Person = apiService.updatePerson(id, person)

    suspend fun deletePerson(id: Int) = apiService.deletePerson(id)

    suspend fun getPersonContacts(id: Int): Person = apiService.getPersonContacts(id)

    suspend fun getAllStatusPersons(): List<StatusPerson> = apiService.getAllStatusPersons()

    suspend fun getStatusPersonById(id: Int): StatusPerson = apiService.getStatusPersonById(id)

    suspend fun addStatusPerson(statusPerson: StatusPerson): StatusPerson = apiService.addStatusPerson(statusPerson)

    suspend fun updateStatusPerson(id: Int, statusPerson: StatusPerson): StatusPerson = apiService.updateStatusPerson(id, statusPerson)

    suspend fun deleteStatusPerson(id: Int) = apiService.deleteStatusPerson(id)

    suspend fun getAllVerietyPersons(): List<VerietyPerson> = apiService.getAllVerietyPersons()

    suspend fun getVerietyPersonById(id: Int): VerietyPerson = apiService.getVerietyPersonById(id)

    suspend fun addVerietyPerson(verietyPerson: VerietyPerson): VerietyPerson = apiService.addVerietyPerson(verietyPerson)

    suspend fun updateVerietyPerson(id: Int, verietyPerson: VerietyPerson): VerietyPerson = apiService.updateVerietyPerson(id, verietyPerson)

    suspend fun deleteVerietyPerson(id: Int) = apiService.deleteVerietyPerson(id)

    suspend fun getPersonsWithContacts(): List<Person> = apiService.getPersonsWithContacts()
}
