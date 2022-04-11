package com.hamid.template.ui.facilitiesPatiensts.frafments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.google.gson.Gson
import com.hamid.template.R
import com.hamid.template.base.BaseFragment
import com.hamid.template.databinding.FragmentPatientBinding
import com.hamid.template.ui.checkList.CheckListActivity
import com.hamid.template.ui.dashboard.adopters.VisitsAdopter
import com.hamid.template.ui.facilitiesPatiensts.FacilitiyVM
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
import com.hamid.template.ui.mapScreen.ClientMapActivity
import com.hamid.template.utils.*
import com.hamid.template.utils.dialogs.CalenderDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


@AndroidEntryPoint
class PatientFragment : BaseFragment<FragmentPatientBinding, FacilitiyVM>() {

    override val viewModel: FacilitiyVM by activityViewModels()

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    lateinit var visitsAdopter: VisitsAdopter

    lateinit var calendar: Calendar
    override fun setBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPatientBinding {
        return FragmentPatientBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       setOnClickEvents()
    }

    private fun setOnClickEvents() {
        calendar= Calendar.getInstance()
        binding.SelectedDate.setText(calendar.getDateValueLocal())
        val items = listOf(Constants.Up, Constants.Down)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        binding.trips.setAdapter(adapter)
        binding.trips.setText(sharedPreferenceManager.getTripType,false)
        binding.trips.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                sharedPreferenceManager.getTripType=binding.trips.text.toString()
                binding.trips.clearFocus()
                getAllTrips()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
        binding.toolbar.setNavigationOnClickListener {
             viewModel.onButtonBackPressed()
        }
        visitsAdopter= VisitsAdopter(requireContext(), ArrayList(),viewModel,sharedPreferenceManager,viewLifecycleOwner)
        binding.visitRecycle.adapter=visitsAdopter
        visitsAdopter.setClickListener(object :VisitsAdopter.ItemClickListener{
            override fun showToDayTripDetails(group: TodayTripResponse.Data.Down, position: Int) {
                viewModel.moveToTodayTrips(group)
            }

            override fun onClicked(product: TodayTripResponse.Data.Down) {

            }

            override fun onValueChanged(int: Int) {

            }



            override fun onPickUpClicked(client: TodayTripResponse.Data.Down.Client,position: Int) {
                val visitDetails=visitsAdopter.edcationsList.get(position).responseTripDetails
                if (visitDetails==null){
                    showSnackBar("Start trip first")
                    return
                }
                if (visitDetails.data==null){
                    showSnackBar("Start trip first")
                    return
                }
                val bundle=Bundle()
                bundle.putString(Constants.data,Gson().toJson(client))
                bundle.putString(Constants.visitDetails,Gson().toJson(visitDetails))
                bundle.putBoolean(Constants.isPickUp,true)
                checkListActivityRexponse.launch(CheckListActivity.getIntent(requireContext()).putExtras(bundle))

            }

            override fun onDropOfClicked(client: TodayTripResponse.Data.Down.Client,position: Int) {
                val visitDetails=visitsAdopter.edcationsList.get(position).responseTripDetails
                if (visitDetails==null){
                    showSnackBar("Start trip first")
                    return
                }
                if (visitDetails.data==null){
                    showSnackBar("Start trip first")
                    return
                }
                val bundle=Bundle()
                bundle.putString(Constants.data,Gson().toJson(client))
                bundle.putString(Constants.visitDetails,Gson().toJson(visitDetails))
                bundle.putBoolean(Constants.isPickUp,false)
                checkListActivityRexponse.launch(CheckListActivity.getIntent(requireContext()).putExtras(bundle))
            }

            override fun onMissingClicked(client: TodayTripResponse.Data.Down.Client,position: Int) {
                val visitDetails=visitsAdopter.edcationsList.get(position).responseTripDetails
                if (visitDetails==null){
                    showSnackBar("Start trip first")
                    return
                }
                if (visitDetails.data==null){
                    showSnackBar("Start trip first")
                    return
                }
                if (viewModel.responseDocumentList!=null){
                    viewModel.showSelectFormDialog(viewModel.responseDocumentList!!,client)
                }else{
                    viewModel.ShowLoading()
                    viewModel.getFormsList(true,client)
                }
            }

            override fun onStartTripClicked(group: TodayTripResponse.Data.Down.TransportationGroup) {
                val bundle=Bundle()
                bundle.putString(Constants.data,Gson().toJson(group))
                bundle.putBoolean(Constants.startTrip,true)
                mapActivityRexponse.launch(ClientMapActivity.getIntent(requireContext()).putExtras(bundle))
            }

            override fun onEndTripClicked(group: TodayTripResponse.Data.Down.TransportationGroup) {
                val bundle=Bundle()
                bundle.putString(Constants.data,Gson().toJson(group))
                bundle.putBoolean(Constants.startTrip,false)
                mapActivityRexponse.launch(ClientMapActivity.getIntent(requireContext()).putExtras(bundle))
            }
        })
        binding.SelectedDate.setOnClickListener {
            CalenderDatePicker(object :CalenderDatePicker.OnSelected{
                override fun Selected(calendar: Calendar) {
                 this@PatientFragment.calendar=calendar
                    binding.SelectedDate.setText(calendar.getDateValueLocal())
                    getAllTrips()
                }
            },calendar = calendar).show(childFragmentManager,"Calender")
        }
        getAllTrips()
    }
    fun getAllTrips(){
        viewModel.getTodayTrips(calendar.getDateValue()).observe(viewLifecycleOwner){
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    viewModel.HideLoading()
                    it.data?.let { it1 ->
                        HandleTrips(it1)
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

    private fun HandleTrips(it1: TodayTripResponse) {
        if (sharedPreferenceManager.getTripType.equals(Constants.Down)){
            visitsAdopter.UpdateAll(it1.data.downList)
        }else{
            visitsAdopter.UpdateAll(it1.data.upList)
        }
    }


    override fun onResume() {
        super.onResume()
    }

    var checkListActivityRexponse= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

    }
    var mapActivityRexponse= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
             getAllTrips()
    }

}