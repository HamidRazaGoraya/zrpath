package com.hamid.template.ui.dashboard.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.hamid.template.R
import com.hamid.template.base.BaseFragment
import com.hamid.template.databinding.AddUserDetailsBinding
import com.hamid.template.databinding.FragmentHomeBinding
import com.hamid.template.databinding.RegisterFragmentBinding
import com.hamid.template.ui.dashboard.MainVM
import com.hamid.template.ui.loginAndRegister.RegisterVM
import com.hamid.template.ui.onboarding.OnBoardingVM
import com.hamid.template.utils.SharedPreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, MainVM>() {

    override val viewModel: MainVM by activityViewModels()

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager


    override fun setBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickEvents()
    }

    private fun setOnClickEvents() {
        val items = listOf("Up", "Down")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        binding.trips.setAdapter(adapter)
        binding.trips.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                  viewModel.tripType=binding.trips.text.toString()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        binding.toolbar.setNavigationOnClickListener {
               viewModel.showSideMenu()
        }
        binding.patientButton.setOnClickListener {
            viewModel.patientClicked()
        }
        binding.mapsButton.setOnClickListener {
            viewModel.mapsClicked()
        }
        binding.settingsButton.setOnClickListener {
            viewModel.settingsClicked()
        }
        binding.helpButton.setOnClickListener {
            viewModel.helpClicked()
        }
    }

    override fun onResume() {
        super.onResume()
        setOnClickEvents()
    }

}