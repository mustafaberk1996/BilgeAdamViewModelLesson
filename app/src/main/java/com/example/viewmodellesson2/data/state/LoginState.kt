package com.example.viewmodellesson2.data.state

sealed class LoginState {
    object Idle: LoginState()
    object Success: LoginState()
    object Error: LoginState()


}