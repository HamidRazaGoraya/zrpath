package com.hamid.template.utils.dialogs.adopter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hamid.template.databinding.RowMapFormsBinding
import com.hamid.template.ui.mapScreen.models.ResponseDocumentList
import com.hamid.template.utils.dialogs.eventsListners.AllFormsListners
import com.hamid.template.utils.dialogs.viewHolder.AllFormsViewHolder

class AllFormsAdopter : RecyclerView.Adapter<AllFormsViewHolder>(), Filterable {

    private lateinit var binding: RowMapFormsBinding

    var data: MutableList<ResponseDocumentList.DataItem>? = arrayListOf()
    var newList: MutableList<ResponseDocumentList.DataItem>? = arrayListOf()
    lateinit var moodBoosterListener: AllFormsListners

    fun updateItems(newItems: List<ResponseDocumentList.DataItem>?) {
        val oldItems = ArrayList(this.data!!)
        this.data!!.clear()
        this.newList!!.clear()
        if (newItems != null) {
            this.data!!.addAll(newItems)
            this.newList!!.addAll(newItems)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllFormsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = RowMapFormsBinding.inflate(inflater, parent, false)
        return AllFormsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllFormsViewHolder, position: Int) {
        newList?.get(position)?.let {
            holder.setData(it,moodBoosterListener)
        }
    }

    fun setListener(moodBoosterListener: AllFormsListners){
        this.moodBoosterListener = moodBoosterListener
    }

    override fun getItemCount(): Int = (newList?.size) ?: 0


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    newList = data as ArrayList<ResponseDocumentList.DataItem>
                } else {
                    val resultList = ArrayList<ResponseDocumentList.DataItem>()
                    for (row in data!!) {
                        if (row.name.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            resultList.add(row)
                        }
                    }
                    newList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = newList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                newList = results?.values as ArrayList<ResponseDocumentList.DataItem>
                notifyDataSetChanged()
            }
        }
    }
}