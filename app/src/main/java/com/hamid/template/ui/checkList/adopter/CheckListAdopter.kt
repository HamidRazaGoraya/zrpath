package com.hamid.template.ui.checkList.adopter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hamid.template.databinding.RecycleCheckBoxBinding
import com.hamid.template.ui.checkList.viewHolder.CheckListViewHolder
import com.hamid.template.ui.mapScreen.models.UserCheckListResponse

class CheckListAdopter : RecyclerView.Adapter<CheckListViewHolder>() {

    private lateinit var binding: RecycleCheckBoxBinding

    var data: MutableList<UserCheckListResponse.Data.Check>? = arrayListOf()


    fun updateItems(newItems: List<UserCheckListResponse.Data.Check>?) {
        val oldItems = ArrayList(this.data!!)
        this.data!!.clear()
        if (newItems != null) {
            this.data!!.addAll(newItems)
        }
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return oldItems.size
            }

            override fun getNewListSize(): Int {
                return data!!.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldItems[oldItemPosition] == newItems!![newItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldItems[oldItemPosition] == newItems!![newItemPosition]
            }
        }).dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = RecycleCheckBoxBinding.inflate(inflater, parent, false)
        return CheckListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CheckListViewHolder, position: Int) {
        data?.get(position)?.let {
            holder.setData(it)
        }
    }


    override fun getItemCount(): Int = (data?.size) ?: 0

}