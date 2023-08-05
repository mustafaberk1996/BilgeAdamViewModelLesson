package com.example.viewmodellesson2.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val name: String,
    val surname: String,
    val profileImageUrl: String,
    val email: String = "email",
    val password: String = "password"
) : Parcelable