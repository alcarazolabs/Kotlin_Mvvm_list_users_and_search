package com.example.primerappmvvmretrofitkotlin.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
        val  name:String,
        val  email:String,
        val  image:String,
        val  phone:String
        ): Parcelable

data class UserList(val results: List<User> = listOf())
