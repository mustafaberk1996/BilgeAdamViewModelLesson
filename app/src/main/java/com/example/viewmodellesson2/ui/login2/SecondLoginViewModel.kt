package com.example.viewmodellesson2.ui.login2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viewmodellesson2.Database
import com.example.viewmodellesson2.data.state.SecondLoginState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SecondLoginViewModel:ViewModel() {



    private val _loginState:MutableStateFlow<SecondLoginState> = MutableStateFlow(SecondLoginState.Idle)
    val loginState:StateFlow<SecondLoginState> = _loginState

    private val _message:MutableSharedFlow<String> = MutableSharedFlow()
    val message:SharedFlow<String> = _message



    fun login(email:String, password:String){

        viewModelScope.launch {
            if (!email.isNullOrEmpty() && !password.isNullOrEmpty()){
                //login islemi yap
                _loginState.value = SecondLoginState.Loading
                delay(2000)

                Database.users.firstOrNull { it.email == email && it.password == password }?.let {
                    //success
                    _loginState.value = SecondLoginState.Success
                }?: kotlin.run {
                    // error, user not found
                    _loginState.emit(SecondLoginState.Error)
                }


            }else{
                _message.emit("Bos alan birakmayinz.")
            }
        }



    }

}