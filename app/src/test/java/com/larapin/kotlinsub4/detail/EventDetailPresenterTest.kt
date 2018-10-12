package com.larapin.kotlinsub4.detail

import com.google.gson.Gson
import com.larapin.kotlinsub4.TestContextProvider
import com.larapin.kotlinsub4.api.ApiRepository
import com.larapin.kotlinsub4.api.TheSportDBApi
import com.larapin.kotlinsub4.detail.event.EventDetailPresenter
import com.larapin.kotlinsub4.detail.event.EventDetailView
import com.larapin.kotlinsub4.model.EventDetail
import com.larapin.kotlinsub4.model.EventDetailResponse
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Created by Avin on 10/10/2018.
 */
class EventDetailPresenterTest{
    @Mock
    private lateinit var view: EventDetailView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var presenter: EventDetailPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = EventDetailPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetEventDetail(){
        val event: MutableList<EventDetail> = mutableListOf()
        val response = EventDetailResponse(event)
        val id = "441613"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getEventDetail(id)),
                EventDetailResponse::class.java
        )).thenReturn(response)

        presenter.getEventDetail(id)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showEventDetail(event)
        Mockito.verify(view).hideLoading()
    }
}