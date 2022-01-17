package com.hamid.template.ui.facilitiesPatiensts

import com.hamid.template.base.BaseViewModel
import com.hamid.template.network.ApiRepository
import com.hamid.template.ui.dashboard.models.AllFacilitiesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FacilitiyVM @Inject
constructor(
    private val apiRepository: ApiRepository,
) : BaseViewModel<FacilityContracts>() {

    fun initThings() {
        viewInteractor?.setData()
    }
    lateinit var facility: AllFacilitiesModel.Data
    var tripType:String?=null
    fun showPatientsList()=viewInteractor?.showPatientsList()
    fun onButtonBackPressed()=viewInteractor?.onButtonBackPressed()

}