package com.hamid.template.ui.facilitiesPatiensts.frafments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.hamid.template.R
import com.hamid.template.base.BaseFragment
import com.hamid.template.databinding.AddUserDetailsBinding
import com.hamid.template.databinding.FragmentHomeBinding
import com.hamid.template.databinding.FragmentPatientBinding
import com.hamid.template.databinding.RegisterFragmentBinding
import com.hamid.template.ui.dashboard.MainVM
import com.hamid.template.ui.dashboard.adopters.VisitsAdopter
import com.hamid.template.ui.dashboard.models.DummyModel
import com.hamid.template.ui.facilitiesPatiensts.FacilitiyVM
import com.hamid.template.ui.loginAndRegister.RegisterVM
import com.hamid.template.ui.onboarding.OnBoardingVM
import com.hamid.template.utils.SharedPreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class PatientFragment : BaseFragment<FragmentPatientBinding, FacilitiyVM>() {

    override val viewModel: FacilitiyVM by activityViewModels()

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    lateinit var visitsAdopter: VisitsAdopter

    override fun setBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPatientBinding {
        return FragmentPatientBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       setOnClickEvents()
    }

    private fun setOnClickEvents() {
        binding.VisitType.setText("Trip Direction ${viewModel.tripType}")
        binding.toolbar.setNavigationOnClickListener {
             viewModel.onButtonBackPressed()
        }
        visitsAdopter= VisitsAdopter(requireContext(), ArrayList())
        binding.visitRecycle.adapter=visitsAdopter
        visitsAdopter.insertItems(DummyModel())
        visitsAdopter.insertItems(DummyModel())
        visitsAdopter.insertItems(DummyModel())
        visitsAdopter.insertItems(DummyModel())
        visitsAdopter.insertItems(DummyModel())
    }


    override fun onResume() {
        super.onResume()
    }

}