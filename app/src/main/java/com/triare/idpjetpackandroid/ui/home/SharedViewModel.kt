package com.triare.idpjetpackandroid.ui.home

import androidx.lifecycle.ViewModel
import com.triare.idpjetpackandroid.base.ReplayMutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class SharedViewModel : ViewModel() {

    private var _scoreA = ReplayMutableSharedFlow<String>()
    val scoreA = _scoreA.asSharedFlow()

    private var _scoreB = ReplayMutableSharedFlow<String>()
    val scoreB = _scoreB.asSharedFlow()

    private var teamAScore = 0
    private var teamBScore = 0

    init {
        _scoreA.tryEmit(teamAScore.toString())
        _scoreB.tryEmit(teamBScore.toString())
    }

    fun goalToTeamA() {
        teamAScore++
        _scoreA.tryEmit(teamAScore.toString())

    }

    fun goalToTeamB() {
        teamBScore++
        _scoreB.tryEmit(teamBScore.toString())
    }
}