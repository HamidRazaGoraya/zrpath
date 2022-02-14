package com.hamid.template.ui.dashboard.adopters

import android.annotation.SuppressLint
import android.content.Context
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.hamid.template.R
import com.hamid.template.databinding.DummyRecycleBinding
import com.hamid.template.databinding.RecycleAllClientBinding
import com.hamid.template.databinding.RecycleVisitBinding
import com.hamid.template.ui.dashboard.models.DummyModel
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
import com.hamid.template.utils.getRandomColor

class VisitsClientsAdopter(val mContext: Context, val edcationsList: ArrayList<TodayTripResponse.Data.Down.Client>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)
    private var mClickListener: VisitsAdopter.ItemClickListener? = null
    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecycleVisitView(RecycleAllClientBinding.inflate(mInflater, parent, false))
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RecycleVisitView) {
            FillEducation(holder.binding, edcationsList[position], position)
        }
    }
    private fun FillEducation(binding: RecycleAllClientBinding, allOffers: TodayTripResponse.Data.Down.Client, position: Int) {
        binding.clientName.text = allOffers.name
        binding.parents.text = allOffers.parentName
        binding.Age.text = allOffers.age
        binding.phone.text = allOffers.phone
        binding.location.text = allOffers.fullAddress
        binding.Gender.text = allOffers.gender
        binding.statusName.text=allOffers.scheduleStatusName
          val allFilters=allOffers.transportationFilterName
          val allIds=allOffers.transportationFilterID
          for (i in allFilters.indices){
              val chip = Chip(ContextThemeWrapper(mContext,R.style.MaterialComponents_Chip_Thin),null,0)
              chip.text=allFilters[i]
              chip.labelFor=allIds[i]
              chip.getRandomColor()
              binding.chipsInput.addView(chip)
          }
        binding.map.setOnClickListener {
            mClickListener?.onMapLicked(allOffers)
        }
       }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return edcationsList.size
    }

    class RecycleVisitView internal constructor(val binding: RecycleAllClientBinding) :
        RecyclerView.ViewHolder(
            binding.root
        )

    fun setClickListener(itemClickListener: VisitsAdopter.ItemClickListener?) {
        mClickListener = itemClickListener
    }



    fun insertItems(buttonsModel: TodayTripResponse.Data.Down.Client) {
        this.edcationsList.add(buttonsModel)
        notifyItemInserted(itemCount - 1)
    }

    fun deleteAllItems() {
        val size = edcationsList.size
        edcationsList.clear()
        notifyItemRangeRemoved(0, size)
    }

    fun getButtonAt(position: Int): TodayTripResponse.Data.Down.Client {
        return edcationsList[position]
    }

    fun UpdateAll(arrayList: List<TodayTripResponse.Data.Down.Client>) {
        edcationsList.clear()
        edcationsList.addAll(arrayList)
        notifyDataSetChanged()
    }



}