package com.example.viewmodellesson2.data.state

sealed class ReactionState{
    object Idle: ReactionState()
    object Happy: ReactionState()
    object Shocked: ReactionState()
    object Suspicious: ReactionState()
}
