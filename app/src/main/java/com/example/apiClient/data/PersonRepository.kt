package com.example.apiClient.data

import com.example.apiClient.models.*
import com.example.apiClient.network.ApiServiceInterface

class PersonRepository(private val apiService: ApiServiceInterface) {

    suspend fun getAllPersons(): List<Person> = apiService.getAllPersons()

    suspend fun getFilteredPersons(verietyId: Int?, statusId: Int?): List<Person> {
        return apiService.getFilteredPersons(verietyId, statusId)
    }


    suspend fun getPersonById(id: Int): Person {
        val response = apiService.getPersonById(id)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Person not found")
        } else {
            throw Exception("Error fetching person")
        }
    }

    suspend fun addPerson(person: Person): Person = apiService.addPerson(person)

    suspend fun updatePerson(person: Person): Boolean {
        val response = apiService.updatePerson(person.id, person)
        return response.isSuccessful
    }



    suspend fun deletePerson(id: Int) = apiService.deletePerson(id)

    suspend fun getPersonWithContacts(personId: Int): Person {
        return apiService.getPersonContacts(personId)
    }
    suspend fun getPersonContacts(id: Int): Person = apiService.getPersonContacts(id)


    suspend fun addPhone(phonePerson: PhonePerson) {
        apiService.addPhone(phonePerson.personID, phonePerson)
    }

    suspend fun addEmail(emailPerson: EmailPerson) {
        apiService.addEmail(emailPerson.personID, emailPerson)
    }

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
