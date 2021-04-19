package com.example.primerappmvvmretrofitkotlin.repository

import com.example.primerappmvvmretrofitkotlin.data.model.UserList
import com.example.primerappmvvmretrofitkotlin.data.remote.UserDataSource

class UserRepositoryImpl(private val dataSource: UserDataSource) : UserRepository {

    override suspend fun getUsers(): UserList = dataSource.getUsers()
    override suspend fun searchUsers(name: String): UserList = dataSource.searchUsers(name)

}