package com.example.viewmodellesson2.data.state

sealed class SecondLoginState {
    object Idle:SecondLoginState()
    object Loading:SecondLoginState()
    object Success:SecondLoginState()
    object Error:SecondLoginState()
}