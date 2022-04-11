package com.hamid.template.ui.fillForm

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.viewModels
import com.google.gson.Gson
import com.hamid.template.R
import com.hamid.template.base.BaseActivity
import com.hamid.template.databinding.ActivityFillFormBinding
import com.hamid.template.ui.facilitiesPatiensts.models.RequestSaveCheck
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
import com.hamid.template.ui.fillForm.model.RequestSaveForm
import com.hamid.template.ui.mapScreen.models.ResponseDocumentList
import com.hamid.template.ui.mapScreen.models.ResponseDocumentUrl
import com.hamid.template.ui.mapScreen.models.ResponseTripDetails
import com.hamid.template.ui.mapScreen.models.UserCheckListResponse
import com.hamid.template.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FillFormActivity : BaseActivity<ActivityFillFormBinding, FillFormVM>(), FillFormContracts {

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    private lateinit var responseDocumentUrl: ResponseDocumentUrl

    lateinit var client: TodayTripResponse.Data.Down.Client

     var form: ResponseDocumentList.DataItem?=null
     var isfromCheckList: UserCheckListResponse.Data.CheckListItem?=null
     var visitdetails: ResponseTripDetails?=null

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, FillFormActivity::class.java)
        }
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        viewModel.viewInteractor = this
        client= Gson().fromJson(intent.getStringExtra(Constants.client),TodayTripResponse.Data.Down.Client::class.java)
        responseDocumentUrl= Gson().fromJson(intent.getStringExtra(Constants.data), ResponseDocumentUrl::class.java)
        form=Gson().fromJson(intent.getStringExtra(Constants.form),ResponseDocumentList.DataItem::class.java)
        if (intent.extras!!.containsKey(Constants.checkList)){
            isfromCheckList=Gson().fromJson(intent.getStringExtra(Constants.checkList),UserCheckListResponse.Data.CheckListItem::class.java)
            visitdetails=Gson().fromJson(intent.getStringExtra(Constants.visitDetails),ResponseTripDetails::class.java)
        }
        viewModel.initThings()

    }




    override val viewModel: FillFormVM by viewModels()

    @Override
    override fun setBinding(layoutInflater: LayoutInflater): ActivityFillFormBinding {
        return ActivityFillFormBinding.inflate(layoutInflater)
    }

    override fun setData() {
        binding.toolbar.setNavigationOnClickListener {
            viewModel.onButtonBackPressed()
        }
        window.setNavigationBarColor(resources.getColor(R.color.white))
        window.statusBarColor=resources.getColor(R.color.primary_two)
        window.setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON)
        binding.llProgressBlogDetail.makeVisible()
        binding.webview.makeGone()
        if (responseDocumentUrl.data.isNotEmpty()) {
            binding.webview.getSettings().setJavaScriptEnabled(true)
            binding.webview.addJavascriptInterface(MyJavaScriptInterface(viewModel), "Android")
            binding.webview.getSettings().setUseWideViewPort(false)
            binding.webview.getSettings().setLoadWithOverviewMode(true)
            binding.webview.getSettings().setSupportZoom(true)
            binding.webview.getSettings().setBuiltInZoomControls(true)
            binding.webview.getSettings().setDisplayZoomControls(false)
            binding.webview.setWebChromeClient(object : WebChromeClient() {
                override fun onProgressChanged(view: WebView, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    binding.pbTest.setProgress(newProgress)
                    if (newProgress == 100) {
                        binding.llProgressBlogDetail.makeGone()
                        binding.webview.makeVisible()
                    }
                }
            })
            binding.webview.loadUrl(responseDocumentUrl.data)

        }
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



    override fun onBackPressed() {
        if (!viewModel.isSaved){
            AlertDialog.Builder(this)
                .setTitle("You have not saved the form.")
                .setPositiveButton(
                    "Save"
                ) { dialog, which -> dialog.dismiss() }
                .setNegativeButton(
                    "Close"
                ) { dialog, which ->
                    dialog.dismiss()
                    setResult(RESULT_CANCELED, null)
                    finish()
                }.show()
        }else{
            finish()
        }

    }

    override fun checkListSave(checK: UserCheckListResponse.Data.CheckListItem,html: String) {
        viewModel.checkCheckList(RequestSaveCheck.Data("",checK.CheckListID,visitdetails!!.data!!.transportVisitID,sharedPreferenceManager.getEmployID(),checK.transportVisitNoteID,html.split(":").toTypedArray().get(1).trim { it <= ' ' })).observe(this){
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    viewModel.HideLoading()
                    it.data?.let { it1 ->
                        val intent = Intent()
                        intent.putExtra(Constants.WEBVIEW_FORM_DATA, html)
                        setResult(RESULT_OK, intent)
                        finish()
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

    override fun saveAndFinish(html: String) {
       runOnUiThread {
           viewModel.requestSaveForm(RequestSaveForm.Data(false,0,client.ReferralID,-1,sharedPreferenceManager.getEmployID(), html.split(":").toTypedArray().get(1).trim { it <= ' ' })).observe(this){
               when (it.status) {
                   Resource.Status.SUCCESS -> {
                       viewModel.HideLoading()
                       it.data?.let { it1 ->
                           if (isfromCheckList==null){
                               val intent = Intent()
                               intent.putExtra(Constants.WEBVIEW_FORM_DATA, html)
                               setResult(RESULT_OK, intent)
                               finish()
                           }else{
                               viewModel.checkListSave(isfromCheckList!!,html)
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

    private class MyJavaScriptInterface(var fillFormVM: FillFormVM) {

        @JavascriptInterface
        fun shareData(html: String) {
            if (!fillFormVM.isDataLoaded) {
                if (!TextUtils.isEmpty(html)) {
                    fillFormVM.isDataLoaded = true
                    Log.i("Data",html)
                    fillFormVM.saveAndFinish(html)
                }
            }
        }
    }

}