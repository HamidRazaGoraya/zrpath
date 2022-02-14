package com.hamid.template.ui.checkList

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.google.gson.Gson
import com.hamid.template.base.BaseActivity
import com.hamid.template.databinding.ActivityCheckListBinding
import com.hamid.template.ui.checkList.adopter.CheckListAdopter
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
import com.hamid.template.ui.mapScreen.models.UserCheckListResponse
import com.hamid.template.utils.Constants
import com.hamid.template.utils.Resource
import com.hamid.template.utils.SharedPreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CheckListActivity : BaseActivity<ActivityCheckListBinding, CheckListVM>(), CheckListContracts {

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    var checkListAdopter=CheckListAdopter()
    lateinit var client:TodayTripResponse.Data.Down.Client

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, CheckListActivity::class.java)
        }
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        viewModel.viewInteractor = this
        client= Gson().fromJson(intent.getStringExtra(Constants.data),TodayTripResponse.Data.Down.Client::class.java)
        viewModel.initThings()

    }




    override val viewModel: CheckListVM by viewModels()

    @Override
    override fun setBinding(layoutInflater: LayoutInflater): ActivityCheckListBinding {
        return ActivityCheckListBinding.inflate(layoutInflater)
    }

    override fun setData() {
       binding.checkRecycle.adapter=checkListAdopter
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

    override fun checkForCheckList() {
        viewModel.getUserCheckList(0,client.ReferralID).observe(this){
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    viewModel.HideLoading()
                    it.data?.let { it1 ->
                        FillCheckList(it1)
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

    private fun FillCheckList(it1: UserCheckListResponse) {
        if (it1.isSuccess){
            it1.data?.let {
                checkListAdopter.updateItems(it.checkList)
            }
        }
    }
}