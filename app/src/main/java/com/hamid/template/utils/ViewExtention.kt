package com.hamid.template.utils

import android.content.res.ColorStateList
import android.graphics.Color
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText
import com.hamid.template.R

fun View.setShowCondition(boolean: Boolean) {
    if (boolean){
        this.visibility = View.VISIBLE
    }else{
        this.visibility = View.GONE
    }
}
fun TextInputEditText.addDateMonth(){
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {}

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, start: Int, removed: Int, added: Int) {
            if (start == 1 && start + added == 2 && p0?.contains('/') == false) {
                this@addDateMonth.setText("$p0/")
                this@addDateMonth.text?.length?.let { this@addDateMonth.setSelection(it) }
            } else if (start == 3 && start - removed == 2 && p0?.contains('/') == true) {
                this@addDateMonth.setText(p0.toString().replace("/", ""))
                this@addDateMonth.text?.length?.let { this@addDateMonth.setSelection(it) }
            }
            if (this@addDateMonth.text.toString().length==3 && !this@addDateMonth.text.toString().contains("/")){
                this@addDateMonth.setText(p0!!.get(0).toString()+p0.get(1).toString()+"/"+p0.get(2).toString())
                this@addDateMonth.text?.length?.let { this@addDateMonth.setSelection(it) }
            }
        }
    })
}
fun View.makeGone() {
    this.visibility = View.GONE
}

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.makeInvisible() {
    this.visibility = View.INVISIBLE
}

fun TextInputEditText.disableEditText() {
    this.isFocusableInTouchMode = false
    this.isEnabled = false
}

fun TextInputEditText.enableEditText() {
    this.isFocusableInTouchMode = true
    this.isEnabled = true
}

fun TextInputEditText.isLimitedValue(limit: Int): Boolean {
    return this.text.toString().toInt() <= limit
}

@BindingAdapter("is_selected")
fun setSelected(view: View, selected: Boolean) {
    view.isSelected = selected
}

fun EditText.hasEmptyValue(): Boolean {
    return this.text.toString().isEmpty()
}

fun EditText.getTx(): String {
    return this.text.toString()
}

fun Group.setAllOnClickListener(listener: View.OnClickListener?) {
    referencedIds.forEach { id ->
        rootView.findViewById<View>(id).setOnClickListener(listener)
    }
}

 fun EditText.enableEditText() {
    isFocusableInTouchMode = true
    isFocusable = true
    isEnabled = true
}

 fun EditText.disableEditText() {
    isFocusableInTouchMode = false
    isFocusable = false
    isEnabled = false
}

fun ImageView.circleImage(url:String){
    Glide.with(this).load(url).circleCrop().into(this)
}
fun ImageView.roundImage(url:String){
    Glide.with(this).load(url).circleCrop().centerCrop()
        .apply(RequestOptions.bitmapTransform(RoundedCorners(20))).into(this)
}
fun ImageView.roundImage(url:Int){
    Glide.with(this).load(url).centerCrop()
        .apply(RequestOptions.bitmapTransform(RoundedCorners(20))).into(this)
}

fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
    val spannableString = SpannableString(this.text)
    for (link in links) {
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                Selection.setSelection((view as TextView).text as Spannable, 0)
                view.invalidate()
                link.second.onClick(view)
            }
        }
        val startIndexOfLink = this.text.toString().indexOf(link.first)
        spannableString.setSpan(
            clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            ForegroundColorSpan(Color.parseColor("#46C2CC")),
            startIndexOfLink,
            startIndexOfLink + link.first.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    this.movementMethod =
        LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
    this.setText(spannableString, TextView.BufferType.SPANNABLE)
}
fun CheckBox.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
    val spannableString = SpannableString(this.text)
    for (link in links) {
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                Selection.setSelection((view as TextView).text as Spannable, 0)
                view.invalidate()
                link.second.onClick(view)
            }
        }
        val startIndexOfLink = this.text.toString().indexOf(link.first)
        spannableString.setSpan(
            clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            ForegroundColorSpan(Color.parseColor("#46C2CC")),
            startIndexOfLink,
            startIndexOfLink + link.first.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    this.movementMethod =
        LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
    this.setText(spannableString, TextView.BufferType.SPANNABLE)
}

fun TextView.ShowEmptyDot(){
    this.text="---- -----"
}

fun MaterialCardView.setLocalEnable(boolean: Boolean){
    this.isEnabled=boolean
    if (boolean){
        this.setCardBackgroundColor(ColorStateList.valueOf(this.context.getColor(R.color.white)))
    }else{
        this.setCardBackgroundColor(ColorStateList.valueOf(this.context.getColor(R.color.color_divider)))
    }
}
fun MaterialCardView.setCheckListColor(boolean: Boolean){
    Log.i("here",boolean.toString()+"hamid")
    if (boolean){
        Log.i("here",boolean.toString()+"hamid2")
        this.setCardBackgroundColor(ColorStateList.valueOf(this.context.getColor(R.color.listBackDark)))
    }else{
        Log.i("here",boolean.toString()+"hamid3")
        this.setCardBackgroundColor(ColorStateList.valueOf(this.context.getColor(R.color.listBackLight)))
    }
}