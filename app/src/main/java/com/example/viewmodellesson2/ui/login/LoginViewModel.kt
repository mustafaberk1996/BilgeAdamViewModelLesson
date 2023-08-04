package com.example.viewmodellesson2.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viewmodellesson2.data.state.LoginState
import com.example.viewmodellesson2.data.state.ReactionState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel:ViewModel() {


    private val _reactionState:MutableStateFlow<ReactionState> = MutableStateFlow(ReactionState.Idle)
    val reactionState:StateFlow<ReactionState> = _reactionState

    private val _emailText:MutableStateFlow<String> = MutableStateFlow("")
    private val _passwordText:MutableStateFlow<String> = MutableStateFlow("")

    private val _message:MutableSharedFlow<String> = MutableSharedFlow()
    val message:SharedFlow<String> = _message

    private val _loginState:MutableSharedFlow<LoginState> = MutableSharedFlow()
    val loginState:SharedFlow<LoginState> = _loginState



    fun loginButtonClicked(emailText: String, passwordText: String) {
        viewModelScope.launch {

            if (!emailText.isNullOrEmpty() && !passwordText.isNullOrEmpty()) {

                _loginState.emit( if ((0..1).random()==0) LoginState.Success else LoginState.Error)


            } else {
                _message.emit("Lutfen bos alan birakmayiniz!")
            }
        }
    }

    fun emailTextChanged(emailText: String) {
        _emailText.value = emailText
        updateReaction()
    }

    fun passwordTextChanged(passwordText: String) {
        _passwordText.value = passwordText
        updateReaction()
    }


    private fun updateReaction() {
        viewModelScope.launch {
            if (_emailText.value.isNullOrEmpty() && _passwordText.value.isNullOrEmpty()){
                _reactionState.value = ReactionState.Suspicious
            }
            else if (
                !_emailText.value.isNullOrEmpty() && _passwordText.value.isNullOrEmpty() ||
                !_passwordText.value.isNullOrEmpty() && _emailText.value.isNullOrEmpty()
            ){
                _reactionState.value = ReactionState.Shocked
            }
            else{
                _reactionState.value = ReactionState.Happy
            }
        }
    }



}