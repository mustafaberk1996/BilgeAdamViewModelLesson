package com.example.viewmodellesson2.data.model

data class User(val id:Int, val name:String, val surname:String, val profileImageUrl:String,val email:String = "email", val password:String = "password")