package com.hamid.template.ui.dashboard.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.hamid.template.R
import com.hamid.template.base.BaseFragment
import com.hamid.template.databinding.*
import com.hamid.template.ui.dashboard.MainVM
import com.hamid.template.ui.loginAndRegister.RegisterVM
import com.hamid.template.ui.onboarding.OnBoardingVM
import com.hamid.template.utils.SharedPreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding, MainVM>() {

    override val viewModel: MainVM by activityViewModels()

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager


    override fun setBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMapBinding {
        return FragmentMapBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       setOnClickEvents()
    }

    private fun setOnClickEvents() {
        binding.toolbar.setNavigationOnClickListener {
            viewModel.onButtonBackPressed()
        }
    }


    override fun onResume() {
        super.onResume()
    }

}