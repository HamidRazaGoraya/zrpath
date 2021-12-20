package com.hamid.template.ui.dashboard.adopters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hamid.template.databinding.DummyRecycleBinding
import com.hamid.template.databinding.RecycleVisitBinding
import com.hamid.template.ui.dashboard.models.DummyModel

class VisitsAdopter(val mContext: Context, val edcationsList: ArrayList<DummyModel>) :
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
    private fun FillEducation(binding: RecycleVisitBinding, allOffers: DummyModel, position: Int) {

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
        fun onDeleteClicked(product: DummyModel)
        fun onValueChanged(int: Int)
    }

    fun insertItems(buttonsModel: DummyModel) {
        this.edcationsList.add(buttonsModel)
        notifyItemInserted(itemCount - 1)
    }

    fun deleteAllItems() {
        val size = edcationsList.size
        edcationsList.clear()
        notifyItemRangeRemoved(0, size)
    }

    fun getButtonAt(position: Int): DummyModel {
        return edcationsList[position]
    }

    fun UpdateAll(arrayList: ArrayList<DummyModel>) {
        edcationsList.clear()
        edcationsList.addAll(arrayList)
        notifyDataSetChanged()
    }



}