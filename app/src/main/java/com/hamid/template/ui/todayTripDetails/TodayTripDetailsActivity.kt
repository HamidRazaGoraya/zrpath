package com.hamid.template.ui.todayTripDetails

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.material.chip.Chip
import com.google.gson.Gson
import com.hamid.template.R
import com.hamid.template.base.BaseActivity
import com.hamid.template.databinding.ActivityTodayTripDetailsBinding
import com.hamid.template.ui.checkList.CheckListActivity
import com.hamid.template.ui.dashboard.models.VisitListModel
import com.hamid.template.ui.facilitiesPatiensts.models.RequestSetTime
import com.hamid.template.ui.mapScreen.ClientMapActivity
import com.hamid.template.ui.mapScreen.models.ResponseTripDetails
import com.hamid.template.ui.missingForms.MissingFormsActivity
import com.hamid.template.ui.todayTripDetails.dialogs.DropSignHere
import com.hamid.template.ui.todayTripDetails.models.ResponseOnGoingVisit
import com.hamid.template.utils.*
import com.repsly.library.timelineview.TimelineView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TodayTripDetailsActivity : BaseActivity<ActivityTodayTripDetailsBinding, TodayTripDetailsVM>(), TodayTripDetailsContracts {

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager



    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, TodayTripDetailsActivity::class.java)
        }
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        viewModel.viewInteractor = this
        viewModel.visitListModel=Gson().fromJson(intent.getStringExtra(Constants.data),VisitListModel::class.java)
        viewModel.initThings()
    }




    override val viewModel: TodayTripDetailsVM by viewModels()

    @Override
    override fun setBinding(layoutInflater: LayoutInflater): ActivityTodayTripDetailsBinding {
        return ActivityTodayTripDetailsBinding.inflate(layoutInflater)
    }



    @Override
    override fun setUpView() {
        window.setNavigationBarColor(resources.getColor(R.color.white))
        window.statusBarColor=resources.getColor(R.color.primary_two)
        viewModel.visitListModel.client?.let {
            binding.toolbar.title=it.name
            binding.clientDetails.run {
                parents.text=it.parentName
                location.text=it.address
                phone.text=it.phone
            }
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        binding.beginPrepareClicked.setOnClickListener {
            Log.i("Begin","000")
            viewModel.clickedBeginPrepare()
        }
        binding.PickCheckListClicked.setOnClickListener {
            viewModel.clickedPickUpCheckList()
        }
        binding.formCardClicked.setOnClickListener {
            viewModel.clickedMissingForm()
        }
        binding.SignatureClicked.setOnClickListener {
            viewModel.clickedDropOfSignature()
        }

        binding.swipe.setOnRefreshListener {
            binding.swipe.isRefreshing=true
            viewModel.loadVisitDetails()
        }
        viewModel.loadVisitDetails()
        viewModel.allDeactivate()
     }


    fun showLoading() {
        showLoader()
    }

    fun hideLoading() {
        hideLoader()
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
    }

    override fun allDeactivate() {
        viewModel.disableTimeLine(binding.beginPrepareTimeLine,"1", start = false, end = true)
        viewModel.disableTimeLine(binding.pickCheckListLine,"2", start = true, end = true)
        viewModel.disableTimeLine(binding.formTimeLine,"3", start = true, end = true)
        viewModel.disableTimeLine(binding.dropCheckListLine,"4", start = true, end = false)
        binding.beginPrepareClicked.setLocalEnable(false)
        binding.PickCheckListClicked.setLocalEnable(false)
        binding.formCardClicked.setLocalEnable(false)
        binding.SignatureClicked.setLocalEnable(false)
    }

    override fun activeBeginPrepare() {
        binding.beginPrepareClicked.setLocalEnable(true)
        viewModel.inProgressTimeLine(binding.beginPrepareTimeLine,"1", start = false, end = true)
    }

    override fun activeCheckListPick() {
        binding.beginPrepareClicked.isEnabled=false
        binding.PickCheckListClicked.setLocalEnable(true)
        viewModel.activeTimeLine(binding.beginPrepareTimeLine,"1", start = false, end = true)
        viewModel.inProgressTimeLine(binding.pickCheckListLine,"2", start = true, end = true)
    }

    override fun activeMissingForm() {
        binding.formCardClicked.setLocalEnable(true)
        viewModel.data?.onGoingVisitDetail?.let {
            if (it.isCheckListCompleted){
                viewModel.activeTimeLine(binding.pickCheckListLine,"2", start = true, end = true)
            }
        }
        viewModel.inProgressTimeLine(binding.formTimeLine,"3", start = true, end = true)
    }

    override fun activeSignature() {
        binding.SignatureClicked.setLocalEnable(true)
        viewModel.activeTimeLine(binding.formTimeLine,"3", start = true, end = true)
        viewModel.inProgressTimeLine(binding.dropCheckListLine,"4", start = true, end = false)
    }

    override fun loadAllPointsCompleted() {
        viewModel.activeTimeLine(binding.dropCheckListLine,"4", start = true, end = false)
    }



    override fun activeTimeLine(timelineView: TimelineView, string: String, start: Boolean, end: Boolean) {
        val colorGreen=resources.getColor(R.color.color_green)
        timelineView.markerColor=colorGreen
        if (start){
            timelineView.startLineColor=colorGreen
        }else{
            timelineView.setStartLineColor(resources.getColor(R.color.white))
        }
        if (end){
            timelineView.endLineColor=colorGreen
        }else{
            timelineView.setEndLineColor(resources.getColor(R.color.white))
        }
        timelineView.text=string
        timelineView.markerActiveColor=colorGreen
        timelineView.isActive=true
    }

    override fun disableTimeLine(timelineView: TimelineView, string: String, start: Boolean, end: Boolean) {
        val colorRed=resources.getColor(R.color.color_red)
        timelineView.markerColor=colorRed
        if (start){
            timelineView.startLineColor=colorRed
        }else{
            timelineView.setStartLineColor(resources.getColor(R.color.white))
        }
        if (end){
            timelineView.endLineColor=colorRed
        }else{
            timelineView.setEndLineColor(resources.getColor(R.color.white))
        }
        timelineView.text=string
        timelineView.markerActiveColor=colorRed
        timelineView.isActive=false
    }

    override fun inProgressTimeLine(timelineView: TimelineView, string: String, start: Boolean, end: Boolean) {
        val colorGreen=resources.getColor(R.color.yellow)
        timelineView.markerColor=colorGreen
        if (start){
            timelineView.startLineColor=colorGreen
        }else{
            timelineView.setStartLineColor(resources.getColor(R.color.white))
        }
        if (end){
            timelineView.endLineColor=colorGreen
        }else{
            timelineView.setEndLineColor(resources.getColor(R.color.white))
        }
        timelineView.text=string
        timelineView.markerActiveColor=colorGreen
        timelineView.isActive=true
    }



    override fun clickedBeginPrepare() {
        Log.i("Begin","001")
        viewModel.visitListModel.client?.let {
            viewModel.saveTTime(RequestSetTime.Data(true,1,null,0,it.transportationGroupID,null,null,null,it.scheduleID,sharedPreferenceManager.getEmployID())).observe(this){
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        viewModel.HideLoading()
                        it.data?.let { it1 ->
                            if (it1.message.equals("Trip Start Successfully")){
                                showSnackBar("Trip prepare started")
                            }

                            if (it1.isSuccess){
                                viewModel.loadVisitDetails()
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

    }

    override fun clickedPickUpCheckList() {
       viewModel.data?.let {
           val bundle=Bundle()
           bundle.putBoolean(Constants.isPickUp,true)
           viewModel.visitListModel.client?.let {
               bundle.putString(Constants.client,Gson().toJson(it))
           }
           bundle.putString(Constants.visitDetails,Gson().toJson(ResponseTripDetails(true,123,"",ResponseTripDetails.Data(false,"","","",12.2,23.5,"",123,13,false,123.5,it.onGoingVisitDetail!!.transportVisitID!!,it.onGoingVisitDetail.transportationGroupID,"",0.25),"200")))
           activityForResults.launch(CheckListActivity.getIntent(this).putExtras(bundle).putExtra(Constants.scheduleID,viewModel.data!!.onGoingVisitDetail!!.scheduleID))
       }
    }

    override fun clickedDropOfSignature() {
        viewModel.data?.let {
            DropSignHere(object :DropSignHere.OnSelected{
                override fun SignatureAdded(bitmap: Bitmap) {
                     viewModel.saveUserSignature(it.onGoingVisitDetail!!.transportVisitID!!,it.referralDetail!!.referralID,it.onGoingVisitDetail.scheduleID,FileEncoder.convertImageToFile(bitmap,this@TodayTripDetailsActivity)).observe(this@TodayTripDetailsActivity){
                         when (it.status) {
                             Resource.Status.SUCCESS -> {
                                 viewModel.HideLoading()
                                 viewModel.loadVisitDetails()
                             }
                             Resource.Status.ERROR -> {
                                 viewModel.loadVisitDetails()
                                 viewModel.HideLoading()
                                 it.message?.let { it1 -> showSnackBar(it1) }
                             }
                             Resource.Status.LOADING -> {
                                 viewModel.ShowLoading()
                             }
                         }
                     }
                }
                override fun SignatureNotAdded() {
                    showSnackBar(getString(R.string.please_sign_to_submit))
                }
            },viewModel.data).show(supportFragmentManager,"Sign here")
        }
    }

    override fun clickedMissingForm() {
        viewModel.data?.let {
            val bundle=Bundle()
            viewModel.visitListModel.client?.let {
                bundle.putString(Constants.client,Gson().toJson(it))
            }
            bundle.putString(Constants.visitDetails,Gson().toJson(ResponseTripDetails(true,123,"",ResponseTripDetails.Data(false,"","","",12.2,23.5,"",123,13,false,123.5,it.onGoingVisitDetail!!.transportVisitID!!,it.onGoingVisitDetail.transportationGroupID,"",0.25),"200")))
            activityForResults.launch(MissingFormsActivity.getIntent(this).putExtras(bundle))
        }
    }



    val activityForResults = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result: ActivityResult ->
        binding.swipe.isRefreshing=true
        viewModel.loadVisitDetails()
        if (result.resultCode == Activity.RESULT_OK) {

        }

    }




    override fun finishTrip() {
        finish()
    }

    override fun loadVisitDetails() {
        viewModel.visitListModel.client?.let {
            viewModel.OnGoingVisit(it.scheduleID,it.ReferralID,it.transportationGroupID).observe(this){
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        viewModel.HideLoading()

                        it.data?.let { it1 ->
                            if (it1.data==null){
                                showSnackBar("On going visit details not found")
                                return@observe
                            }


                            it1.data.let {
                                viewModel.data=it1.data
                            }
                            viewModel.data=it1.data
                            if (it1.data.onGoingVisitDetail==null){
                                viewModel.currentActive=1
                                viewModel.handleActivation()
                            }

                            it1.data.onGoingVisitDetail?.let { data->

                                val checkIfPrepareStarted=data.transportVisitID.checkIfIDFound()

                                if (!checkIfPrepareStarted){
                                    viewModel.currentActive=1
                                    viewModel.handleActivation()
                                    return@observe
                                }
                                viewModel.currentActive=2
                                if (data.isCheckListCompleted){
                                    viewModel.currentActive=4
                                }
                                if (data.Signed){
                                    viewModel.currentActive=5
                                }

                                binding.pickCheckListText.text="${data.AddedUpCheckListCount}/${data.upCheckListCount}"
                                data.dropOffTime?.let {
                                    viewModel.currentActive=6
                                }
                            }
                            viewModel.handleActivation()
                            it1.data.let {
                                viewModel.fillUserDetails(it)
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

    }

    override fun ShowLoading() {
        showLoader()
    }

    override fun HideLoading() {
        hideLoader()
        binding.swipe.isRefreshing=false
    }

    override fun loadTripCompletedView() {
        binding.beginPrepareClicked.isEnabled=false
        binding.PickCheckListClicked.isEnabled=false
        binding.formCardClicked.isEnabled=false
        binding.SignatureClicked.isEnabled=false
        binding.tripCompleted.makeVisible()
        viewModel.activeTimeLine(binding.dropCheckListLine,"4", start = true, end = false)
    }

    override fun fillUserDetails(data: ResponseOnGoingVisit.Data) {
        data.referralDetail?.let {
            binding.clientDetails.run {
                phone.text=it.phone
                location.text=it.patientAddress
                location.paint.isUnderlineText=true
                location.setOnClickListener {view->

                    val gmmIntentUri = Uri.parse("geo:0,0?q=${it.patientAddress}")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    startActivity(mapIntent)
                }
                parents.text=it.ParentName
            }
        }
        data.onGoingVisitDetail?.let {
            binding.clientDetails.statusName.text=it.scheduleStatusName
             val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
             val allFilters=it.transportationFilterNames.toArrayList()
             val allIds=it.transportationFilterIDs.toArrayList()
            binding.clientDetails.chipsInput.removeAllViews()
            for (i in allFilters.indices){
                val chip: Chip = inflater.inflate(R.layout.custome_chip, null) as Chip
                chip.setEnsureMinTouchTargetSize(false)
                chip.text=allFilters[i]
                chip.labelFor=allIds[i].toInt()
                chip.getRandomColorChip()
                binding.clientDetails.chipsInput.addView(chip)
            }
            binding.tvStartTimeValue.ShowEmptyDot()
            binding.tvEndTimeValue.ShowEmptyDot()
            it.pickUpTime?.let {
                binding.tvStartTimeValue.text=it.getLocalTime()
            }
            it.dropOffTime?.let {
                binding.tvEndTimeValue.text=it.getLocalTime()
            }
        }
    }



}