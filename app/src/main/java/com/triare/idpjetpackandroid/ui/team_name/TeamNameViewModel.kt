package com.triare.idpjetpackandroid.ui.team_name

import androidx.lifecycle.ViewModel
import com.triare.idpjetpackandroid.base.ReplayMutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class TeamNameViewModel : ViewModel() {

    private var _teamName = ReplayMutableSharedFlow<String>()
    val teamName = _teamName.asSharedFlow()


    fun nameChanged(name: String) {
        _teamName.tryEmit(name)
    }

}