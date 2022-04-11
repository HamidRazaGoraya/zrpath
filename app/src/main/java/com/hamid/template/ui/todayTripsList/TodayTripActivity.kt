package com.hamid.template.ui.todayTripsList

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.gson.Gson
import com.hamid.template.R
import com.hamid.template.base.BaseActivity
import com.hamid.template.databinding.ActivityTodayTripBinding
import com.hamid.template.ui.dashboard.models.ResponseDashBoard
import com.hamid.template.ui.dashboard.models.VisitListModel
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
import com.hamid.template.ui.todayTripDetails.TodayTripDetailsActivity
import com.hamid.template.ui.todayTripsList.adopter.RefferalListAdopter
import com.hamid.template.ui.todayTripsList.models.RequestReferralList
import com.hamid.template.ui.todayTripsList.models.ResponseReferralList
import com.hamid.template.utils.Constants
import com.hamid.template.utils.Resource
import com.hamid.template.utils.SharedPreferenceManager
import com.hamid.template.utils.dialogs.eventsListners.OnRefferalClicked
import com.hamid.template.utils.setShowCondition
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TodayTripActivity : BaseActivity<ActivityTodayTripBinding, TodayTripVM>(), TodayTripContracts {

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    //lateinit var group: TodayTripResponse.Data.Down

    lateinit var refferalListAdopter:RefferalListAdopter
    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, TodayTripActivity::class.java)
        }
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        viewModel.viewInteractor = this
        viewModel.initThings()

    }




    override val viewModel: TodayTripVM by viewModels()

    @Override
    override fun setBinding(layoutInflater: LayoutInflater): ActivityTodayTripBinding {
        return ActivityTodayTripBinding.inflate(layoutInflater)
    }



    @Override
    override fun setUpView() {
        window.setNavigationBarColor(resources.getColor(R.color.white))
        window.statusBarColor=resources.getColor(R.color.primary_two)
       // group=Gson().fromJson(intent.getStringExtra(Constants.data),TodayTripResponse.Data.Down::class.java)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        binding.showFacilities.setOnClickListener {
            val bundle=Bundle()
            bundle.putInt(Constants.actionKey,1)
            sendFinishBundle(bundle)
        }
        binding.swipe.setOnRefreshListener {
            binding.swipe.isRefreshing=true
            viewModel.getDataApi()
        }
    }

    @Override
    override fun finishScreen() {
        finish()
    }

    @Override
    override fun setData() {

    }

    override fun onResume() {
        super.onResume()
        viewModel.getDataApi()
    }

    override fun getDataApi() {
        viewModel.getDashboard().observe(this){
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    viewModel.HideLoading()
                    it.data?.let { it1 ->
                        if (!it1.isSuccess)   {
                            showSnackBar(it.message)
                            return@observe
                        }
                       it1.data?.let {
                           val visitListModel=ArrayList<VisitListModel>()
                           if (!it.todayVisits.isNullOrEmpty()){
                               visitListModel.add(VisitListModel(true,null,true,"Today's visits"))
                               for (i in 0 until it.todayVisits.size){
                                   visitListModel.add(VisitListModel(true,it.todayVisits[i],false,null))
                               }
                           }
                           if (!it.nextDayVisits.isNullOrEmpty()){
                               visitListModel.add(VisitListModel(false,null,true,"Tomorrow's visits"))
                               for (i in 0 until it.nextDayVisits.size){
                                   visitListModel.add(VisitListModel(false,it.nextDayVisits[i],false,null))
                               }
                           }
                           refferalListAdopter.updateItems(visitListModel)
                           refferalListAdopter.notifyDataSetChanged()
                           binding.NoDataFound.setShowCondition(visitListModel.isNullOrEmpty())
                       }
                    }
                }
                Resource.Status.ERROR -> {
                    viewModel.HideLoading()
                    if (it.data==null){
                        showSnackBar(Gson().toJson(it.data))
                    }else{
                        it.message?.let { it1 -> showSnackBar(it1) }
                    }
                }
                Resource.Status.LOADING -> {
                    viewModel.ShowLoading()
                }
            }
        }
    }


    override fun HideLoading() {
        binding.swipe.isRefreshing=false
        hideLoader()
    }

    override fun ShowLoading() {
        showLoader()
    }

    override fun setupAdopter() {
        refferalListAdopter= RefferalListAdopter(object :OnRefferalClicked{
            override fun onClicked(item: ResponseDashBoard.Data.VisitItem) {
                viewModel.moveToDetailsActivity(item)
            }
        })
        binding.refferalList.adapter=refferalListAdopter
        viewModel.getDataApi()
    }

    override fun moveToDetailsActivity(item: ResponseDashBoard.Data.VisitItem) {
        todayTripDetails.launch(TodayTripDetailsActivity.getIntent(this).putExtra(Constants.data,Gson().toJson(item)))
    }

    val todayTripDetails = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            when(result.data!!.getIntExtra(Constants.actionKey,0)){
                1->{

                }
            }
        }

    }

}