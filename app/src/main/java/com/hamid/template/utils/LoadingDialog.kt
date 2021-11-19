package com.hamid.template.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.StyleRes
import com.hamid.template.R


/**
 * Created by dev on 2019/8/1.
 */
class LoadingDialog constructor(context: Context, @StyleRes themeResId: Int) :
    Dialog(context, themeResId) {
    private var UploadedData: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading)
        UploadedData = findViewById(R.id.UploadedData)

    }

    fun SetProgress(progress: String?) {
        UploadedData!!.text = progress
    }
}