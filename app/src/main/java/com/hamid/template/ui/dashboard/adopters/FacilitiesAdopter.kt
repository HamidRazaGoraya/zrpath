package com.hamid.template.ui.dashboard.adopters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hamid.template.databinding.FacilitiesRecycleBinding
import com.hamid.template.ui.dashboard.models.AllFacilitiesModel

class FacilitiesAdopter(val mContext: Context, val allFacilities: ArrayList<AllFacilitiesModel.Data>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)
    private var mClickListener: ItemClickListener? = null
    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AllFacilitiesView(FacilitiesRecycleBinding.inflate(mInflater, parent, false))
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        if (holder is AllFacilitiesView) {
            fillAllFacilities(holder.binding, allFacilities[position], position)
        }
    }
    private fun fillAllFacilities(binding: FacilitiesRecycleBinding, allFacilities: AllFacilitiesModel.Data, position: Int) {
              binding.facilityName.text=allFacilities.facilityName
        binding.root.setOnClickListener {
            mClickListener?.onItemClicked(allFacilities)
        }
       }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return allFacilities.size
    }

    class AllFacilitiesView internal constructor(val binding: FacilitiesRecycleBinding) :
        RecyclerView.ViewHolder(
            binding.root
        )

    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClicked(product: AllFacilitiesModel.Data)

    }

    fun insertItems(buttonsModel: AllFacilitiesModel.Data) {
        this.allFacilities.add(buttonsModel)
        notifyItemInserted(itemCount - 1)
    }

    fun deleteAllItems() {
        val size = allFacilities.size
        allFacilities.clear()
        notifyItemRangeRemoved(0, size)
    }

    fun getButtonAt(position: Int): AllFacilitiesModel.Data {
        return allFacilities[position]
    }

    fun UpdateAll(arrayList: List<AllFacilitiesModel.Data>) {
        allFacilities.clear()
        allFacilities.addAll(arrayList)
        notifyDataSetChanged()
    }



}