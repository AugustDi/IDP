package com.triare.idpjetpackandroid.ui.home.team_b

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.triare.idpjetpackandroid.R
import com.triare.idpjetpackandroid.databinding.FragmentGameBaseBinding
import com.triare.idpjetpackandroid.ui.home.SharedViewModel
import com.triare.idpjetpackandroid.ui.home.base.BaseTeamScoreFragment

class TeamBFragment : BaseTeamScoreFragment() {

    private val viewModel: SharedViewModel by activityViewModels()


    override fun getActionText(): String = getString(R.string.goal_b)

    override fun action(binding: FragmentGameBaseBinding) {
        super.action(binding)

        binding.action.setOnClickListener {
            viewModel.goalToTeamB()
        }
    }

}