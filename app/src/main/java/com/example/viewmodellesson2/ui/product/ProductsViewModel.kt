package com.example.viewmodellesson2.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viewmodellesson2.data.model.Product
import com.example.viewmodellesson2.data.model.User
import com.example.viewmodellesson2.data.state.ProductListState
import com.example.viewmodellesson2.data.state.AdapterState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.NullPointerException

class ProductsViewModel:ViewModel() {


    val productList = mutableListOf(
        Product(1,"Ampul",13.2,"https://img.vivense.com/480x320/images/1abdfa6009f6470696f4869d7bf8a3e0.jpg"),
        Product(1,"Kablo",43.2,"https://st1.myideasoft.com/shop/hr/44/myassets/products/322/nyaf-kablo.jpg?revision=1514979689"),
        Product(1,"Florasan",132.2,"https://productimages.hepsiburada.net/s/25/375/10124950437938.jpg"),
        Product(1,"Masa",43.2,"https://akn-evidea.a-cdn.akinoncdn.com/products/2021/01/25/54109/98822fbc-e467-4e0c-bc07-eb6045aa05bc_size1000x1000_cropTop.jpg"),
        Product(1,"Sehpa",43.2,"https://img.vivense.com/480x320/images/dfc69a77fbba482ca121f679c5221011.jpg"),
        Product(1,"Sandalye",43.2,"https://cilek.com/cdn/shop/products/21.08.8483.00_1.png?v=1618834429"),
        Product(1,"Tornavida",43.2,"https://st2.myideasoft.com/idea/cd/40/myassets/products/414/tornavida-fiyat.jpg?revision=1640171860"),
    )

    private val _productListState:MutableStateFlow<ProductListState> = MutableStateFlow(
        ProductListState.Idle
    )
    val productListState:StateFlow<ProductListState> = _productListState

    private val _adapterState:MutableStateFlow<AdapterState> = MutableStateFlow(AdapterState.Idle)
    val adapterState:StateFlow<AdapterState> = _adapterState

    private val _favState:MutableSharedFlow<Product> = MutableSharedFlow()
    val favState:SharedFlow<Product> = _favState

    private val _undoFavState:MutableSharedFlow<Product> = MutableSharedFlow()
    val undoFavState:SharedFlow<Product> = _undoFavState

    init {
        getProducts()
    }


    fun getProducts() {
        viewModelScope.launch {
            _productListState.value = ProductListState.Loading
            delay(3000)
            //_productListState.value = if ((0..1).random() == 0) ProductListState.Result(productList) else ProductListState.Error(NullPointerException())
            _productListState.value =  ProductListState.Result(productList)
        }
    }

    fun addOrRemoveFavorite(product: Product, index:Int) {
        viewModelScope.launch {
            _adapterState.value = AdapterState.Changed(index)
            _favState.emit(product)
        }
    }

    fun undoFavorite(product: Product) {
        viewModelScope.launch {
            _undoFavState.emit(product)
        }
    }

    fun removeItem(removedProduct: Product) {
        viewModelScope.launch {
            if (_productListState.value is ProductListState.Result){
                val index =  (_productListState.value as ProductListState.Result).products.indexOf(removedProduct)
                (_productListState.value as ProductListState.Result).products.removeAt(index)
                _adapterState.emit(AdapterState.Remove(index))
            }
        }
    }
}