package com.larapin.kotlinsub4.event

import com.google.gson.Gson
import com.larapin.kotlinsub4.util.CoroutineContextProvider
import com.larapin.kotlinsub4.api.ApiRepository
import com.larapin.kotlinsub4.api.TheSportDBApi
import com.larapin.kotlinsub4.model.EventResponse
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

/**
 * Created by Avin on 04/09/2018.
 */
class EventPresenter(private val view: EventView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {
    fun getEventList(league: String?, event: String?) {
        view.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getEvent(league, event)),
                        EventResponse::class.java
                )
            }
            view.hideLoading()
            view.showEventList(data.await().events)
        }
    }
}
