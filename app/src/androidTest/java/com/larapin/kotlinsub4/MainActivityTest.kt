package com.larapin.kotlinsub4

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.larapin.kotlinsub4.R.id.*
import com.larapin.kotlinsub4.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Avin on 10/10/2018.
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest{
    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testAppBehaviour(){
        //Aplikasi terbuka dan menampilkan beberapa view
        Thread.sleep(3000)
        //memastikan suatu daftar tampil
        onView(withId(list_event))
                .check(matches(isDisplayed()))
        //melakukan scroll pada daftar ke-10
        Thread.sleep(3000)
        onView(withId(list_event)).perform(RecyclerViewActions
                .scrollToPosition<RecyclerView.ViewHolder>(9))
        //melakukan klik pada daftar ke-10
        Thread.sleep(3000)
        onView(withId(list_event)).perform(RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(9, click()))
        //memeriksa tombol favorit tampil
        Thread.sleep(3000)
        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))
        //melakukan klik pada tombol add to favoritr
        Thread.sleep(3000)
        onView(withId(add_to_favorite)).perform(click())
        //memeriksa teks yang muncul
        onView(withText("Added to favorite"))
                .check(matches(isDisplayed()))
        //kembali pada halaman sebelumnya
        Thread.sleep(3000)
        pressBack()

        //memeriksa navigation tampil
        Thread.sleep(3000)
        onView(withId(navigation))
                .check(matches(isDisplayed()))
        //melakukan klik pada navigation fav
        Thread.sleep(3000)
        onView(withId(navigation_fav)).perform(click())
        Thread.sleep(3000)

        //memastikan suatu daftar favorit tampil
        onView(withId(list_favorite_event))
                .check(matches(isDisplayed()))
        //melakukan klik pada daftar ke-1
        Thread.sleep(3000)
        onView(withId(list_favorite_event)).perform(RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        //memeriksa tombol favorit tampil
        Thread.sleep(3000)
        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))
        //melakukan klik pada tombol add to favoritr
        Thread.sleep(3000)
        onView(withId(add_to_favorite)).perform(click())
        //memeriksa teks yang muncul
        onView(withText("Removed from favorite"))
                .check(matches(isDisplayed()))
        //kembali pada halaman sebelumnya
        Thread.sleep(3000)
        pressBack()
    }

}
