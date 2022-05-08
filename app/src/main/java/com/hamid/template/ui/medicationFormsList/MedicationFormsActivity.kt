package com.hamid.template.ui.medicationFormsList

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.gson.Gson
import com.hamid.template.R
import com.hamid.template.base.BaseActivity
import com.hamid.template.databinding.ActivityMedicationFormsBinding
import com.hamid.template.ui.checkList.models.RequestMedicationFormsList
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
import com.hamid.template.ui.fillForm.OpenFormActivity
import com.hamid.template.ui.fillForm.model.RequestSavedOpenForm
import com.hamid.template.ui.mapScreen.models.ResponseTripDetails
import com.hamid.template.ui.medicationFormsList.adopter.MedicationFormsAdopter
import com.hamid.template.ui.medicationFormsList.events.MedicationFormClicked
import com.hamid.template.ui.medicationFormsList.model.ResponseMedicationFormsList
import com.hamid.template.ui.missingForms.eventListners.FormClicked
import com.hamid.template.ui.missingForms.model.ResponseMissingDocument
import com.hamid.template.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MedicationFormsActivity : BaseActivity<ActivityMedicationFormsBinding, MedicaationFormsVM>(), MedicationFormsContracts {

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    lateinit var medicationFormsAdopter: MedicationFormsAdopter


    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MedicationFormsActivity::class.java)
        }
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        viewModel.viewInteractor = this
        viewModel.client= Gson().fromJson(intent.getStringExtra(Constants.client),TodayTripResponse.Data.Down.Client::class.java)
        viewModel.visitdetails=Gson().fromJson(intent.getStringExtra(Constants.visitDetails),ResponseTripDetails::class.java)
        viewModel.initThings(viewModel.client)
        binding.toolbar.setNavigationOnClickListener {
            val bundle=Bundle()
            bundle.putInt(Constants.actionKey,1)
            sendFinishBundle(bundle)
        }



    }




    override val viewModel: MedicaationFormsVM by viewModels()

    @Override
    override fun setBinding(layoutInflater: LayoutInflater): ActivityMedicationFormsBinding {
        return ActivityMedicationFormsBinding.inflate(layoutInflater)
    }

    override fun setData() {
        window.setNavigationBarColor(resources.getColor(R.color.white))
        window.statusBarColor=resources.getColor(R.color.primary_two)
        binding.swipe.setOnRefreshListener {
            binding.swipe.isRefreshing=true
            viewModel.getMedicationFormsList()
        }
    }

    override fun onButtonBackPressed() {
        sendFinishBundle(Bundle())
    }

    override fun ShowLoading() {

       showLoader()
    }

    override fun HideLoading() {
        binding.swipe.isRefreshing=false
        hideLoader()
    }

    override fun setUpFormsList() {
           medicationFormsAdopter= MedicationFormsAdopter(object : MedicationFormClicked {
               override fun openClicked(item: ResponseMedicationFormsList.DataItem) {
                        viewModel.moveToOpenFormActivity(item)
               }

               override fun editClicked(item: ResponseMedicationFormsList.DataItem) {

               }

               override fun deleteClicked(item: ResponseMedicationFormsList.DataItem) {

               }
           })
          binding.medicationFormsRecycle.adapter=medicationFormsAdopter
          viewModel.getMedicationFormsList()
    }

    override fun getMedicationFormsList() {
        viewModel.loadApiCallMedicationForms(RequestMedicationFormsList.Data(viewModel.client.ReferralID,sharedPreferenceManager.getEmployID())).observe(this){
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.swipe.isRefreshing=false
                    viewModel.HideLoading()
                    it.data?.let { data->
                        data.data?.let { formsList->
                            medicationFormsAdopter.updateItems(formsList)
                            binding.NoDataFound.setShowCondition(formsList.isNullOrEmpty())
                        }
                    }

                }
                Resource.Status.ERROR -> {
                    binding.swipe.isRefreshing=false
                    viewModel.HideLoading()
                    it.message?.let { it1 -> showSnackBar(it1) }
                }
                Resource.Status.LOADING -> {
                    viewModel.ShowLoading()
                }
            }
        }
    }

    override fun moveToOpenFormActivity(item: ResponseMedicationFormsList.DataItem) {
        viewModel.openSavedForm(RequestSavedOpenForm.Data(item.referralDocumentID,item.referralID)).observe(this){
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.swipe.isRefreshing=false
                    viewModel.HideLoading()
                    Log.i("status","here0")
                    it.data?.let { data->
                        val bundle=Bundle()
                        Log.i("status","here1")
                        data.isInternal=item.kindOfDocument.equals("Internal")
                        bundle.putString(Constants.data,Gson().toJson(data))
                        bundle.putString(Constants.client,intent.getStringExtra(Constants.client))
                        bundle.putString(Constants.checkList,intent.getStringExtra(Constants.client))
                        bundle.putString(Constants.visitDetails,intent.getStringExtra(Constants.visitDetails))
                        activityForResults.launch(OpenFormActivity.getIntent(this@MedicationFormsActivity).putExtras(bundle))
                    }

                }
                Resource.Status.ERROR -> {
                    Log.i("status","here2")
                    binding.swipe.isRefreshing=false
                    viewModel.HideLoading()
                    it.message?.let { it1 -> showSnackBar(it1) }
                }
                Resource.Status.LOADING -> {
                    viewModel.ShowLoading()
                }
            }
        }
    }
    val activityForResults = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result: ActivityResult ->
        viewModel.getMedicationFormsList()
    }
}