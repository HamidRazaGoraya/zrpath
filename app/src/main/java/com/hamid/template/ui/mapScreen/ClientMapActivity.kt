package com.hamid.template.ui.mapScreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.chip.Chip
import com.google.gson.Gson
import com.hamid.template.R
import com.hamid.template.base.BaseActivity
import com.hamid.template.databinding.ActivityMapsBinding
import com.hamid.template.ui.checkList.CheckListActivity
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
import com.hamid.template.ui.fillForm.FillFormActivity
import com.hamid.template.ui.mapScreen.models.ResponseDocumentList
import com.hamid.template.ui.mapScreen.models.UserCheckListResponse
import com.hamid.template.utils.*
import com.hamid.template.utils.dialogs.AllFormsList
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ClientMapActivity : BaseActivity<ActivityMapsBinding, MapVM>(), MapContracts {

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, ClientMapActivity::class.java)
        }
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        viewModel.viewInteractor = this
        viewModel.client=Gson().fromJson(intent.getStringExtra(Constants.data),TodayTripResponse.Data.Down.Client::class.java)
        viewModel.initThings()
    }

    override val viewModel: MapVM by viewModels()

    @Override
    override fun setBinding(layoutInflater: LayoutInflater): ActivityMapsBinding {
        return ActivityMapsBinding.inflate(layoutInflater)
    }



    @Override
    override fun setData() {
        window.setNavigationBarColor(resources.getColor(R.color.white))
        window.statusBarColor=resources.getColor(R.color.white)
        binding.toolbar.setNavigationOnClickListener {
            viewModel.onButtonBackPressed()
        }
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        viewModel.ShowLoading()
        mapFragment.getMapAsync {
            val sydney = LatLng(-34.0, 151.0)
            it.moveCamera(CameraUpdateFactory.newLatLng(sydney))
            viewModel.HideLoading()
        }
        viewModel.fillUserDetails()
        viewModel.checkForCheckList()
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


    override fun fillUserDetails(client: TodayTripResponse.Data.Down.Client) {
        val userDetails=binding.mapClientDetails
        userDetails.clientName.text = client.name
        userDetails.parents.text = client.parentName
        userDetails.genderAge.text="${client.gender} , ${client.age}"
        userDetails.phone.text = client.phone
        userDetails.location.text = client.fullAddress
        val allFilters=client.transportationFilterName
        val allIds=client.transportationFilterID
        for (i in allFilters.indices){
            val chip = Chip(ContextThemeWrapper(this,R.style.MaterialComponents_Chip_Thin),null,0)
            chip.text=allFilters[i]
            chip.labelFor=allIds[i]
            chip.getRandomColor()
            userDetails.chipsInput.addView(chip)
        }
        userDetails.digitalForm.setOnClickListener {
            if (viewModel.responseDocumentList!=null){
                viewModel.showSelectFormDialog(viewModel.responseDocumentList!!)
            }else{
                viewModel.ShowLoading()
                viewModel.getFormsList(true)
            }
        }

    }

    override fun apiCallForUrl(form: ResponseDocumentList.DataItem) {
        viewModel.getDocumentUrl(form.eBFormID,viewModel.client.ReferralID,true).observe(this){
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    viewModel.HideLoading()
                    it.data?.let { it1 ->
                        startActivity(FillFormActivity.getIntent(this@ClientMapActivity).putExtra(Constants.data,Gson().toJson(it1)))
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

    override fun showSelectFormDialog(documentList: ResponseDocumentList) {
            AllFormsList(object :AllFormsList.Buttons{
                override fun CareTypeClicked(careTypeId: ResponseDocumentList.DataItem) {
                    viewModel.apiCallForUrl(careTypeId)
                }
            },documentList.data!!).show(supportFragmentManager,"ChooseForm")
    }
    override fun getFormsList(clicked:Boolean) {
        viewModel.getDocumentList().observe(this){
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    viewModel.HideLoading()
                    it.data?.let { it1 ->
                        viewModel.responseDocumentList=it1
                        if (clicked){

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
    override fun checkForCheckList() {
        binding.mapClientDetails.checkList.makeGone()
        viewModel.getUserCheckList(0,viewModel.client.ReferralID).observe(this){
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    viewModel.HideLoading()
                    it.data?.let { it1 ->
                        FillCheckList(it1)
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

    override fun onCheckListClicked() {
            checkListActivityRexponse.launch(CheckListActivity.getIntent(this).putExtra(Constants.data,Gson().toJson(viewModel.client)))
    }

    var checkListActivityRexponse= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        viewModel.checkForCheckList()
    }

    private fun FillCheckList(it1: UserCheckListResponse) {
        if (it1.isSuccess){
            it1.data?.let {
                if (!it.checkList.isNullOrEmpty()){
                    binding.mapClientDetails.checkList.makeVisible()
                    binding.mapClientDetails.checkList.setText("CheckList  ${it.checkList.filter { it.isChecked}.size} / ${it.checkList.size}}")
                    binding.mapClientDetails.checkList.setOnClickListener {
                      viewModel.onCheckListClicked()
                    }
                }
            }

        }
    }

}