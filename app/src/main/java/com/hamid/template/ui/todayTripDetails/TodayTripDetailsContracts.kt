package com.hamid.template.ui.todayTripDetails

import com.hamid.template.base.ViewInteractor
import com.hamid.template.ui.todayTripDetails.models.ResponseOnGoingVisit
import com.repsly.library.timelineview.TimelineView


interface TodayTripDetailsContracts : ViewInteractor {
    fun finishScreen()
    fun setData()
    fun setUpView()
    fun HideLoading()
    fun ShowLoading()
    fun allDeactive()
    fun activePickUp()
    fun activeCheckListPick()
    fun activeMissingForm()
    fun activeSignature()
    fun activeDrop()

    fun activeTimeLine(timelineView: TimelineView,string: String,start: Boolean,end: Boolean)
    fun disableTimeLine(timelineView: TimelineView,string: String,start: Boolean,end: Boolean)
    fun inProgressTimeLine(timelineView: TimelineView,string: String,start: Boolean,end: Boolean)

    fun clickedStartTrip()
    fun clickedPickUpCheckList()
    fun clickedMissingForm()
    fun clickedDropOfSignature()
    fun clickedDrop()
    fun finishTrip()

    fun loadVisitDetails()


    fun checkGroupStatus()
    fun loadGroupNotStartView()
    fun loadGroupStartedView()
    fun loadGroupTripCompleted()
    fun loadTripCompletedView()

    fun fillUserDetails(data:ResponseOnGoingVisit.Data)

    fun handlePrepare()
}