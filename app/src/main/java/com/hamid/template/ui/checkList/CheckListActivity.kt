package com.hamid.template.ui.checkList

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.gson.Gson
import com.hamid.template.R
import com.hamid.template.base.BaseActivity
import com.hamid.template.databinding.ActivityCheckListBinding
import com.hamid.template.ui.checkList.adopter.CheckListAdopter
import com.hamid.template.ui.checkList.models.RequestSelfCheckList
import com.hamid.template.ui.facilitiesPatiensts.models.RequestDeleteCheck
import com.hamid.template.ui.facilitiesPatiensts.models.RequestSaveCheck
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
import com.hamid.template.ui.fillForm.FillFormActivity
import com.hamid.template.ui.mapScreen.models.RequestDocumentUrl
import com.hamid.template.ui.mapScreen.models.ResponseDocumentList
import com.hamid.template.ui.mapScreen.models.ResponseTripDetails
import com.hamid.template.ui.mapScreen.models.UserCheckListResponse
import com.hamid.template.utils.*
import com.hamid.template.utils.dialogs.AllFormsList
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CheckListActivity : BaseActivity<ActivityCheckListBinding, CheckListVM>(), CheckListContracts {

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    lateinit var checkListAdopter:CheckListAdopter
    lateinit var client:TodayTripResponse.Data.Down.Client
    lateinit var visitdetails:ResponseTripDetails
    var scheduleID:Int=0;
    var isPickUp: Boolean=false

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, CheckListActivity::class.java)
        }
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        viewModel.viewInteractor = this
        scheduleID=intent.getIntExtra(Constants.scheduleID,0)
        client= Gson().fromJson(intent.getStringExtra(Constants.client),TodayTripResponse.Data.Down.Client::class.java)
        visitdetails=Gson().fromJson(intent.getStringExtra(Constants.visitDetails),ResponseTripDetails::class.java)
        isPickUp=intent.getBooleanExtra(Constants.isPickUp,false)
        viewModel.initThings(client)
        binding.toolbar.setNavigationOnClickListener {
            val bundle=Bundle()
            bundle.putInt(Constants.actionKey,1)
            sendFinishBundle(bundle)
        }

    }




    override val viewModel: CheckListVM by viewModels()

    @Override
    override fun setBinding(layoutInflater: LayoutInflater): ActivityCheckListBinding {
        return ActivityCheckListBinding.inflate(layoutInflater)
    }

    override fun setData() {
        window.setNavigationBarColor(resources.getColor(R.color.white))
        window.statusBarColor=resources.getColor(R.color.primary_two)
        checkListAdopter= CheckListAdopter(object :CheckListAdopter.myClickListner{
            override fun onChecked(check: UserCheckListResponse.Data.CheckListItem) {
              viewModel.checkCheckList(RequestSaveCheck.Data("",check.CheckListID,visitdetails.data!!.transportVisitID,sharedPreferenceManager.getEmployID(),check.transportVisitNoteID,null)).observe(this@CheckListActivity){
                  when (it.status) {
                      Resource.Status.SUCCESS -> {
                          viewModel.HideLoading()
                          it.data?.let { it1 ->
                              viewModel.checkForCheckList()
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

            override fun onUnChecked(checK:UserCheckListResponse.Data.CheckListItem) {
                 viewModel.unCheckList(RequestDeleteCheck.Data(checK.transportVisitNoteID)).observe(this@CheckListActivity){
                     when (it.status) {
                         Resource.Status.SUCCESS -> {
                             viewModel.HideLoading()
                             it.data?.let { it1 ->
                                 viewModel.checkForCheckList()
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

            override fun onFormClicked(checK: UserCheckListResponse.Data.CheckListItem) {
                viewModel.ShowLoading()
                viewModel.apiCallForUrl(checK,client)
            }
        })
       binding.checkRecycle.adapter=checkListAdopter
        binding.completeCheckList.setOnClickListener {
          viewModel.checkListCompleted()
        }
    }

    override fun checkListCompleted() {
        viewModel.checkListCompleted(RequestSelfCheckList.Data(visitdetails.data!!.transportVisitID,scheduleID)).observe(this){

            when (it.status) {
                Resource.Status.SUCCESS -> {
                    viewModel.HideLoading()
                    it.data?.let { it1 ->
                        showSnackBar(it1.message)
                       if (it1.isSuccess){
                           viewModel.onButtonBackPressed()
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

    override fun onButtonBackPressed() {
        sendFinishBundle(Bundle())
    }

    override fun ShowLoading() {
       showLoader()
    }

    override fun HideLoading() {
        hideLoader()
    }

    override fun checkForCheckList() {
        viewModel.getUserCheckList(client.ReferralID,client.tripDirection).observe(this){
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

    private fun FillCheckList(it1: UserCheckListResponse) {
        if (it1.isSuccess){
            it1.data.let {
                checkListAdopter.updateItems(it.checkList)
            }

        }
    }






    override fun apiCallForUrl(checK: UserCheckListResponse.Data.CheckListItem, client: TodayTripResponse.Data.Down.Client) {
        val data= RequestDocumentUrl.Data(true,client.ReferralID,checK.EBFormID!!)
        viewModel.getDocumentUrl(data).observe(this){
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    viewModel.HideLoading()
                    it.data?.let { it1 ->
                        val bundle=Bundle()
                        bundle.putString(Constants.data,Gson().toJson(it1))
                        bundle.putString(Constants.client,Gson().toJson(client))
                        bundle.putString(Constants.checkList,Gson().toJson(checK))
                        bundle.putString(Constants.visitDetails,Gson().toJson(visitdetails))
                        activityForResults.launch(FillFormActivity.getIntent(this).putExtras(bundle))
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


    val activityForResults = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result: ActivityResult ->
        viewModel.checkForCheckList()


    }

}