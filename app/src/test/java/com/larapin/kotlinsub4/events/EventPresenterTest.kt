package com.larapin.kotlinsub4.events

import com.google.gson.Gson
import com.larapin.kotlinsub4.TestContextProvider
import com.larapin.kotlinsub4.api.ApiRepository
import com.larapin.kotlinsub4.api.TheSportDBApi
import com.larapin.kotlinsub4.event.EventPresenter
import com.larapin.kotlinsub4.event.EventView
import com.larapin.kotlinsub4.model.Event
import com.larapin.kotlinsub4.model.EventResponse
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/**
 * Created by Avin on 09/10/2018.
 */
class EventPresenterTest{
    @Mock
    private
    lateinit var view: EventView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: EventPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = EventPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetEventList(){
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)
        val idLeague = "4328"
        val eventCat = "eventspastleague"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getEvent(idLeague, eventCat)),
                EventResponse::class.java
        )).thenReturn(response)

        presenter.getEventList(idLeague, eventCat)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showEventList(events)
        Mockito.verify(view).hideLoading()
    }
}