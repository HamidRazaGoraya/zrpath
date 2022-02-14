package com.hamid.template.ui.checkList

import com.hamid.template.base.ViewInteractor


interface CheckListContracts : ViewInteractor {
    fun setData()
    fun onButtonBackPressed()
    fun ShowLoading()
    fun HideLoading()
    fun checkForCheckList()
}