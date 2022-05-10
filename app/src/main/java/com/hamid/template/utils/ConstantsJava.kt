package com.hamid.template.utils

import android.content.Context
import android.content.SharedPreferences
import android.widget.TextView
import com.hamid.template.R

class ConstantsJava() {
    val BASE_URL = "https://mobile-dev.zrpath.com/"
    val IMAGE_URL = "https://mobile-dev.zrpath.com/"
    private val AppContent = "AppContent"
    private val MODE = Context.MODE_PRIVATE
    private var baseUrl=Pair("baseUrl",BASE_URL)

    private fun getShared(context: Context):SharedPreferences{
      return  context.getSharedPreferences(AppContent,MODE)
    }

    fun getBaseURl(context: Context):String{
       return getShared(context).getString(baseUrl.first,baseUrl.second).checkForNull()
    }
    fun setBaseURl(context: Context,newURL:String){
        getShared(context).edit().putString(baseUrl.first,newURL).apply()
    }
    fun resetBaseURl(context: Context){
        getShared(context).edit().putString(baseUrl.first,baseUrl.second).apply()
    }

    fun checkForStaging(context: Context):Boolean{
        if (getBaseURl(context).equals(baseUrl.second)){
            return true
        }
        return false
    }

    private fun String?.checkForNull():String{
        if (this==null){
            return baseUrl.second
        }
        return this
    }
}