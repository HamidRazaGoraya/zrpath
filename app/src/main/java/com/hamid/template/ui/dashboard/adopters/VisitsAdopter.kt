package com.hamid.template.ui.dashboard.adopters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.hamid.template.databinding.RecycleVisitBinding
import com.hamid.template.ui.facilitiesPatiensts.FacilitiyVM
import com.hamid.template.ui.facilitiesPatiensts.models.RequestTransportDetails
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
import com.hamid.template.utils.Resource
import com.hamid.template.utils.SharedPreferenceManager
import com.hamid.template.utils.makeGone
import com.hamid.template.utils.makeVisible

class VisitsAdopter(val mContext: Context, val edcationsList: ArrayList<TodayTripResponse.Data.Down>,val mapVM: FacilitiyVM,var sharedPreferenceManager: SharedPreferenceManager,var lifecycleOwner: LifecycleOwner) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)
    private var mClickListener: ItemClickListener? = null
    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecycleVisitView(RecycleVisitBinding.inflate(mInflater, parent, false))
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RecycleVisitView) {
            FillEducation(holder.binding, edcationsList[position], position)
        }
    }
    private fun FillEducation(binding: RecycleVisitBinding, allOffers: TodayTripResponse.Data.Down, position: Int) {
          binding.facilityLocation.setText(allOffers.transportationGroup.location)
          binding.facilityHouse.setText(allOffers.transportationGroup.facilityName)
          val arrayList=ArrayList<TodayTripResponse.Data.Down.Client>()
          arrayList.addAll(allOffers.clientList)

          binding.facilityDetails.setText("${allOffers.transportationGroup.staffNames}\n(${allOffers.transportationGroup.facilityName}|${allOffers.transportationGroup.groupLocation})")

        val tripStatus=allOffers.transportationGroup.GroupTripStatus
        when(tripStatus){
            "Trip not started"->{
                tripStartView(binding,allOffers.transportationGroup)
            }
            "Trip not start"->{
                tripStartView(binding,allOffers.transportationGroup)
            }
            "Trip Started"->{
                tripEndView(binding,allOffers.transportationGroup)
            }
            else->{
                tripCompletedView(binding)
            }
        }
        binding.tripDetails.setOnClickListener {
            mClickListener?.showToDayTripDetails(allOffers,position)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return edcationsList.size
    }

    class RecycleVisitView internal constructor(val binding: RecycleVisitBinding) :
        RecyclerView.ViewHolder(
            binding.root
        )

    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun showToDayTripDetails(group:TodayTripResponse.Data.Down,position: Int)
        fun onClicked(product: TodayTripResponse.Data.Down)
        fun onValueChanged(int: Int)
        fun onPickUpClicked(client:TodayTripResponse.Data.Down.Client,position: Int)
        fun onDropOfClicked(client:TodayTripResponse.Data.Down.Client,position: Int)
        fun onMissingClicked(client:TodayTripResponse.Data.Down.Client,position: Int)
        fun onStartTripClicked(group:TodayTripResponse.Data.Down.TransportationGroup)
        fun onEndTripClicked(group:TodayTripResponse.Data.Down.TransportationGroup)
    }

    fun insertItems(buttonsModel: TodayTripResponse.Data.Down) {
        this.edcationsList.add(buttonsModel)
        notifyItemInserted(itemCount - 1)
    }

    fun deleteAllItems() {
        val size = edcationsList.size
        edcationsList.clear()
        notifyItemRangeRemoved(0, size)
    }

    fun getButtonAt(position: Int): TodayTripResponse.Data.Down {
        return edcationsList[position]
    }

    fun UpdateAll(arrayList: List<TodayTripResponse.Data.Down>) {
        edcationsList.clear()
        edcationsList.addAll(arrayList)
        notifyDataSetChanged()
    }





    fun tripStartView(binding: RecycleVisitBinding,group:TodayTripResponse.Data.Down.TransportationGroup){
        binding.startStop.isEnabled=true
        binding.startStop.text="Start trip"
        binding.tripDetails.makeGone()
        binding.startStop.setOnClickListener {
            mClickListener?.onStartTripClicked(group)
        }
    }
    fun tripEndView(binding: RecycleVisitBinding,group:TodayTripResponse.Data.Down.TransportationGroup){
        binding.startStop.isEnabled=true
        binding.startStop.text="End trip"
        binding.tripDetails.makeVisible()
        binding.startStop.setOnClickListener {
            mClickListener?.onEndTripClicked(group)
        }
    }
    fun tripCompletedView(binding: RecycleVisitBinding){
        binding.startStop.isEnabled=false
        binding.startStop.text="Completed"
        binding.tripDetails.makeVisible()
    }

}