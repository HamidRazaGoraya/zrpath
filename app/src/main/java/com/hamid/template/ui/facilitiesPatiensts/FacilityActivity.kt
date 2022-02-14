package com.hamid.template.ui.facilitiesPatiensts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import com.google.gson.Gson
import com.hamid.template.R
import com.hamid.template.base.BaseActivity
import com.hamid.template.databinding.ActivityFacilityBinding
import com.hamid.template.databinding.ActivityOnboardingBinding
import com.hamid.template.databinding.ActivityRegistrationBinding
import com.hamid.template.ui.dashboard.MainActivity
import com.hamid.template.ui.dashboard.models.AllFacilitiesModel
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
import com.hamid.template.ui.loginAndRegister.fragments.LoginFragment
import com.hamid.template.ui.loginAndRegister.fragments.RegisterFragment
import com.hamid.template.ui.mapScreen.ClientMapActivity
import com.hamid.template.ui.onboarding.fragments.FillUserDetails
import com.hamid.template.utils.Constants
import com.hamid.template.utils.SharedPreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FacilityActivity : BaseActivity<ActivityFacilityBinding, FacilitiyVM>(), FacilityContracts {

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, FacilityActivity::class.java)
        }
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        viewModel.viewInteractor = this
        viewModel.facility=Gson().fromJson(intent.getStringExtra(Constants.data),AllFacilitiesModel.Data::class.java)
        viewModel.initThings()
    }

    override val viewModel: FacilitiyVM by viewModels()

    @Override
    override fun setBinding(layoutInflater: LayoutInflater): ActivityFacilityBinding {
        return ActivityFacilityBinding.inflate(layoutInflater)
    }



    @Override
    override fun setData() {
        window.setNavigationBarColor(resources.getColor(R.color.white))
        window.statusBarColor=resources.getColor(R.color.white)
        viewModel.facility=Gson().fromJson(intent!!.extras!!.getString("data",""),AllFacilitiesModel.Data::class.java)
    }

    override fun showPatientsList() {
        Navigation.findNavController(this, R.id.fragmentFacilities).navigate(R.id.action_home_to_Patient)
    }

    override fun onButtonBackPressed() {
              finish()
    }


    override fun ShowLoading() {
        showLoader()
    }

    override fun HideLoading() {
        hideLoader()
    }


    override fun startMapActivity(client: TodayTripResponse.Data.Down.Client) {
        val bundle=Bundle()
        bundle.putString(Constants.data,Gson().toJson(client))
        startActivity(ClientMapActivity.getIntent(this).putExtras(bundle))
    }




}