package com.example.apiClient.network

import com.example.apiClient.models.*
import retrofit2.http.*

interface ApiServiceInterface {

    @GET("/api/v1/persons")
    suspend fun getAllPersons(): List<Person>

    @GET("/persons/{id}")
    suspend fun getPersonById(@Path("id") id: Int): Person

    @POST("/persons")
    suspend fun addPerson(@Body person: Person): Person

    @PUT("/persons/{id}")
    suspend fun updatePerson(@Path("id") id: Int, @Body person: Person): Person

    @DELETE("/persons/{id}")
    suspend fun deletePerson(@Path("id") id: Int)

    @GET("/persons/with-contacts")
    suspend fun getPersonsWithContacts(): List<Person>

    @GET("/status-persons")
    suspend fun getAllStatusPersons(): List<StatusPerson>

    @GET("/status-persons/{id}")
    suspend fun getStatusPersonById(@Path("id") id: Int): StatusPerson

    @POST("/status-persons")
    suspend fun addStatusPerson(@Body statusPerson: StatusPerson): StatusPerson

    @PUT("/status-persons/{id}")
    suspend fun updateStatusPerson(@Path("id") id: Int, @Body statusPerson: StatusPerson): StatusPerson

    @DELETE("/status-persons/{id}")
    suspend fun deleteStatusPerson(@Path("id") id: Int)

    @GET("/veriety-persons")
    suspend fun getAllVerietyPersons(): List<VerietyPerson>

    @GET("/veriety-persons/{id}")
    suspend fun getVerietyPersonById(@Path("id") id: Int): VerietyPerson

    @POST("/veriety-persons")
    suspend fun addVerietyPerson(@Body verietyPerson: VerietyPerson): VerietyPerson

    @PUT("/veriety-persons/{id}")
    suspend fun updateVerietyPerson(@Path("id") id: Int, @Body verietyPerson: VerietyPerson): VerietyPerson

    @DELETE("/veriety-persons/{id}")
    suspend fun deleteVerietyPerson(@Path("id") id: Int)
}
