package com.hamid.template.utils

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
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