package com.hamid.template.ui.fillForm

import com.hamid.template.base.ViewInteractor


interface FillFormContracts : ViewInteractor {
    fun setData()
    fun onButtonBackPressed()
    fun ShowLoading()
    fun HideLoading()
    fun saveAndFinish(html:String)
}