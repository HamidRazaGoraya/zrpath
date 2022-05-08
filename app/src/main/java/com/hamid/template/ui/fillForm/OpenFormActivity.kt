package com.hamid.template.ui.fillForm

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.webkit.*
import androidx.activity.viewModels
import com.google.gson.Gson
import com.hamid.template.R
import com.hamid.template.base.BaseActivity
import com.hamid.template.databinding.ActivityFillFormBinding
import com.hamid.template.ui.facilitiesPatiensts.models.RequestSaveCheck
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
import com.hamid.template.ui.fillForm.model.RequestSaveForm
import com.hamid.template.ui.mapScreen.models.ResponseDocumentUrl
import com.hamid.template.ui.mapScreen.models.ResponseTripDetails
import com.hamid.template.ui.mapScreen.models.UserCheckListResponse
import com.hamid.template.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OpenFormActivity : BaseActivity<ActivityFillFormBinding, FillFormVM>(), FillFormContracts {

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    private lateinit var url: ResponseDocumentUrl

    lateinit var client: TodayTripResponse.Data.Down.Client

     var isfromCheckList: UserCheckListResponse.Data.CheckListItem?=null
     var visitdetails: ResponseTripDetails?=null


    private var onDownloadComplete: BroadcastReceiver? = null


    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, OpenFormActivity::class.java)
        }
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        viewModel.viewInteractor = this
        client= Gson().fromJson(intent.getStringExtra(Constants.client),TodayTripResponse.Data.Down.Client::class.java)
        url= Gson().fromJson(intent.getStringExtra(Constants.data), ResponseDocumentUrl::class.java)
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

        val dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager

        if (!TextUtils.isEmpty(url.data)) {
            binding.webview.getSettings().setJavaScriptEnabled(true)
            binding.webview.addJavascriptInterface(MyJavaScriptInterface(viewModel), "Android")
            binding.webview.getSettings().setBuiltInZoomControls(true)
            binding.webview.setWebViewClient(WebViewClient())
            binding.webview.setWebChromeClient(object : WebChromeClient() {
                override fun onProgressChanged(view: WebView, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    binding.pbTest.setProgress(newProgress)
                    if (newProgress == 100) {
                        binding.llProgressBlogDetail.setVisibility(View.GONE)
                        binding.webview.setVisibility(View.VISIBLE)
                    }
                }
            })
            binding.webview.getSettings().setLoadWithOverviewMode(true)
            binding.webview.getSettings().setUseWideViewPort(false)
            binding.webview.getSettings().setSupportZoom(true)
            binding.webview.getSettings().setBuiltInZoomControls(true)
            binding.webview.getSettings().setDisplayZoomControls(false)
            binding.webview.getSettings().setPluginState(WebSettings.PluginState.ON)
            binding.webview.setWebViewClient(AppWebViewClients(this,binding.webview))
            if (url.data.contains(".")) {
                val extension = url.data.substring(url.data.lastIndexOf("."))
                if (extension == ".jpeg" || extension == ".jpg" || extension == ".png" || extension == ".PNG") {
                    binding.webview.loadUrl(url.data)
                } else if (extension == ".doc" || extension == ".docx" || extension == ".xls" || extension == ".xlsx" || extension == ".xlt" || extension == ".xlw" || extension == ".pdf" || extension == ".txt") {
                    binding.webview.loadUrl("http://docs.google.com/gview?embedded=true&url=${url.data}")
                } else {
                    if (!url.isInternal) {
                        url.data = url.data + "Android"
                    }
                    binding.webview.loadUrl(url.data)
                }
            } else {
                if (!url.isInternal) {
                    url.data = url.data + "Android"
                }
                binding.webview.loadUrl(url.data)
            }
            onDownloadComplete = object : BroadcastReceiver() {
                override fun onReceive(ctxt: Context, intent: Intent) {
                    val extras = intent.extras
                    val q = DownloadManager.Query()
                    q.setFilterById(extras!!.getLong(DownloadManager.EXTRA_DOWNLOAD_ID))
                    val c = dm.query(q)
                    if (c.moveToFirst()) {
                        val status = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS))
                        if (status == DownloadManager.STATUS_SUCCESSFUL) {
                            // process download
                            val title = c.getString(c.getColumnIndexOrThrow(DownloadManager.COLUMN_TITLE))
                            showSnackBar("Downloaded")
                            viewModel.HideLoading()
                            finish()
                            // get other required data by changing the constant passed to getColumnIndex
                        }
                    }
                }
            }
            registerReceiver(
                onDownloadComplete,
                IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
            )
            binding.webview.setDownloadListener(DownloadListener { url, userAgent, contentDisposition, mimeType, contentLength ->
                viewModel.ShowLoading()
                val request = DownloadManager.Request(
                    Uri.parse(url)
                )
                request.setMimeType(mimeType)
                val cookies = CookieManager.getInstance().getCookie(url)
                request.addRequestHeader("cookie", cookies)
                request.addRequestHeader("User-Agent", userAgent)
                request.setDescription("Downloading file...")
                request.setTitle(
                    URLUtil.guessFileName(
                        url, contentDisposition,
                        mimeType
                    )
                )
                request.allowScanningByMediaScanner()
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                request.setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(
                        url, contentDisposition, mimeType
                    )
                )
                dm.enqueue(request)
            }
            )
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
        finish()

    }

    override fun checkListSave(checK: UserCheckListResponse.Data.CheckListItem,html: String) {
        viewModel.checkCheckList(RequestSaveCheck.Data("",checK.CheckListID,visitdetails!!.data!!.transportVisitID,sharedPreferenceManager.getEmployID(),checK.transportVisitNoteID,html)).observe(this){
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

           if (isfromCheckList==null){
               val intent = Intent()
               intent.putExtra(Constants.WEBVIEW_FORM_DATA, url.data)
               setResult(RESULT_OK, intent)
               finish()
           }else{
               viewModel.checkListSave(isfromCheckList!!,url.data)
           }
       }
    }



    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(onDownloadComplete)
    }

    class AppWebViewClients(var context: Context,var webView: WebView) : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            val manager = context.getSystemService(WINDOW_SERVICE) as WindowManager
            val metrics = DisplayMetrics()
            manager.defaultDisplay.getMetrics(metrics)
            metrics.widthPixels /= metrics.density.toInt()
            webView.loadUrl("javascript:document.body.style.zoom = " + 1.toString() + ";")
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