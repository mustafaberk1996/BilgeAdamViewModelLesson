package com.example.viewmodellesson2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SecondViewModel:ViewModel() {



    //SHARED
    //STATE



    private val _counter:MutableStateFlow<Int> = MutableStateFlow(0)
    val counter:StateFlow<Int> = _counter

    private val _timer:MutableStateFlow<Int> = MutableStateFlow(0)
    val timer:StateFlow<Int> = _timer

    private val _state:MutableStateFlow<Int> = MutableStateFlow(0)
    val state:StateFlow<Int> = _state

    private val _message:MutableSharedFlow<String> = MutableSharedFlow()
    val message:SharedFlow<String> = _message


    init {
        viewModelScope.launch {
            while (true){
                _timer.emit(_timer.value+1)
                delay(1000)
            }
        }

    }

    fun increase() {
        viewModelScope.launch {
            _counter.emit(_counter.value+1)
        }

    }

    fun getData() {
        viewModelScope.launch {
            _state.emit(1)
        }
    }

    fun getMessage() {
        viewModelScope.launch {
            _message.emit("Kullanici Giris Hatali!")
        }
    }


}