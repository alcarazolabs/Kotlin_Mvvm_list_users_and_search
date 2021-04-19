package com.example.primerappmvvmretrofitkotlin.repository

import com.example.primerappmvvmretrofitkotlin.data.model.UserList

interface UserRepository {
    suspend fun getUsers(): UserList
    suspend fun searchUsers(name:String): UserList
}