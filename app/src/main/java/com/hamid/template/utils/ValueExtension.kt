package com.hamid.template.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.gson.Gson
import com.hamid.template.R
import com.hamid.template.ui.todayTripsList.models.RequestReferralList
import java.io.File
import java.lang.reflect.Type
import java.sql.Types
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

fun Double.decimalConverter(): String {
    val precision = DecimalFormat("00.00")
    return precision.format(this)
}

fun Float.decimalConverter(): String =
    "%.2f".format()

fun getFormatedDateTime(
    dateStr: String?,
    strReadFormat: String?,
    strWriteFormat: String?
): String? {
    var formattedDate = dateStr
    val readFormat: DateFormat = SimpleDateFormat(strReadFormat, Locale.getDefault())
    val writeFormat: DateFormat = SimpleDateFormat(strWriteFormat, Locale.getDefault())
    var date: Date? = null
    try {
        date = readFormat.parse(dateStr)
    } catch (e: ParseException) {
    }
    if (date != null) {
        formattedDate = writeFormat.format(date)
    }
    return formattedDate
}

fun String.getTimeAfterHalfHour():String?{
    val millisToAdd: Long = 1800000
    val format: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    val d = format.parse(this)
    d.time = d.time + millisToAdd
    return format.format(d)
}

fun File.getMediaDuration(context: Context): Long {
    if (!exists()) return 0
    val retriever = MediaMetadataRetriever()
    retriever.setDataSource(context, Uri.parse(absolutePath))
    val duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
    retriever.release()

    return duration?.toLongOrNull() ?: 0
}
fun String?.HandleNullToken():String{
    if (this.isNullOrEmpty()){
        return "String";
    }
    return this
}
fun String?.HandleNullToken(string: String):String{
    if (this.isNullOrEmpty()){
        return string;
    }
    return this
}
fun String?.HandleNullkey():String{
    if (this.isNullOrEmpty()){
        return "A_657c48d0-915a-4fdc-a3e4-90b1fef38344";
    }
    return this
}

fun Calendar.getDateValue():String{
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
    return format.format(this.time)
}
fun Calendar.getDateValueLocal():String{
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    return format.format(this.time)
}

fun Chip.getRandomColor() {
    this.setTextColor(Color.parseColor("#ffffff"))
    this.chipBackgroundColor= ColorStateList.valueOf((Math.random() * 16777215).toInt() or (0xFF shl 24))
}
fun Chip.getRandomColorChip() {
    val context=this.context.resources
    val ids=ArrayList<Int>()
    ids.add(context.getColor(R.color.random01))
    ids.add(context.getColor(R.color.random02))
    ids.add(context.getColor(R.color.random03))
    ids.add(context.getColor(R.color.random04))
    ids.add(context.getColor(R.color.random05))
    ids.add(context.getColor(R.color.random06))
    ids.add(context.getColor(R.color.random07))
    ids.add(context.getColor(R.color.random08))
    ids.add(context.getColor(R.color.random09))
    ids.add(context.getColor(R.color.random010))
    ids.add(context.getColor(R.color.random011))
    ids.add(context.getColor(R.color.random012))
    ids.add(context.getColor(R.color.random013))
    ids.add(context.getColor(R.color.random014))
    ids.add(context.getColor(R.color.random015))
    ids.add(context.getColor(R.color.random016))
    this.setTextColor(Color.parseColor("#ffffff"))
    this.chipBackgroundColor= ColorStateList.valueOf(ids.random())
}

fun Boolean.isPickUpString():String{
    if (this){
        return "Up"
    }
    return "Down"
}


fun String?.checkNull():String{
    Log.i("OutPosition","${this}")
    if (this.isNullOrEmpty()){
        return ""
    }
    return this
}

fun ImageView.getRandomColor() {
    val gd = GradientDrawable()
    gd.setColor((Math.random() * 16777215).toInt() or (0xFF shl 24))
    gd.cornerRadius = 200f

    Glide.with(this.context).load(gd).into(this)

}

fun ImageView.setRandomTint() {
    this.imageTintList= ColorStateList.valueOf((Math.random() * 16777215).toInt() or (0xFF shl 24))
}


fun Any.toJsonString():String{
    return Gson().toJson(this)
}

