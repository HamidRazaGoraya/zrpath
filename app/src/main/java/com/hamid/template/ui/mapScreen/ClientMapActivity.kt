package com.hamid.template.ui.mapScreen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.google.gson.Gson
import com.hamid.template.R
import com.hamid.template.base.BaseActivity
import com.hamid.template.databinding.ActivityMapsBinding
import com.hamid.template.ui.facilitiesPatiensts.models.RequestSetTime
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
import com.hamid.template.ui.mapScreen.models.RequestTripClose
import com.hamid.template.ui.mapScreen.models.RequestTripStart
import com.hamid.template.ui.todayTripDetails.models.ResponseOnGoingVisit
import com.hamid.template.utils.Constants
import com.hamid.template.utils.Resource
import com.hamid.template.utils.SharedPreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ClientMapActivity : BaseActivity<ActivityMapsBinding, MapVM>(), MapContracts {

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager
    lateinit var locationManager:LocationManager
    lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    var REQUEST_CHECK_SETTINGS=101
    private var map:GoogleMap?=null

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, ClientMapActivity::class.java)
        }
    }


    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        viewModel.viewInteractor = this
        intent.extras?.let {
            if (it.containsKey(Constants.data)){
                viewModel.group= Gson().fromJson(it.getString(Constants.data), TodayTripResponse.Data.Down.TransportationGroup::class.java)
                viewModel.startTrip=it.getBoolean(Constants.startTrip,true)
            }
            if (it.containsKey(Constants.onGoingVisit)){
                viewModel.onGoingVisit=Gson().fromJson(it.getString(Constants.onGoingVisit),ResponseOnGoingVisit.Data::class.java)
                if (it.getBoolean(Constants.isPickUp)){
                    viewModel.childPickUp()
                }else{
                    viewModel.childDrop()
                }
            }
        }

        viewModel.initThings()
    }

    override val viewModel: MapVM by viewModels()

    @Override
    override fun setBinding(layoutInflater: LayoutInflater): ActivityMapsBinding {
        return ActivityMapsBinding.inflate(layoutInflater)
    }



    @Override
    override fun setData() {
        window.setNavigationBarColor(resources.getColor(R.color.white))
        window.statusBarColor=resources.getColor(R.color.primary_two)
        binding.toolbar.setNavigationOnClickListener {
            viewModel.onButtonBackPressed()
        }
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                for (location in p0.locations){
                    map?.let {
                        viewModel.moveCameraAt(location,it)
                    }
                }
            }
        }
    }



    override fun onButtonBackPressed() {
              finish()
    }


    override fun ShowLoading() {
        showLoader()
    }

    override fun HideLoading() {
        hideLoader()
    }



    override fun checkForLocationPermission() {
              if ((ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)&&(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)){
                  locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
               return
              }
              viewModel.checkForLocationService()
    }


    val locationPermissionRequest = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->

        if ((ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)){
            viewModel.checkForLocationService()
            return@registerForActivityResult
        }

        viewModel.permissionMissing()
    }

    override fun checkForLocationService() {

        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())


        task.addOnSuccessListener { locationSettingsResponse ->
            viewModel.getLastKnownLocation()
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException){
                try {
                    exception.startResolutionForResult(this, REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    viewModel.permissionMissing()
                }
            }
        }
    }

    override fun getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            viewModel.permissionMissing()
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
                viewModel.startMap(location)
            }
    }
    override fun startMap(location: Location?) {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        viewModel.ShowLoading()
        mapFragment.getMapAsync {map->
            this.map=map
            location?.let {
                viewModel.moveCameraAt(it,map)
            }
            enableMyLocation(map)
            viewModel.HideLoading()
            viewModel.startLocationUpdate()
            viewModel.onGoingVisit?.referralDetail?.let {
                //Fill userMarker
                map.addMarker(MarkerOptions().position(LatLng(it.latitude,it.longitude)).title("Child Location"))
            }
        }
    }

    override fun moveCameraAt(location: Location, map: GoogleMap) {
        viewModel.userLocation=location
        if (viewModel.moveCamera){
            viewModel.moveCamera=false
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude,location.longitude),14f))
        }
    }

    override fun permissionMissing() {
        Toast.makeText(this,"Location permission required",Toast.LENGTH_SHORT).show()
        finish()
    }






    @SuppressLint("MissingPermission")
    override fun startLocationUpdate() {

        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    override fun stopLocationUpdate() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }


    override fun onDestroy() {
        super.onDestroy()
        viewModel.stopLocationUpdate()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        viewModel.checkForLocationService()
        super.onActivityResult(requestCode, resultCode, data)
    }
    private fun enableMyLocation(map: GoogleMap) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true
        } else {
            viewModel.checkForLocationPermission()
        }
    }






    override fun saveAndExit() {
        val resultIntent = Intent()
        resultIntent.putExtra("some_key", "String data")
        setResult(RESULT_OK, resultIntent)
        finish()
    }
    override fun setUpStartGroupTrip() {
        binding.startTrip.isEnabled=true
        binding.startTrip.text="Start Trip"
        viewModel.group?.let {group->
            binding.startTrip.setOnClickListener {
                viewModel.TripStart(RequestTripStart.Data(group.transportationGroupID,sharedPreferenceManager.getEmployID(),viewModel.userLocation!!.latitude,viewModel.userLocation!!.longitude)).observe(this){
                    when (it.status) {
                        Resource.Status.SUCCESS -> {
                            viewModel.HideLoading()
                            it.data?.let { it1 ->
                                showSnackBar(it1.message)
                                if (it1.isSuccess){
                                    viewModel.saveAndExit()
                                }
                            }
                        }
                        Resource.Status.ERROR -> {
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

    }
    override fun setUpEndGroupTrip() {
        binding.startTrip.text="End Trip"
        binding.startTrip.isEnabled=true
        viewModel.group?.let{group->
            binding.startTrip.setOnClickListener {
                viewModel.TripClose(RequestTripClose.Data(group.transportationGroupID,sharedPreferenceManager.getEmployID(),viewModel.userLocation!!.latitude,viewModel.userLocation!!.longitude)).observe(this){
                    when (it.status) {
                        Resource.Status.SUCCESS -> {
                            viewModel.HideLoading()
                            it.data?.let { it1 ->
                                showSnackBar(it1.message)
                                if (it1.isSuccess){
                                    viewModel.saveAndExit()
                                }
                            }
                        }
                        Resource.Status.ERROR -> {
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
    }


    override fun childPickUp() {
        binding.startTrip.text="Start trip"
        binding.startTrip.isEnabled=true
        viewModel.onGoingVisit?.onGoingVisitDetail?.let {data->
            binding.startTrip.setOnClickListener {

                viewModel.saveTTime(RequestSetTime.Data(true,1,viewModel.userLocation!!.longitude,data.transportVisitID!!,data.transportationGroupID,viewModel.userLocation!!.latitude,null,null,data.scheduleID,sharedPreferenceManager.getEmployID())).observe(this){
                    when (it.status) {
                        Resource.Status.SUCCESS -> {
                            viewModel.HideLoading()
                            it.data?.let { it1 ->
                                showSnackBar(it1.message)
                                if (it1.isSuccess){
                                    viewModel.saveAndExit()
                                }
                            }
                        }
                        Resource.Status.ERROR -> {
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
    }

    override fun childDrop() {
        binding.startTrip.text="End trip"
        binding.startTrip.isEnabled=true
        viewModel.onGoingVisit?.onGoingVisitDetail?.let{data->
            binding.startTrip.setOnClickListener {
                viewModel.saveTTime(RequestSetTime.Data(true,2,null,data.transportVisitID!!,data.transportationGroupID,null,viewModel.userLocation!!.longitude,viewModel.userLocation!!.longitude,data.scheduleID,sharedPreferenceManager.getEmployID())).observe(this){
                    when (it.status) {
                        Resource.Status.SUCCESS -> {
                            viewModel.HideLoading()
                            it.data?.let { it1 ->
                                showSnackBar(it1.message)
                                if (it1.isSuccess){
                                    viewModel.saveAndExit()
                                }
                            }
                        }
                        Resource.Status.ERROR -> {
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
    }

}