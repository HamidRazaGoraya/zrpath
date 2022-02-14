package com.hamid.template.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.view.View
import androidx.core.view.marginTop
import com.google.android.material.chip.Chip
import java.io.File
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

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
    val rnd = Random()
    this.setTextColor(Color.parseColor("#ffffff"))
    this.chipBackgroundColor=ColorStateList.valueOf(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)))
}