package com.hamid.template.ui.missingForms

import com.hamid.template.base.BaseViewModel
import com.hamid.template.network.ApiRepository
import com.hamid.template.ui.facilitiesPatiensts.models.RequestDeleteCheck
import com.hamid.template.ui.facilitiesPatiensts.models.RequestSaveCheck
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
import com.hamid.template.ui.fillForm.model.RequestDeleteDocument
import com.hamid.template.ui.fillForm.model.RequestSavedDocumentList
import com.hamid.template.ui.fillForm.model.RequestSavedOpenForm
import com.hamid.template.ui.mapScreen.models.*
import com.hamid.template.ui.missingForms.model.RequestMissingDocument
import com.hamid.template.ui.missingForms.model.RequestUserMissingDocument
import com.hamid.template.ui.missingForms.model.ResponseMissingDocument
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MissingFormsVM @Inject
constructor(
    private val apiRepository: ApiRepository,
) : BaseViewModel<MissingFormsContracts>() {

    fun initThings(client:TodayTripResponse.Data.Down.Client) {
        viewInteractor?.setData()
        viewInteractor?.getFormsList(false,client)
        viewInteractor?.setUpTabLayout()
    }
    lateinit var client:TodayTripResponse.Data.Down.Client
    lateinit var visitdetails: ResponseTripDetails

    var responseDocumentList :ResponseDocumentList?=null
    fun onButtonBackPressed()=viewInteractor?.onButtonBackPressed()
    fun ShowLoading()=viewInteractor?.ShowLoading()
    fun HideLoading()=viewInteractor?.HideLoading()

    fun apiCallForUrl(form: ResponseDocumentList.DataItem, client: TodayTripResponse.Data.Down.Client)=viewInteractor?.apiCallForUrl(form,client)
    fun showSelectFormDialog(documentList: ResponseDocumentList, client: TodayTripResponse.Data.Down.Client)=viewInteractor?.showSelectFormDialog(documentList,client)
    fun getFormsList(clicked:Boolean,client: TodayTripResponse.Data.Down.Client)=viewInteractor?.getFormsList(clicked,client)
    fun getDocumentList()=apiRepository.getDocumentList()
    fun getDocumentUrl(data: RequestDocumentUrl.Data)=apiRepository.getDocumentUrl(data)
    fun GetMissingDocumentList(data: RequestMissingDocument.Data)=apiRepository.GetMissingDocumentList(data)


    fun getSavedDocuments(data: RequestSavedDocumentList.Data)=apiRepository.getSavedDocumentsList(data)
    fun openSavedForm(data: RequestSavedOpenForm.Data)=apiRepository.openSavedForm(data)
    fun deleteDocument(data: RequestDeleteDocument.Data)=apiRepository.deleteDocument(data)


    fun openClicked(item: ResponseMissingDocument.DataItem)=viewInteractor?.openClicked(item)
    fun editClicked(item: ResponseMissingDocument.DataItem)=viewInteractor?.editClicked(item)
    fun deleteClicked(item: ResponseMissingDocument.DataItem)=viewInteractor?.deleteClicked(item)

    fun saveUserSignature(TransportVisitIDValue:Int,ReferralIDValue:Int,ScheduleIDValue:Int,file: File)=apiRepository.saveUserSignature(TransportVisitIDValue, ReferralIDValue, ScheduleIDValue, file)

    fun SetMissingDocument(data: RequestUserMissingDocument.Data)=apiRepository.SetMissingDocument(data)
    fun UploadDocument(ReferralIDValue:Int,KindOfDocument:String,file: File)=apiRepository.UploadDocument(ReferralIDValue, KindOfDocument, file)
}