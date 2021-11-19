package com.hamid.template.ui.dashboard

import com.hamid.template.base.ViewInteractor


interface MainContracts : ViewInteractor {
    fun initiate()
    fun ShowLoading()
    fun HideLoading()
}