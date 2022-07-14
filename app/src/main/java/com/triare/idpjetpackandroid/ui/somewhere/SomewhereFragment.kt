package com.triare.idpjetpackandroid.ui.somewhere

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.triare.idpjetpackandroid.R
import com.triare.idpjetpackandroid.databinding.FragmentSomewhereBinding
import com.triare.idpjetpackandroid.ui.team_name.TeamNameFragmentDirections

class SomewhereFragment : Fragment() {

    private val binding: FragmentSomewhereBinding by viewBinding(CreateMethod.INFLATE)

    private val args: SomewhereFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    private fun initUi() {
        initTeamName()
        initAction()
        initToolbar()
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initAction() {
        binding.action.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_global_homeFragment)
        }
    }

    private fun initTeamName() {
        binding.name.text = args.teamName
    }
}