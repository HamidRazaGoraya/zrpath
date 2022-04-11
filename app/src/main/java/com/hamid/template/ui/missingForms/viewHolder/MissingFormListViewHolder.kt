package com.hamid.template.ui.missingForms.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hamid.template.databinding.RowMapFormsBinding
import com.hamid.template.databinding.RowSectionListBinding
import com.hamid.template.databinding.RowSectionListMissingBinding
import com.hamid.template.databinding.RowTabVisitsBinding
import com.hamid.template.ui.dashboard.models.ResponseDashBoard
import com.hamid.template.ui.mapScreen.models.ResponseDocumentList
import com.hamid.template.ui.missingForms.eventListners.FormClicked
import com.hamid.template.ui.missingForms.eventListners.MissingFormClicked
import com.hamid.template.ui.missingForms.model.ResponseMissingDocument
import com.hamid.template.ui.missingForms.model.ResponseUserMissingDocument
import com.hamid.template.ui.todayTripsList.models.ResponseReferralList
import com.hamid.template.utils.GetImageName
import com.hamid.template.utils.dialogs.eventsListners.AllFormsListners
import com.hamid.template.utils.dialogs.eventsListners.OnRefferalClicked
import com.hamid.template.utils.getRandomColor
import com.hamid.template.utils.setRandomTint

class MissingFormListViewHolder(var binding: RowSectionListMissingBinding, var formClicked: MissingFormClicked) :
    RecyclerView.ViewHolder(binding.root) {

    fun setData(item: ResponseUserMissingDocument.DataItem) {
        binding.tvSection.text=item.missingDocumentName
        binding.root.setOnClickListener {
            formClicked.openClicked(item)
        }
        binding.llEdit.setOnClickListener {
            formClicked.editClicked(item)
        }
        binding.llDelete.setOnClickListener {
            formClicked.deleteClicked(item)
        }
        binding.ivSection.setRandomTint()
    }

}