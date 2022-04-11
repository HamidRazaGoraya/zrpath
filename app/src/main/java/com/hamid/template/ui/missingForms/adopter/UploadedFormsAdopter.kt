package com.hamid.template.ui.missingForms.adopter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hamid.template.databinding.RowSectionListBinding
import com.hamid.template.ui.missingForms.eventListners.FormClicked
import com.hamid.template.ui.missingForms.model.ResponseMissingDocument
import com.hamid.template.ui.missingForms.viewHolder.FormListViewHolder

class UploadedFormsAdopter(val formClicked: FormClicked) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    var data: MutableList<ResponseMissingDocument.DataItem> = arrayListOf()
    var newList: MutableList<ResponseMissingDocument.DataItem> = arrayListOf()


    fun updateItems(newItems: List<ResponseMissingDocument.DataItem>) {
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
        return FormListViewHolder(RowSectionListBinding.inflate(inflater, parent, false),formClicked)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FormListViewHolder){
            holder.setData(data.get(position))
        }

    }



    override fun getItemCount(): Int = (data.size) ?: 0





}