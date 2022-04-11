package com.hamid.template.ui.mapScreen

import android.location.Location
import com.google.android.gms.maps.GoogleMap
import com.hamid.template.base.ViewInteractor
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
import com.hamid.template.ui.mapScreen.models.ResponseDocumentList
import com.hamid.template.ui.mapScreen.models.ResponseTripDetails


interface MapContracts : ViewInteractor {
    fun setData()
    fun onButtonBackPressed()
    fun ShowLoading()
    fun HideLoading()
    fun checkForLocationPermission()
    fun checkForLocationService()
    fun moveCameraAt(location: Location,map:GoogleMap)
    fun startMap(location: Location?)
    fun getLastKnownLocation()
    fun permissionMissing()
    fun startLocationUpdate()
    fun stopLocationUpdate()
    fun setUpStartGroupTrip()
    fun setUpEndGroupTrip()
    fun saveAndExit()

    fun childPickUp()
    fun childDrop()
}