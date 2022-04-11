package com.hamid.template.ui.mapScreen

import android.location.Location
import com.google.android.gms.maps.GoogleMap
import com.hamid.template.base.BaseViewModel
import com.hamid.template.network.ApiRepository
import com.hamid.template.ui.facilitiesPatiensts.models.*
import com.hamid.template.ui.mapScreen.models.*
import com.hamid.template.ui.todayTripDetails.models.ResponseOnGoingVisit
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapVM @Inject
constructor(
    private val apiRepository: ApiRepository,
) : BaseViewModel<MapContracts>() {

    fun initThings() {
        viewInteractor?.setData()
        viewInteractor?.checkForLocationPermission()
        group?.let {
           if (startTrip){
               viewInteractor?.setUpStartGroupTrip()
           }else{
               viewInteractor?.setUpEndGroupTrip()
           }
       }
    }
    var userLocation:Location?=null
    var responseDocumentList :ResponseDocumentList?=null
    var moveCamera=true
    var group: TodayTripResponse.Data.Down.TransportationGroup?=null
    var startTrip:Boolean=true
    var onGoingVisit: ResponseOnGoingVisit.Data?=null
    fun onButtonBackPressed()=viewInteractor?.onButtonBackPressed()
    fun ShowLoading()=viewInteractor?.ShowLoading()
    fun HideLoading()=viewInteractor?.HideLoading()
    fun moveCameraAt(location: Location,map: GoogleMap)=viewInteractor?.moveCameraAt(location,map)
    fun checkForLocationService()=viewInteractor?.checkForLocationService()
    fun startMap(location: Location?)=viewInteractor?.startMap(location)
    fun getLastKnownLocation()=viewInteractor?.getLastKnownLocation()
    fun permissionMissing()=viewInteractor?.permissionMissing()
    fun startLocationUpdate()=viewInteractor?.startLocationUpdate()
    fun stopLocationUpdate()=viewInteractor?.stopLocationUpdate()
    fun  checkForLocationPermission()=viewInteractor?.checkForLocationPermission()


    fun getTripDetails(data:RequestTransportDetails.Data)=apiRepository.requestTransportDetails(data)
    fun saveTTime(data: RequestSetTime.Data)=apiRepository.saveTTime(data)

    fun saveAndExit()=viewInteractor?.saveAndExit()


    fun childPickUp()=viewInteractor?.childPickUp()
    fun childDrop()=viewInteractor?.childDrop()

    fun TripStart(data: RequestTripStart.Data)=apiRepository.TripStart(data)
    fun TripClose(data: RequestTripClose.Data)=apiRepository.TripClose(data)


}