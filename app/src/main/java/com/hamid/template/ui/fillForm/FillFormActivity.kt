package com.hamid.template.ui.fillForm

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.viewModels
import com.google.gson.Gson
import com.hamid.template.base.BaseActivity
import com.hamid.template.databinding.ActivityFillFormBinding
import com.hamid.template.ui.mapScreen.models.ResponseDocumentUrl
import com.hamid.template.utils.Constants
import com.hamid.template.utils.SharedPreferenceManager
import com.hamid.template.utils.makeGone
import com.hamid.template.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FillFormActivity : BaseActivity<ActivityFillFormBinding, FillFormVM>(), FillFormContracts {

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    private lateinit var responseDocumentUrl: ResponseDocumentUrl




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
        responseDocumentUrl= Gson().fromJson(intent.getStringExtra(Constants.data), ResponseDocumentUrl::class.java)
        viewModel.initThings()

    }




    override val viewModel: FillFormVM by viewModels()

    @Override
    override fun setBinding(layoutInflater: LayoutInflater): ActivityFillFormBinding {
        return ActivityFillFormBinding.inflate(layoutInflater)
    }

    override fun setData() {
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

    override fun saveAndFinish(html: String) {
        val intent = Intent()
        intent.putExtra(Constants.WEBVIEW_FORM_DATA, html)
        setResult(RESULT_OK, intent)
        finish()
    }

    private class MyJavaScriptInterface(var fillFormVM: FillFormVM) {

        @JavascriptInterface
        fun shareData(html: String) {
            if (!fillFormVM.isDataLoaded) {
                if (!TextUtils.isEmpty(html)) {
                    fillFormVM.isDataLoaded = true
                    fillFormVM.saveAndFinish(html)
                }
            }
        }
    }

}