package com.example.viewmodellesson2.data.model

data class Product(val id:Int, val name:String, val price:Double, val imageUrl:String,var favorite:Boolean = false)