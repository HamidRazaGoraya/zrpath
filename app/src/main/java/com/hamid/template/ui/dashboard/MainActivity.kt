package com.hamid.template.ui.dashboard

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.DocumentsContract
import android.view.LayoutInflater
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.navigation.Navigation
import com.hamid.template.BuildConfig
import com.hamid.template.R
import com.hamid.template.base.BaseActivity
import com.hamid.template.databinding.ActivityMainBinding
import com.hamid.template.ui.dashboard.fragments.HomeFragment
import com.hamid.template.ui.facilitiesPatiensts.FacilityActivity
import com.hamid.template.ui.loginAndRegister.RegisterActivity
import com.hamid.template.ui.todayTripsList.TodayTripActivity
import com.hamid.template.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainVM>(), MainContracts {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        viewModel.viewInteractor = this
        viewModel.setData()
    }

    override val viewModel: MainVM by viewModels()

    @Override
    override fun setBinding(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }



    @Override
    override fun initiate() {
        binding.navigationView.version.text="Version ${BuildConfig.VERSION_NAME}"
        window.setNavigationBarColor(resources.getColor(R.color.white))
        window.statusBarColor=resources.getColor(R.color.primary_two)
        val  sidemenu=binding.navigationView
        sidemenu.closeNavigation.setOnClickListener {
            viewModel.hideSideMenu()
        }
        viewModel.logInResponse=sharedPreferenceManager.UserLogInResponse
        viewModel.logInResponse?.let {
            sidemenu.userName.text="${it.data.employee.firstName.CheckForNotNull()} ${it.data.employee.lastName.CheckForNotNull()}"
        }
        sidemenu.logOut.setOnClickListener {
            viewModel.hideSideMenu()
            viewModel.logOutClicked()
        }
        sidemenu.Patient.setOnClickListener {
            viewModel.hideSideMenu()
            viewModel.moveToTodayTrip()
        }
        sidemenu.map.setOnClickListener {
            viewModel.hideSideMenu()
            viewModel.mapsClicked()
        }
        sidemenu.Settings.setOnClickListener {
            viewModel.hideSideMenu()
            viewModel.settingsClicked()
        }
        sidemenu.help.setOnClickListener {
            viewModel.hideSideMenu()
            viewModel.helpClicked()
        }

    }

    override fun ShowLoading() {
        showLoader()
    }

    override fun HideLoading() {
        hideLoader()
    }

    override fun showSideMenu() {
        toggleLeftDrawer(true)
    }

    override fun hideSideMenu() {
        toggleLeftDrawer(false)
    }
    private fun toggleLeftDrawer(boolean: Boolean) {
        if (boolean){
            if (!binding.drawerLayout.isDrawerOpen(binding.slider)) {
                binding.drawerLayout.openDrawer(binding.slider)
            }
        }
        else{
            if (binding.drawerLayout.isDrawerOpen(binding.slider)) {
                binding.drawerLayout.closeDrawer(binding.slider)
            }
        }
    }

    override fun logOutClicked() {
        sharedPreferenceManager.deleteAll()
        startActivity(RegisterActivity.getIntent(this))
        finishAffinity()
    }



    override fun mapsClicked() {
        viewModel.hideSideMenu()
        Navigation.findNavController(this, R.id.fragmentDashboard).navigate(R.id.action_home_to_Maps)

    }

    override fun settingsClicked() {
        viewModel.hideSideMenu()
        Navigation.findNavController(this, R.id.fragmentDashboard).navigate(R.id.action_home_to_Settings)
    }

    override fun helpClicked() {
        viewModel.hideSideMenu()
       // Navigation.findNavController(this, R.id.fragmentDashboard).navigate(R.id.action_home_to_help)

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"
        }
        getFile.launch(intent)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(binding.slider)) {
            binding.drawerLayout.closeDrawer(binding.slider)
        } else {
            super.onBackPressed()
        }
    }

    override fun notificationClicked() {
        showSnackBar("Notification")
    }

    override fun searchClicked() {
        showSnackBar("SearchClicked")
    }

    override fun onButtonBackPressed() {
        super.onBackPressed()
    }

    override fun onFacilitySelected(bundle: Bundle) {
        viewModel.hideSideMenu()
       startActivity(FacilityActivity.getIntent(this).putExtras(bundle))
    }
    override fun patientClicked() {

        viewModel.hideSideMenu()
        Navigation.findNavController(this, R.id.fragmentDashboard).navigate(R.id.action_home_to_facilities)

    }

    override fun moveToTodayTrip() {
        todayTripResponse.launch(TodayTripActivity.getIntent(this))
    }

    val todayTripResponse = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            when(result.data!!.getIntExtra(Constants.actionKey,0)){
                1->{
                    viewModel.patientClicked()
                }
            }
        }

    }
    val getFile = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.run {
             FileManager.getPath(this@MainActivity,this)?.run {
                 File(this).run {
                     showSnackBar(name)
                 }
             }
            }
        }

    }
}