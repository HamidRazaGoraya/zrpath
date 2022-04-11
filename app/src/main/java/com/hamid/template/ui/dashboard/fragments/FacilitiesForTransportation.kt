package com.hamid.template.ui.dashboard.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.google.gson.Gson
import com.hamid.template.R
import com.hamid.template.base.BaseFragment
import com.hamid.template.databinding.*
import com.hamid.template.ui.dashboard.MainVM
import com.hamid.template.ui.dashboard.adopters.FacilitiesAdopter
import com.hamid.template.ui.dashboard.models.AllFacilitiesModel
import com.hamid.template.ui.loginAndRegister.RegisterVM
import com.hamid.template.ui.onboarding.OnBoardingVM
import com.hamid.template.utils.Constants
import com.hamid.template.utils.Resource
import com.hamid.template.utils.SharedPreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class FacilitiesForTransportation : BaseFragment<FacilitiesFragmentBinding, MainVM>() {

    override val viewModel: MainVM by activityViewModels()

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    lateinit var facilitiesAdopter: FacilitiesAdopter

    override fun setBinding(layoutInflater: LayoutInflater, container: ViewGroup?): FacilitiesFragmentBinding {
        return FacilitiesFragmentBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       setOnClickEvents()
    }

    private fun setOnClickEvents() {
        binding.toolbar.setNavigationOnClickListener {
            viewModel.onButtonBackPressed()
        }
        facilitiesAdopter= FacilitiesAdopter(requireContext(), ArrayList())
        binding.AllFacilities.adapter=facilitiesAdopter
        facilitiesAdopter.setClickListener(object :FacilitiesAdopter.ItemClickListener{
            override fun onItemClicked(product: AllFacilitiesModel.Data) {
                val bundle=Bundle()
                bundle.putString(Constants.data,Gson().toJson(product))
                viewModel.onFacilitySelected(bundle)
            }
        })
        APICall()
    }

    private fun APICall() {
        viewModel.getFacilityDetails().observe(viewLifecycleOwner){
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    viewModel.HideLoading()
                    it.data?.let { it1 ->
                        facilitiesAdopter.deleteAllItems()
                        if (!it1.data.isNullOrEmpty()){
                            facilitiesAdopter.UpdateAll(it1.data)
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    viewModel.HideLoading()
                    it.message?.let { it1 -> showSnackBar(it1) }
                }
                Resource.Status.LOADING -> {
                    viewModel.ShowLoading()
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
    }

}