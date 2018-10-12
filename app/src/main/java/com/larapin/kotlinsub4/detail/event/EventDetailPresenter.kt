package com.larapin.kotlinsub4.detail.event

import com.google.gson.Gson
import com.larapin.kotlinsub4.api.ApiRepository
import com.larapin.kotlinsub4.api.TheSportDBApi
import com.larapin.kotlinsub4.model.EventDetailResponse
import com.larapin.kotlinsub4.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by Avin on 06/09/2018.
 */
class EventDetailPresenter(private val view: EventDetailView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()){

    fun getEventDetail(eventId: String?){
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getEventDetail(eventId)),
                        EventDetailResponse::class.java
                )
            }
            view.hideLoading()
            view.showEventDetail(data.await().events)
        }
    }
}