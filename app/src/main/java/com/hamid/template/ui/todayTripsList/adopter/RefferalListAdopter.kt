package com.hamid.template.ui.todayTripsList.adopter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hamid.template.databinding.HeadingVisitsBinding
import com.hamid.template.databinding.RowTabVisitsBinding
import com.hamid.template.ui.dashboard.models.ResponseDashBoard
import com.hamid.template.ui.dashboard.models.VisitListModel
import com.hamid.template.ui.todayTripsList.models.ResponseReferralList
import com.hamid.template.ui.todayTripsList.viewModel.HeadingListViewHolder
import com.hamid.template.ui.todayTripsList.viewModel.RefferalListViewHolder
import com.hamid.template.utils.dialogs.eventsListners.OnRefferalClicked

class RefferalListAdopter(val onRefferalClicked: OnRefferalClicked) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    var data: MutableList<VisitListModel> = arrayListOf()
    var newList: MutableList<VisitListModel> = arrayListOf()


    fun updateItems(newItems: List<VisitListModel>) {
        val oldItems = ArrayList(this.data)
        this.data.clear()
        this.newList.clear()
        this.data.addAll(newItems)
        this.newList.addAll(newItems)
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return oldItems.size
            }

            override fun getNewListSize(): Int {
                return data.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldItems[oldItemPosition] == newItems[newItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldItems[oldItemPosition] == newItems[newItemPosition]
            }
        }).dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (viewType==0){
            return RefferalListViewHolder(RowTabVisitsBinding.inflate(inflater, parent, false),onRefferalClicked)
        }
        return HeadingListViewHolder(HeadingVisitsBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RefferalListViewHolder){
            newList[position].visitItem?.let {
                holder.setData(it)
            }
        }
        if (holder is HeadingListViewHolder){
            newList[position].heading?.let {
                holder.setData(it)
            }
        }
    }



    override fun getItemCount(): Int = (newList.size) ?: 0


    override fun getItemViewType(position: Int): Int {
        return if (newList[position].isHeading){ 1 }else{ 0 }
    }


}