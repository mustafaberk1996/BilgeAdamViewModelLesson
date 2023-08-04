package com.example.viewmodellesson2.data.state

import com.example.viewmodellesson2.data.model.Product
import java.lang.Exception

sealed class ProductListState{
    object Idle: ProductListState()
    object Loading: ProductListState()
    class Result(val products: List<Product>): ProductListState()
    class Error(exception: Exception): ProductListState()

}
