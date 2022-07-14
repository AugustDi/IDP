package com.triare.idpjetpackandroid.ui.team_name

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.triare.idpjetpackandroid.R
import com.triare.idpjetpackandroid.databinding.FragmentChangeTeamNameBinding
import com.triare.idpjetpackandroid.ext.launchWhenStarted
import com.triare.idpjetpackandroid.ui.somewhere.SomewhereFragmentDirections
import kotlinx.coroutines.flow.onEach

class TeamNameFragment : Fragment() {

    private val binding: FragmentChangeTeamNameBinding by viewBinding(CreateMethod.INFLATE)

    private val viewModel: TeamNameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    private fun initUi() {
        initToolbar()
        initGoSomewhere()
        initActionSave()
        binding.nameText.doAfterTextChanged { name -> viewModel.nameChanged(name.toString()) }
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initActionSave() {
        binding.actionSave.setOnClickListener { view ->
            view.findNavController().navigate(R.id.teamAFragment)
        }

    }

    private fun initGoSomewhere() {
        viewModel.teamName.onEach { teamName ->
            binding.actionGo.setOnClickListener { view ->
                val action =
                    TeamNameFragmentDirections.actionTeamNameFragmentToSomewhereFragment(teamName)
                view.findNavController().navigate(action)
            }
        }.launchWhenStarted(lifecycleScope)
    }

}