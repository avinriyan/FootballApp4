package com.larapin.kotlinsub4.api

import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

/**
 * Created by Avin on 10/10/2018.
 */
class ApiRepositoryTest{
    @Test
    fun testDoRequest(){
        val apiRepository = mock(ApiRepository::class.java)
        val url = "https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4328"
        apiRepository.doRequest(url)
        Mockito.verify(apiRepository).doRequest(url)
    }
}
