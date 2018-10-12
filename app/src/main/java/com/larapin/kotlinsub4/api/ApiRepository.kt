package com.larapin.kotlinsub4.api

import java.net.URL

/**
 * Created by Avin on 04/09/2018.
 */
class ApiRepository{

    fun doRequest(url: String): String{
        return URL(url).readText()
    }
}