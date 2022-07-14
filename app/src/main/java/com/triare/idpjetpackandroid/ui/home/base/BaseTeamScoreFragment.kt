package com.triare.idpjetpackandroid.ui.home.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.triare.idpjetpackandroid.R
import com.triare.idpjetpackandroid.databinding.FragmentGameBaseBinding
import com.triare.idpjetpackandroid.ext.launchWhenStarted
import com.triare.idpjetpackandroid.ui.home.SharedViewModel
import kotlinx.coroutines.flow.onEach

abstract class BaseTeamScoreFragment : Fragment() {

    private val binding: FragmentGameBaseBinding by viewBinding(CreateMethod.INFLATE)

    private val viewModel: SharedViewModel by activityViewModels()


    abstract fun getActionText(): String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        initTeamAScore()
        initTeamBScore()
        initAction()
        initTeamName()
        action(binding)
        setTeamName(binding)
    }

    private fun initTeamName() {
        binding.teamName.text = getString(R.string.your_team_name)

        binding.teamName.setOnClickListener { view ->
            view.findNavController().navigate(R.id.included_graph)
        }
    }

    private fun initTeamAScore() {
        viewModel.scoreA.onEach {
            binding.scoreTeamA.text = it
        }.launchWhenStarted(lifecycleScope)
    }

    private fun initTeamBScore() {
        viewModel.scoreB.onEach {
            binding.scoreTeamB.text = it
        }.launchWhenStarted(lifecycleScope)
    }

    private fun initAction() {
        binding.action.text = getActionText()
    }

    open fun action(binding: FragmentGameBaseBinding) {}
    open fun setTeamName(binding: FragmentGameBaseBinding){}
}