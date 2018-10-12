package com.larapin.kotlinsub4.detail.event

import com.larapin.kotlinsub4.model.EventDetail

/**
 * Created by Avin on 06/09/2018.
 */
interface EventDetailView{
    fun showLoading()
    fun hideLoading()
    fun showEventDetail(data: List<EventDetail>)
}