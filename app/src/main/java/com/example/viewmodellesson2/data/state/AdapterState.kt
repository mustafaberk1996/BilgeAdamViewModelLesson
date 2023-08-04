package com.example.viewmodellesson2.data.state


sealed class AdapterState {
    object Idle : AdapterState()
    class Remove(val index: Int) : AdapterState()
    class Add(val index: Int) : AdapterState()
    class Changed(val index: Int) : AdapterState()
}
