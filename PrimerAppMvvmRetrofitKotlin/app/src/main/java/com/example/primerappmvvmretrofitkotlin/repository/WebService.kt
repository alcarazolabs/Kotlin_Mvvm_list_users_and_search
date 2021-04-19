package com.example.primerappmvvmretrofitkotlin.repository

import com.example.primerappmvvmretrofitkotlin.application.AppConstants
import com.example.primerappmvvmretrofitkotlin.data.model.UserList
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface WebService {
    //endpoint resources
    @GET("users")
    suspend fun getUsers(): UserList

    @FormUrlEncoded
    @POST("search")
    suspend fun searchUsers(@Field("name") name: String): UserList
    /*
    //Ejemplo de una ruta con php
    //@GET("search.php?s=")
    @GET("search.php")
    suspend fun getUserByName(@Query(value="s") name:String): UserList
    */
}
object RetrofitClient {

    val webservice by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }

}