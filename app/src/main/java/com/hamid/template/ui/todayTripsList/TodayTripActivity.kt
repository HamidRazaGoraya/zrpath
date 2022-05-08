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
import com.hamid.template.ui.mapScreen.ClientMapActivity
import com.hamid.template.ui.todayTripDetails.TodayTripDetailsActivity
import com.hamid.template.ui.todayTripsList.adopter.RefferalListAdopter
import com.hamid.template.ui.todayTripsList.models.RequestDashboardAPI
import com.hamid.template.ui.todayTripsList.models.RequestReferralList
import com.hamid.template.ui.todayTripsList.models.ResponseReferralList
import com.hamid.template.utils.*
import com.hamid.template.utils.dialogs.CalenderDatePicker
import com.hamid.template.utils.dialogs.eventsListners.OnRefferalClicked
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

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
    lateinit var calendar:Calendar

    @Override
    override fun setBinding(layoutInflater: LayoutInflater): ActivityTodayTripBinding {
        return ActivityTodayTripBinding.inflate(layoutInflater)
    }



    @Override
    override fun setUpView() {
        window.setNavigationBarColor(resources.getColor(R.color.white))
        window.statusBarColor=resources.getColor(R.color.primary_two)
        intent.extras?.let {
            Log.i("dataCheck","001")
            if (it.containsKey(Constants.groupId)){
                Log.i("dataCheck","002")
                viewModel.transportationGroupId=it.getInt(Constants.groupId,0)
                Log.i("dataCheck","003 ${viewModel.transportationGroupId}")
            }
        }

        calendar= Calendar.getInstance()
        binding.SelectedDate.setText(calendar.getDateValueLocal())
        val items = listOf(Constants.Up, Constants.Down)
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        binding.trips.setAdapter(adapter)
        binding.trips.setText(sharedPreferenceManager.getTripType,false)
        binding.trips.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                sharedPreferenceManager.getTripType=binding.trips.text.toString()
                binding.trips.clearFocus()
                viewModel.todayTripResponse?.let {
                    viewModel.handleTrips(it)
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
        binding.SelectedDate.setOnClickListener {
            CalenderDatePicker(object : CalenderDatePicker.OnSelected{
                override fun Selected(calendar: Calendar) {
                    this@TodayTripActivity.calendar=calendar
                    binding.SelectedDate.setText(calendar.getDateValueLocal())
                    viewModel.getDataApi()
                }
            },calendar = calendar).show(supportFragmentManager,"Calender")
        }
        binding.toolbar.setNavigationOnClickListener {
            viewModel.onBackClick()
        }



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

        viewModel.getTodayTrips(calendar.getDateValue(),0).observe(this){
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    viewModel.HideLoading()
                    it.data?.let { it1 ->
                        viewModel.todayTripResponse=it1
                        viewModel.handleTrips(it1)
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

    override fun handleTrips(todayTripResponse: TodayTripResponse) {
        if (sharedPreferenceManager.getTripType.equals(Constants.Down)){
            viewModel.loadTripDirection(todayTripResponse.data.downList)
        }else{
            viewModel.loadTripDirection(todayTripResponse.data.upList)
        }
    }

    override fun loadTripDirection(data: List<TodayTripResponse.Data.Down>) {
        binding.NoDataFound.setShowCondition(data.isNullOrEmpty())
        if (data.isNullOrEmpty()){
          return
        }
        val list=ArrayList<VisitListModel>()
        data.forEach { group->
            list.add(VisitListModel(null,true,group.transportationGroup))
            group.clientList.forEach {child->
                list.add(VisitListModel(child,false,group.transportationGroup))
            }
        }
        refferalListAdopter.updateItems(list)
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

            override fun onClicked(visitListModel: VisitListModel) {

            }

            override fun onPrepareClicked(visitListModel: VisitListModel) {

            }

            override fun onStartTripClicked(visitListModel: VisitListModel) {

            }

            override fun onEndTripClicked(visitListModel: VisitListModel) {

            }

            override fun onGroupTripStart(group: TodayTripResponse.Data.Down.TransportationGroup) {
                val bundle=Bundle()
                bundle.putString(Constants.data,Gson().toJson(group))
                bundle.putBoolean(Constants.startTrip,true)
                todayTripDetails.launch(ClientMapActivity.getIntent(this@TodayTripActivity).putExtras(bundle))
            }

            override fun onGroupTripEnd(group: TodayTripResponse.Data.Down.TransportationGroup) {
                val bundle=Bundle()
                bundle.putString(Constants.data,Gson().toJson(group))
                bundle.putBoolean(Constants.startTrip,false)
                todayTripDetails.launch(ClientMapActivity.getIntent(this@TodayTripActivity).putExtras(bundle))
            }
        })

        binding.refferalList.adapter=refferalListAdopter
        viewModel.getDataApi()
    }

    override fun moveToDetailsActivity(visitListModel: VisitListModel) {
        todayTripDetails.launch(TodayTripDetailsActivity.getIntent(this).putExtra(Constants.data,Gson().toJson(visitListModel)))
    }


    val todayTripDetails = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.getDataApi()
        }

    }

}