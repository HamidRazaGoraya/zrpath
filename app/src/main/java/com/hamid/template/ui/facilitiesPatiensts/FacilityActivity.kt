package com.hamid.template.ui.facilitiesPatiensts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.navigation.Navigation
import com.google.gson.Gson
import com.hamid.template.R
import com.hamid.template.base.BaseActivity
import com.hamid.template.databinding.ActivityFacilityBinding
import com.hamid.template.ui.dashboard.models.AllFacilitiesModel
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
import com.hamid.template.ui.fillForm.FillFormActivity
import com.hamid.template.ui.mapScreen.ClientMapActivity
import com.hamid.template.ui.mapScreen.models.RequestDocumentUrl
import com.hamid.template.ui.mapScreen.models.ResponseDocumentList
import com.hamid.template.ui.todayTripsList.TodayTripActivity
import com.hamid.template.utils.Constants
import com.hamid.template.utils.Resource
import com.hamid.template.utils.SharedPreferenceManager
import com.hamid.template.utils.dialogs.AllFormsList
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
        window.statusBarColor=resources.getColor(R.color.primary_two)
        viewModel.facility=Gson().fromJson(intent!!.extras!!.getString(Constants.data,""),AllFacilitiesModel.Data::class.java)
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

    override fun onCheckListClicked() {
    }

    override fun getFormsList(clicked: Boolean, client: TodayTripResponse.Data.Down.Client) {
        viewModel.getDocumentList().observe(this){
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    viewModel.HideLoading()
                    it.data?.let { it1 ->
                        viewModel.responseDocumentList=it1
                        if (clicked){
                            viewModel.showSelectFormDialog(viewModel.responseDocumentList!!, client)
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    viewModel.HideLoading()
                    it.message?.let { it1 -> showSnackBar(it1) }
                }
                Resource.Status.LOADING -> {
                }
            }
        }
    }

    override fun showSelectFormDialog(documentList: ResponseDocumentList, client: TodayTripResponse.Data.Down.Client) {
        AllFormsList(object : AllFormsList.Buttons{
            override fun CareTypeClicked(careTypeId: ResponseDocumentList.DataItem) {
                viewModel.apiCallForUrl(careTypeId,client)
            }
        },documentList.data!!).show(supportFragmentManager,"ChooseForm")
    }

    override fun apiCallForUrl(form: ResponseDocumentList.DataItem, client: TodayTripResponse.Data.Down.Client) {
        val data= RequestDocumentUrl.Data(true,client.ReferralID,form.eBFormID)
        viewModel.getDocumentUrl(data).observe(this){
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    viewModel.HideLoading()
                    it.data?.let { it1 ->
                        val bundle=Bundle()
                        bundle.putString(Constants.data,Gson().toJson(it1))
                        bundle.putString(Constants.form,Gson().toJson(form))
                        bundle.putString(Constants.client,Gson().toJson(client))
                        startActivity(FillFormActivity.getIntent(this).putExtras(bundle))
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

    override fun startMapActivity(client: TodayTripResponse.Data.Down.Client) {
        val bundle=Bundle()
        bundle.putString(Constants.data,Gson().toJson(client))
        startActivity(ClientMapActivity.getIntent(this).putExtras(bundle))
    }

    override fun moveToTodayTrips(group: TodayTripResponse.Data.Down) {
        Log.i("dataCheck","001 ${group.transportationGroupID}")
        startActivity(TodayTripActivity.getIntent(this).putExtra(Constants.data,Gson().toJson(group)).putExtra(Constants.groupId,group.transportationGroup.transportationGroupID))
    }
}