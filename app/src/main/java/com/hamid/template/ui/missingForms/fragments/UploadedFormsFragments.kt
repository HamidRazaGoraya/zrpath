package com.hamid.template.ui.missingForms.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.gson.Gson
import com.hamid.template.base.BaseFragment
import com.hamid.template.databinding.UploadedFormsBinding
import com.hamid.template.ui.fillForm.OpenFormActivity
import com.hamid.template.ui.fillForm.model.RequestDeleteDocument
import com.hamid.template.ui.fillForm.model.RequestSavedOpenForm
import com.hamid.template.ui.medicationFormsList.model.ResponseMedicationFormsList
import com.hamid.template.ui.missingForms.MissingFormsVM
import com.hamid.template.ui.missingForms.adopter.UploadedFormsAdopter
import com.hamid.template.ui.missingForms.eventListners.FormClicked
import com.hamid.template.ui.missingForms.model.RequestMissingDocument
import com.hamid.template.ui.missingForms.model.ResponseMissingDocument
import com.hamid.template.utils.*
import com.hamid.template.utils.dialogs.ConfirmDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.collections.ArrayList


@AndroidEntryPoint
class UploadedFormsFragments : BaseFragment<UploadedFormsBinding, MissingFormsVM>() {

    override val viewModel: MissingFormsVM by activityViewModels()

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    lateinit var uploadedFormsAdopter: UploadedFormsAdopter

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
        uploadedFormsAdopter= UploadedFormsAdopter(object : FormClicked {
            override fun openClicked(item: ResponseMissingDocument.DataItem) {
                moveToOpenFormActivity(item)
            }

            override fun editClicked(item: ResponseMissingDocument.DataItem) {

            }

            override fun deleteClicked(item: ResponseMissingDocument.DataItem) {
                showDeleteConfirm(item)
            }
        })
        binding.missingFormsRecycle.adapter=uploadedFormsAdopter

        binding.swipe.setOnRefreshListener {
            binding.swipe.isRefreshing=true
            loadFilledForms()
        }
        loadFilledForms()
    }

    fun moveToOpenFormActivity(item: ResponseMissingDocument.DataItem) {
        viewModel.openSavedForm(RequestSavedOpenForm.Data(item.referralDocumentID,item.referralID)).observe(viewLifecycleOwner){
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.swipe.isRefreshing=false
                    viewModel.HideLoading()
                    Log.i("status","here0")
                    it.data?.let { data->
                        val bundle=Bundle()
                        Log.i("status","here1")
                        data.isInternal=item.kindOfDocument.equals("Internal")
                        bundle.putString(Constants.data, Gson().toJson(data))
                        bundle.putString(Constants.client,Gson().toJson(viewModel.client))
                        bundle.putString(Constants.visitDetails,Gson().toJson(viewModel.visitdetails))
                        viewModel.openSavedFormActivity(bundle)
                     }
                }
                Resource.Status.ERROR -> {
                    Log.i("status","here2")
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
    fun loadFilledForms() {
        viewModel.GetMissingDocumentList(RequestMissingDocument.Data(viewModel.visitdetails.data!!.transportVisitID,sharedPreferenceManager.getEmployID())).observe(viewLifecycleOwner){
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.swipe.isRefreshing=false
                    viewModel.HideLoading()
                    it.data?.let { data->
                        data.data?.let { formsList->
                            uploadedFormsAdopter.updateItems(formsList)
                            binding.NoDataFound.setShowCondition(formsList.isNullOrEmpty())
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

    fun showDeleteConfirm(item: ResponseMissingDocument.DataItem){
        ConfirmDialog({
            viewModel.deleteDocument(RequestDeleteDocument.Data(item.referralDocumentID,sharedPreferenceManager.getEmployID())).observe(viewLifecycleOwner){
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        loadFilledForms()
                        viewModel.HideLoading()

                    }
                    Resource.Status.ERROR -> {
                        loadFilledForms()
                        viewModel.HideLoading()
                        it.message?.let { it1 -> showSnackBar(it1) }
                    }
                    Resource.Status.LOADING -> {
                        viewModel.ShowLoading()
                    }
                }
            }
        },"File will be deleted please confirm.").show(childFragmentManager,"Confirm")
    }
}