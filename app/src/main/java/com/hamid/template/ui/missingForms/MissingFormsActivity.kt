package com.hamid.template.ui.missingForms

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.hamid.template.R
import com.hamid.template.base.BaseActivity
import com.hamid.template.databinding.ActivityMissingFormsBinding
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
import com.hamid.template.ui.fillForm.FillFormActivity
import com.hamid.template.ui.fillForm.model.RequestSavedOpenForm
import com.hamid.template.ui.mapScreen.models.RequestDocumentUrl
import com.hamid.template.ui.mapScreen.models.ResponseDocumentList
import com.hamid.template.ui.mapScreen.models.ResponseTripDetails
import com.hamid.template.ui.missingForms.fragments.MissingFormsFragments
import com.hamid.template.ui.missingForms.fragments.UploadedFormsFragments
import com.hamid.template.ui.missingForms.model.ResponseMissingDocument
import com.hamid.template.utils.*
import com.hamid.template.utils.dialogs.AllFormsList
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MissingFormsActivity : BaseActivity<ActivityMissingFormsBinding, MissingFormsVM>(), MissingFormsContracts {

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager




    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MissingFormsActivity::class.java)
        }
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        viewModel.viewInteractor = this
        viewModel.client= Gson().fromJson(intent.getStringExtra(Constants.client),TodayTripResponse.Data.Down.Client::class.java)
        viewModel.visitdetails=Gson().fromJson(intent.getStringExtra(Constants.visitDetails),ResponseTripDetails::class.java)
        viewModel.initThings(viewModel.client)
        binding.toolbar.setNavigationOnClickListener {
            val bundle=Bundle()
            bundle.putInt(Constants.actionKey,1)
            sendFinishBundle(bundle)
        }



    }




    override val viewModel: MissingFormsVM by viewModels()

    @Override
    override fun setBinding(layoutInflater: LayoutInflater): ActivityMissingFormsBinding {
        return ActivityMissingFormsBinding.inflate(layoutInflater)
    }

    override fun setData() {
        window.setNavigationBarColor(resources.getColor(R.color.white))
        window.statusBarColor=resources.getColor(R.color.primary_two)

    }

    override fun onButtonBackPressed() {
        sendFinishBundle(Bundle())
    }

    override fun ShowLoading() {
       showLoader()
    }

    override fun HideLoading() {

        hideLoader()
    }



    override fun getFormsList(clicked: Boolean, client: TodayTripResponse.Data.Down.Client) {
        viewModel.getDocumentList().observe(this){
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    viewModel.HideLoading()
                    it.data?.let { it1 ->
                        viewModel.responseDocumentList=it1
                        if (clicked){
                            viewModel.showSelectFormDialog(viewModel.responseDocumentList!!, client)
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    viewModel.HideLoading()
                    it.message?.let { it1 -> showSnackBar(it1) }
                }
                Resource.Status.LOADING -> {
                }
            }
        }
    }

    override fun showSelectFormDialog(documentList: ResponseDocumentList, client: TodayTripResponse.Data.Down.Client) {
        AllFormsList(object : AllFormsList.Buttons{
            override fun CareTypeClicked(careTypeId: ResponseDocumentList.DataItem) {
                viewModel.apiCallForUrl(careTypeId,client)
            }
        },documentList.data!!).show(supportFragmentManager,"ChooseForm")
    }

    override fun apiCallForUrl(form: ResponseDocumentList.DataItem, client: TodayTripResponse.Data.Down.Client) {
        val data= RequestDocumentUrl.Data(true,client.ReferralID,form.eBFormID)
        viewModel.getDocumentUrl(data).observe(this){
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    viewModel.HideLoading()
                    it.data?.let { it1 ->
                        val bundle=Bundle()
                        bundle.putString(Constants.data,Gson().toJson(it1))
                        bundle.putString(Constants.form,Gson().toJson(form))
                        bundle.putString(Constants.client,Gson().toJson(client))
                        startActivity(FillFormActivity.getIntent(this).putExtras(bundle))
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



    override fun openClicked(item: ResponseMissingDocument.DataItem) {
             viewModel.openSavedForm(RequestSavedOpenForm.Data(item.referralDocumentID,item.referralID)).observe(this){
                 when (it.status) {
                     Resource.Status.SUCCESS -> {
                         viewModel.HideLoading()
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

    override fun editClicked(item: ResponseMissingDocument.DataItem) {

    }

    override fun deleteClicked(item: ResponseMissingDocument.DataItem) {

    }
    var uploadedFormsFragments:UploadedFormsFragments= UploadedFormsFragments()
    var missingFormsFragments:MissingFormsFragments= MissingFormsFragments()
    var current:Fragment?=null
    override fun setUpTabLayout() {
        val  fragmentManager = supportFragmentManager
        var oldFragment = fragmentManager.findFragmentByTag("uploadedFormsFragments")
        if (oldFragment != null) {
            fragmentManager.beginTransaction().remove(oldFragment).commit()
        }
        oldFragment = fragmentManager.findFragmentByTag("missingFormsFragments")
        if (oldFragment != null) {
            fragmentManager.beginTransaction().remove(oldFragment).commit()
        }
        fragmentManager.beginTransaction().add(R.id.fragments, uploadedFormsFragments, "uploadedFormsFragments").hide(uploadedFormsFragments).commit()
        fragmentManager.beginTransaction().add(R.id.fragments, missingFormsFragments, "missingFormsFragments").commit()
        current=missingFormsFragments
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (binding.tabLayout.selectedTabPosition==0){

                    fragmentManager.beginTransaction().hide(current!!).show(missingFormsFragments).commit()
                    current=missingFormsFragments
                }else{

                    fragmentManager.beginTransaction().hide(current!!).show(uploadedFormsFragments).commit()
                    current=uploadedFormsFragments
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }
}