package com.hamid.template.ui.missingForms.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.hamid.template.base.BaseFragment
import com.hamid.template.databinding.UploadedFormsBinding
import com.hamid.template.ui.missingForms.MissingFormsVM
import com.hamid.template.ui.missingForms.adopter.MissingFormsAdopter
import com.hamid.template.ui.missingForms.adopter.UploadedFormsAdopter
import com.hamid.template.ui.missingForms.eventListners.FormClicked
import com.hamid.template.ui.missingForms.eventListners.MissingFormClicked
import com.hamid.template.ui.missingForms.model.RequestMissingDocument
import com.hamid.template.ui.missingForms.model.RequestUserMissingDocument
import com.hamid.template.ui.missingForms.model.ResponseMissingDocument
import com.hamid.template.ui.missingForms.model.ResponseUserMissingDocument
import com.hamid.template.utils.*
import com.hamid.template.utils.dialogs.SelectUploadedDocument
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MissingFormsFragments : BaseFragment<UploadedFormsBinding, MissingFormsVM>() {

    override val viewModel: MissingFormsVM by activityViewModels()

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    lateinit var uploadedFormsAdopter: MissingFormsAdopter

    override fun setBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): UploadedFormsBinding {
        return UploadedFormsBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       setOnClickEvents()
    }

    private fun setOnClickEvents() {
        binding.addNewForm.setOnClickListener {
            if (viewModel.responseDocumentList!=null){
                viewModel.showSelectFormDialog(viewModel.responseDocumentList!!,viewModel.client)
            }else{
                viewModel.ShowLoading()
                viewModel.getFormsList(true,viewModel.client)
            }
        }
        binding.addNewForm.makeGone()
        uploadedFormsAdopter= MissingFormsAdopter(object : MissingFormClicked {
            override fun openClicked(item: ResponseUserMissingDocument.DataItem) {
                SelectUploadedDocument(object :SelectUploadedDocument.Buttons{
                    override fun Upload() {

                    }

                    override fun AddNew() {

                    }
                }).show(childFragmentManager,"SelectUploadedDocument")
            }

            override fun editClicked(item: ResponseUserMissingDocument.DataItem) {

            }

            override fun deleteClicked(item: ResponseUserMissingDocument.DataItem) {

            }
        })
        binding.missingFormsRecycle.adapter=uploadedFormsAdopter

        binding.swipe.setOnRefreshListener {
            binding.swipe.isRefreshing=true
            loadFilledForms()
        }
        loadFilledForms()
    }

    fun loadFilledForms() {
        viewModel.SetMissingDocument(RequestUserMissingDocument.Data(viewModel.client.ReferralID)).observe(viewLifecycleOwner){
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.swipe.isRefreshing=false
                    viewModel.HideLoading()
                    it.data?.let { data->
                        data.data?.let { formsList->

                            uploadedFormsAdopter.updateItems(formsList)
                        }
                    }

                }
                Resource.Status.ERROR -> {
                    binding.swipe.isRefreshing=false
                    viewModel.HideLoading()
                    it.message?.let { it1 -> showSnackBar(it1) }
                }
                Resource.Status.LOADING -> {
                    viewModel.ShowLoading()
                }
            }
        }
    }

}