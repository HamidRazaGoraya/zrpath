package com.hamid.template.base

import android.Manifest
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.hamid.template.R
import com.hamid.template.utils.ConnectionLiveData
import com.hamid.template.utils.LoadingDialog
import com.hamid.template.utils.LocaleHelper
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.util.*

abstract class BaseActivity<B : ViewBinding, VM : ViewModel> : AppCompatActivity() {

    protected lateinit var binding: B

//    protected val viewModel: VM by lazy { ViewModelProvider(this).get(getViewModelClass()) }
    protected abstract val viewModel: VM

    var restart = false

  /*  abstract fun sharePref() :SharedPreferenceManager*/

    lateinit var commonDialog : Dialog

    lateinit var connectionLiveData: ConnectionLiveData
    lateinit var loadingDialog: LoadingDialog
    private lateinit var analytics: FirebaseAnalytics
    abstract fun setBinding(layoutInflater: LayoutInflater): B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setBinding(layoutInflater)
        setContentView(binding.root)
        analytics = Firebase.analytics
        commonDialog = showNoConnectionDialog(this)
        connectionLiveData = ConnectionLiveData(this)
        loadingDialog= LoadingDialog(this, R.style.DialogLoadingTheme)
        netWorkCheck()

    }

    fun showLoader(){
       loadingDialog.show()
    }

    fun hideLoader(){
       loadingDialog.dismiss()
    }

    fun netWorkCheck() {
        connectionLiveData.observe(this,{ isConnected ->
            isConnected?.let {
                showMessage(isConnected)
            }
        })
    }

    private fun showMessage(isConnected: Boolean) {
        if (!isConnected) {
            if (!commonDialog.isShowing){
                commonDialog.show()
            }
        } else {
            if (commonDialog.isShowing){
                commonDialog.dismiss()
            }
        }
    }
  /*  override fun attachBaseContext(newBase: Context) {// get chosen language from shread preference
        val localeToSwitchTo = sharePref().language.take(2)
        Log.e("BaseActivity", "laungauge $localeToSwitchTo")

        val localeUpdatedContext: ContextWrapper = ContextUtils.updateLocale(
            newBase, updateResources(
                newBase,
                localeToSwitchTo
            )
        )
        super.attachBaseContext(localeUpdatedContext)
    }*/
    private fun updateResources(context: Context, language: String) : Locale {
        try {
            Configuration(context.resources.configuration).run {
                Locale.setDefault(Locale(language).also { locale ->
                    return locale
                })
            }
        }catch (e: Exception){
            Log.e("BaseActivity", e.printStackTrace().toString())
            return Locale.US
        }
    }


    override fun onResume() {
        super.onResume()
        if (restart){
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }
    }
    fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    fun showNoConnectionDialog(context: Context): AlertDialog {
        return AlertDialog.Builder(context).setMessage(
            "no internet"
        ).setCancelable(false).setNeutralButton("check connection")
        { _, _ ->
            try {
                val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
        }.create()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (commonDialog.isShowing){
            commonDialog.dismiss()
        }
    }
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, Locale.getDefault().language));
    }
    fun LogEvent(eventName:String,fileName:String){
        val bundle = Bundle()
        bundle.putString(fileName,"clicked")
        analytics.logEvent(eventName, bundle)
    }

    fun getProfileImage(imageFile: ImageFile){
        Dexter.withContext(this)
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                        uri?.let {
                            it.let {
                                imageFile.loaded(true,it)
                            }
                        }
                    }.launch("image/*")
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {

                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    showSnackBar("This permission needed to add picture")
                }
            }).check()
    }

    interface ImageFile {
        fun loaded(success: Boolean, uri: Uri?)
    }
    fun changeLanguage(code:String){
        LocaleHelper.setLocale(this,code)
    }



    //Ads Setup
    fun SetupAds(rewardAd:Boolean,fullScreen:Boolean){
        MobileAds.initialize(this){
            val testDeviceIds = Arrays.asList("C7B94B9714AD3C874599FB6459F57698")
            val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
            MobileAds.setRequestConfiguration(configuration)
            if (fullScreen){
                loadIn()
            }
            if (rewardAd){
                loadVideoAd()
                loadInVideoAd()
            }
        }
    }

    fun WatchVideo(showAd: ShowAd) {
        if (mRewardedAd != null ) {
            mRewardedAd?.fullScreenContentCallback
            mRewardedAd?.show(this, OnUserEarnedRewardListener {
                showAd.displayed(true)
                mRewardedAd=null
                loadVideoAd()
            })
        }else if(rewardedInterstitialAd!=null){
            rewardedInterstitialAd!!.show(this, OnUserEarnedRewardListener {
                showAd.displayed(true)
                rewardedInterstitialAd=null
                loadInVideoAd()

            })
        }else{
            showAd.displayed(false)
        }
    }
    private var rewardedInterstitialAd: RewardedInterstitialAd? = null
    private var mRewardedAd: RewardedAd? = null
    private var interstitialAd: InterstitialAd?=null
    private fun loadInVideoAd(){
        val adRequest = AdRequest.Builder().build()
        RewardedInterstitialAd.load(this, getString(R.string.rewared_in),
            adRequest, object : RewardedInterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedInterstitialAd) {
                    rewardedInterstitialAd = ad;
                    Log.i("In", "onAdLoaded");
                    rewardedInterstitialAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                        /** Called when the ad failed to show full screen content.  */
                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                            rewardedInterstitialAd=null
                            loadInVideoAd()
                        }

                        /** Called when ad showed the full screen content.  */
                        override fun onAdShowedFullScreenContent() {}

                        /** Called when full screen content is dismissed.  */
                        override fun onAdDismissedFullScreenContent() {
                            rewardedInterstitialAd=null
                            loadInVideoAd()
                        }
                    }
                }
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Handle the error.
                    Log.i("shop", loadAdError.message)
                    rewardedInterstitialAd = null
                    Handler().postDelayed({
                        try {
                            runOnUiThread{
                                loadInVideoAd()
                            }
                        }catch (e:Exception){
                            e.printStackTrace()
                        }
                    },60000)
                }
            })

    }

    private fun loadVideoAd() {

        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(this, getString(R.string.rewarded_ad),
            adRequest, object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Handle the error.
                    Log.i("shop", loadAdError.message)
                    mRewardedAd = null
                    Handler().postDelayed({
                        try {
                            runOnUiThread{
                                loadVideoAd()
                            }
                        }catch (e:Exception){
                            e.printStackTrace()
                        }
                    },60000)
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    Log.i("shop", "Ad was loaded.")
                    mRewardedAd = rewardedAd
                    mRewardedAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                        /** Called when the ad failed to show full screen content.  */
                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                            mRewardedAd=null
                            loadVideoAd()
                        }

                        /** Called when ad showed the full screen content.  */
                        override fun onAdShowedFullScreenContent() {}

                        /** Called when full screen content is dismissed.  */
                        override fun onAdDismissedFullScreenContent() {
                            mRewardedAd=null
                            loadVideoAd()
                        }
                    }

                }
            })

    }
    private fun loadIn() {

        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this, getString(R.string.Interstitial_ad), adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(@NonNull interstitialAd: InterstitialAd) {
                    // The mInterstitialAd reference will be null until
                    // an ad is loaded.
                    this@BaseActivity.interstitialAd = interstitialAd
                    Log.i("ads", "onAdLoaded")
                    this@BaseActivity.interstitialAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                        /** Called when the ad failed to show full screen content.  */
                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                            this@BaseActivity.interstitialAd=null
                            loadIn()
                        }

                        /** Called when ad showed the full screen content.  */
                        override fun onAdShowedFullScreenContent() {}

                        /** Called when full screen content is dismissed.  */
                        override fun onAdDismissedFullScreenContent() {
                            this@BaseActivity.interstitialAd=null
                            loadIn()
                        }
                    }

                }

                override fun onAdFailedToLoad(@NonNull loadAdError: LoadAdError) {
                    // Handle the error
                    Log.i("ads", loadAdError.message)
                    this@BaseActivity.interstitialAd  = null
                    Handler().postDelayed({
                        try {
                            runOnUiThread{
                                loadIn()
                            }
                        }catch (e:Exception){
                            e.printStackTrace()
                        }
                    },60000)
                }
            })

    }
    fun FullScreenAds(showAd: ShowAd) {
        if (interstitialAd!=null){
            interstitialAd!!.show(this)
            showAd.displayed(true)
        }else{
            showAd.displayed(false)
        }
    }


    interface ShowAd {
        fun displayed(boolean: Boolean)
    }


    fun sendFinishBundle(bundle: Bundle){
        val returnIntent = Intent()
        returnIntent.putExtras(bundle)
        setResult(RESULT_OK, returnIntent)
        finish()
    }
}