package com.example.viewmodellesson2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel:ViewModel() {


    private val fruits = listOf("Elma","Armut","Kiraz","Muz","Greyfurt","Kavun","Talisca",)


    private var _counterLiveData:MutableLiveData<Int> = MutableLiveData(0)
    val counterLiveData:LiveData<Int> = _counterLiveData

    private val _fruitListLiveData:MutableLiveData<List<String>> = MutableLiveData(fruits)
    val fruitListLiveData:LiveData<List<String>> = _fruitListLiveData


    var count = 0


    fun counterButtonClicked(){
        _counterLiveData.value?.let {
            _counterLiveData.postValue(it+1)
        }
        //count++
    }

    fun addFruit(fruitText: String) {
        _fruitListLiveData.value?.let {
            val list = it.toMutableList()
            list.add(fruitText)
            _fruitListLiveData.postValue(list)
        }

    }


}