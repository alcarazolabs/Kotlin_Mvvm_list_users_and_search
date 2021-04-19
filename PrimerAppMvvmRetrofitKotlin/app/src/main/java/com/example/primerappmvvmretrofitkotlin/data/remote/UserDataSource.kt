package com.example.primerappmvvmretrofitkotlin.data.remote

import com.example.primerappmvvmretrofitkotlin.data.model.UserList
import com.example.primerappmvvmretrofitkotlin.repository.WebService

class UserDataSource(private val webService: WebService) {

    suspend fun getUsers(): UserList = webService.getUsers()
    suspend fun searchUsers(name:String): UserList = webService.searchUsers(name)
}