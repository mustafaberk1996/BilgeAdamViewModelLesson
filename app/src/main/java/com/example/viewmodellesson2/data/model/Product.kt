package com.example.viewmodellesson2.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(val id:Int, val name:String, val price:Double, val imageUrl:String,var favorite:Boolean = false):Parcelable