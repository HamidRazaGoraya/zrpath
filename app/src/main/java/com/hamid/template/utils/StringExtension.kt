package com.hamid.template.utils

import android.content.Context
import com.hamid.template.R
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun String.addImageUrl():String{
    return Constants.IMAGE_URL + this
}

fun String.getDateOfConsulatation():String{
    val date: Date? = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(this)
    return SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault()).format(date!!)
}
fun String.getDateOfReport():String{
    val date: Date? = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault()).parse(this)
    return SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault()).format(date!!)
}
fun String.getDateOfNotes():String{
    val date: Date? = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault()).parse(this)
    return SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(date!!)
}

fun String.getTimeOfConsulatation():String{
    val date: Date? = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(this)
    return SimpleDateFormat("HH:mm a", Locale.getDefault()).format(date!!)
}

fun Double.addKrToValue(context: Context):String{
    val precision = DecimalFormat("0.00")
    val availableMoneyFormat = precision.format(this)
    return String.format(context.getString(R.string.attach_kr),availableMoneyFormat)
}

fun String.isValid():Boolean{
    return if (this.isNullOrEmpty()){
        false
    }else this != "null"
}