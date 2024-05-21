package com.example.apiClient.network

import com.example.apiClient.models.*
import retrofit2.Response
import retrofit2.http.*

interface ApiServiceInterface {

    @GET("/api/v1/persons")
    suspend fun getAllPersons(): List<Person>

    @GET("/api/v1/persons/filtered")
    suspend fun getFilteredPersons(
        @Query("veriety_id") verietyId: Int?,
        @Query("status_id") statusId: Int?
    ): List<Person>

    @POST("persons/{id}/phones")
    suspend fun addPhone(@Path("id") personId: Int, @Body phonePerson: PhonePerson)

    @POST("persons/{id}/emails")
    suspend fun addEmail(@Path("id") personId: Int, @Body emailPerson: EmailPerson)

    @GET("/api/v1/persons/{id}")
    suspend fun getPersonById(@Path("id") id: Int): Person

    @POST("/api/v1/persons")
    suspend fun addPerson(@Body person: Person): Person

    @PUT("/api/v1/persons/{id}")
    suspend fun updatePerson(@Path("id") id: Int, @Body person: Person): Person

    @DELETE("/api/v1/persons/{id}")
    suspend fun deletePerson(@Path("id") id: Int)

    @GET("/api/v1/persons/{id}/contacts")
    suspend fun getPersonContacts(@Path("id") id: Int): Person

    @GET("/api/v1/persons/with-contacts")
    suspend fun getPersonsWithContacts(): List<Person>

    @GET("/api/v1/status-persons")
    suspend fun getAllStatusPersons(): List<StatusPerson>

    @GET("/api/v1/status-persons/{id}")
    suspend fun getStatusPersonById(@Path("id") id: Int): StatusPerson

    @POST("/api/v1/status-persons")
    suspend fun addStatusPerson(@Body statusPerson: StatusPerson): StatusPerson

    @PUT("/api/v1/status-persons/{id}")
    suspend fun updateStatusPerson(@Path("id") id: Int, @Body statusPerson: StatusPerson): StatusPerson

    @DELETE("/api/v1/status-persons/{id}")
    suspend fun deleteStatusPerson(@Path("id") id: Int)

    @GET("/api/v1/veriety-persons")
    suspend fun getAllVerietyPersons(): List<VerietyPerson>

    @GET("/api/v1/veriety-persons/{id}")
    suspend fun getVerietyPersonById(@Path("id") id: Int): VerietyPerson

    @POST("/api/v1/veriety-persons")
    suspend fun addVerietyPerson(@Body verietyPerson: VerietyPerson): VerietyPerson

    @PUT("/api/v1/veriety-persons/{id}")
    suspend fun updateVerietyPerson(@Path("id") id: Int, @Body verietyPerson: VerietyPerson): VerietyPerson

    @DELETE("/api/v1/veriety-persons/{id}")
    suspend fun deleteVerietyPerson(@Path("id") id: Int)
}
