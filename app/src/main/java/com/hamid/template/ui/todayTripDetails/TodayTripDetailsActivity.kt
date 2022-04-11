package com.hamid.template.ui.todayTripDetails

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
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
import com.hamid.template.ui.dashboard.models.AllFacilitiesModel
import com.hamid.template.ui.dashboard.models.ResponseDashBoard
import com.hamid.template.ui.facilitiesPatiensts.FacilityActivity
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
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
        viewModel.visitItem=Gson().fromJson(intent.getStringExtra(Constants.data),ResponseDashBoard.Data.VisitItem::class.java)
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
        binding.toolbar.title=viewModel.visitItem.referralName
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        binding.pickUpClicked.setOnClickListener {
            viewModel.clickedStartTrip()
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
        binding.dropClicked.setOnClickListener {
            viewModel.clickedDrop()
        }
        binding.swipe.setOnRefreshListener {
            binding.swipe.isRefreshing=true
            viewModel.checkGroupStatus()
        }
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
        viewModel.checkGroupStatus()
    }

    override fun allDeactive() {
        viewModel.disableTimeLine(binding.pickUpTimeLine,"1", start = false, end = true)
        viewModel.disableTimeLine(binding.pickCheckListLine,"2", start = true, end = true)
        viewModel.disableTimeLine(binding.formTimeLine,"3", start = true, end = true)
        viewModel.disableTimeLine(binding.dropCheckListLine,"4", start = true, end = true)
        viewModel.disableTimeLine(binding.dropTimeLine,"5", start = true, end = false)
        binding.pickUpClicked.setLocalEnable(false)
        binding.PickCheckListClicked.setLocalEnable(false)
        binding.formCardClicked.setLocalEnable(false)
        binding.SignatureClicked.setLocalEnable(false)
        binding.dropClicked.setLocalEnable(false)
    }

    override fun activePickUp() {
        binding.pickUpClicked.setLocalEnable(true)
        viewModel.activeTimeLine(binding.pickUpTimeLine,"1", start = false, end = true)
    }

    override fun activeCheckListPick() {
        binding.pickUpClicked.isEnabled=false
        binding.PickCheckListClicked.setLocalEnable(true)
        viewModel.activeTimeLine(binding.pickCheckListLine,"2", start = true, end = true)
    }

    override fun activeMissingForm() {
        binding.PickCheckListClicked.isEnabled=false
        binding.formCardClicked.setLocalEnable(true)
        viewModel.activeTimeLine(binding.formTimeLine,"3", start = true, end = true)
    }

    override fun activeSignature() {
        binding.SignatureClicked.setLocalEnable(true)
        viewModel.activeTimeLine(binding.dropCheckListLine,"4", start = true, end = true)
    }

    override fun activeDrop() {
        binding.SignatureClicked.isEnabled=false
        binding.dropClicked.setLocalEnable(true)
        viewModel.activeTimeLine(binding.dropTimeLine,"5", start = true, end = false)
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

    override fun clickedStartTrip() {
        activityForResults.launch(ClientMapActivity.getIntent(this).putExtra(Constants.onGoingVisit,Gson().toJson(viewModel.data)).putExtra(Constants.isPickUp,true))
    }

    override fun clickedPickUpCheckList() {
       viewModel.data?.let {
           val bundle=Bundle()
           bundle.putBoolean(Constants.isPickUp,true)
           val client: TodayTripResponse.Data.Down.Client=TodayTripResponse.Data.Down.Client(it.referralDetail!!.referralID,"","25",1,"","",viewModel.visitItem.FacilityID,"","","","","","",false,false,"",12,"","","","","","","","",ArrayList<Int>(),"",ArrayList<String>(),"",123,it.onGoingVisitDetail!!.transportationGroupID,123,"UP","")
           bundle.putString(Constants.client,Gson().toJson(client))
           bundle.putString(Constants.visitDetails,Gson().toJson(ResponseTripDetails(true,123,"",ResponseTripDetails.Data(false,"","","",12.2,23.5,"",123,13,false,123.5,it.onGoingVisitDetail.transportVisitID,it.onGoingVisitDetail.transportationGroupID,"",0.25),"200")))
           activityForResults.launch(CheckListActivity.getIntent(this).putExtras(bundle).putExtra(Constants.scheduleID,viewModel.data!!.onGoingVisitDetail!!.scheduleID))
       }
    }

    override fun clickedDropOfSignature() {
        viewModel.data?.let {
            DropSignHere(object :DropSignHere.OnSelected{
                override fun SignatureAdded(bitmap: Bitmap) {
                     viewModel.saveUserSignature(it.onGoingVisitDetail!!.transportVisitID,it.referralDetail!!.referralID,it.onGoingVisitDetail.scheduleID,FileEncoder.convertImageToFile(bitmap,this@TodayTripDetailsActivity)).observe(this@TodayTripDetailsActivity){
                         when (it.status) {
                             Resource.Status.SUCCESS -> {
                                 viewModel.HideLoading()
                                 viewModel.checkGroupStatus()
                             }
                             Resource.Status.ERROR -> {
                                 viewModel.checkGroupStatus()
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
            }).show(supportFragmentManager,"Sign here")
        }
    }

    override fun clickedDrop() {
        activityForResults.launch(ClientMapActivity.getIntent(this).putExtra(Constants.onGoingVisit,Gson().toJson(viewModel.data)).putExtra(Constants.isPickUp,false))
    }

    override fun clickedMissingForm() {
        viewModel.data?.let {
            val bundle=Bundle()
            val client: TodayTripResponse.Data.Down.Client=TodayTripResponse.Data.Down.Client(it.referralDetail!!.referralID,"","25",1,"","",viewModel.visitItem.FacilityID,"","","","","","",false,false,"",12,"","","","","","","","",ArrayList<Int>(),"",ArrayList<String>(),"",123,it.onGoingVisitDetail!!.transportationGroupID,123,"UP","")
            bundle.putString(Constants.client,Gson().toJson(client))
            bundle.putString(Constants.visitDetails,Gson().toJson(ResponseTripDetails(true,123,"",ResponseTripDetails.Data(false,"","","",12.2,23.5,"",123,13,false,123.5,it.onGoingVisitDetail.transportVisitID,it.onGoingVisitDetail.transportationGroupID,"",0.25),"200")))
            activityForResults.launch(MissingFormsActivity.getIntent(this).putExtras(bundle))
        }
    }



    val activityForResults = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result: ActivityResult ->
        binding.swipe.isRefreshing=true
        viewModel.checkGroupStatus()
        if (result.resultCode == Activity.RESULT_OK) {

        }

    }




    override fun finishTrip() {
        finish()
    }

    override fun loadVisitDetails() {
        viewModel.OnGoingVisit(viewModel.visitItem.scheduleID,viewModel.visitItem.referralID).observe(this){
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
                            it1.data.onGoingVisitDetail?.let { data->
                                viewModel.currentActive=1
                                binding.pickUpMarked.setShowCondition(!data.pickUpTime.isNullOrEmpty())
                                binding.signatureMarkedMarked.setShowCondition(data.Signed)
                                binding.dropOffMarked.setShowCondition(!data.dropOffTime.isNullOrEmpty())
                                binding.isCheckListCompleted.setShowCondition(data.isCheckListCompleted)
                                data.pickUpTime?.let {
                                    viewModel.currentActive=2
                                }
                                if (data.isCheckListCompleted){
                                    viewModel.currentActive=4
                                }
                                if (data.Signed){
                                    viewModel.currentActive=5
                                }
                                data.dropOffTime?.let {
                                    viewModel.currentActive=6
                                }
                                binding.pickCheckListText.text="${data.AddedUpCheckListCount}/${data.upCheckListCount}"
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

    override fun ShowLoading() {
        showLoader()
    }

    override fun HideLoading() {
        hideLoader()
        binding.swipe.isRefreshing=false
    }


    override fun checkGroupStatus() {
        viewModel.loadVisitDetails()
        binding.tripCompleted.makeGone()
        viewModel.visitItem.GroupTripStatus?.let {
            when(it){
                "Trip not started"->{
                    viewModel.loadGroupNotStartView()
                }
                "Trip Completed"->{

                }
                else->{
                    viewModel.loadGroupStartedView()
                }
            }
        }
    }

    override fun loadGroupNotStartView() {
        binding.GroupVisitFalse.makeVisible()
        binding.GroupVisitTrue.makeGone()
        binding.featuredLocked.ActionButton.setOnClickListener {
            val bundle=Bundle()
            val allFacilitiesModel=AllFacilitiesModel.Data(viewModel.visitItem.FacilityID,"")
            bundle.putString(Constants.data,allFacilitiesModel.toJsonString())
            activityForResults.launch(FacilityActivity.getIntent(this).putExtras(bundle))
        }
    }

    override fun loadGroupStartedView() {
        binding.GroupVisitFalse.makeGone()
        binding.GroupVisitTrue.makeVisible()
    }

    override fun loadGroupTripCompleted() {
        binding.pickUpClicked.isEnabled=false
        binding.PickCheckListClicked.isEnabled=false
        binding.formCardClicked.isEnabled=false
        binding.SignatureClicked.isEnabled=false
        binding.dropClicked.isEnabled=false
        binding.GroupTripCompleted.makeVisible()
    }
    override fun loadTripCompletedView() {
        binding.pickUpClicked.isEnabled=false
        binding.PickCheckListClicked.isEnabled=false
        binding.formCardClicked.isEnabled=false
        binding.SignatureClicked.isEnabled=false
        binding.dropClicked.isEnabled=false
        binding.tripCompleted.makeVisible()
    }

    override fun fillUserDetails(data: ResponseOnGoingVisit.Data) {
        data.referralDetail?.let {
            binding.clientDetails.run {
                phone.text=it.phone
                location.text=it.patientAddress
                location.paint.isUnderlineText=true
                location.setOnClickListener {view->
                    val url = "http://maps.google.com/maps?daddr="+it.patientAddress
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
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
                chip.getRandomColor()
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