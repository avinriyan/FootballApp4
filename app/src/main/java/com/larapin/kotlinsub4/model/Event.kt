package com.larapin.kotlinsub4.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Avin on 04/09/2018.
 */
data class Event(
        @SerializedName("idEvent")
        var eventId: String? = null,

        @SerializedName("dateEvent")
        var eventDate: String? = null,

        //home

        @SerializedName("idHomeTeam")
        var idHome: String? = null,

        @SerializedName("strHomeTeam")
        var teamHome: String? = null,

        @SerializedName("intHomeScore")
        var scoreHome: String? = null,

        //away

        @SerializedName("idAwayTeam")
        var idAway: String? = null,

        @SerializedName("strAwayTeam")
        var teamAway: String? = null,

        @SerializedName("intAwayScore")
        var scoreAway: String? = null

)