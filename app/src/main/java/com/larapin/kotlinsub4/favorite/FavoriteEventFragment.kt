package com.larapin.kotlinsub4.favorite


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.larapin.kotlinsub4.R
import com.larapin.kotlinsub4.db.FavoriteEvent
import com.larapin.kotlinsub4.db.database
import com.larapin.kotlinsub4.detail.event.EventDetailActivity
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class FavoriteEventFragment : Fragment() {
    private var favorites: MutableList<FavoriteEvent> = mutableListOf()
    private lateinit var adapter: FavoriteEventAdapter
    private lateinit var listEvent: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_favorite_event, container, false)
        listEvent = view.find(R.id.list_favorite_event)
        swipeRefresh = view.find(R.id.swipe_refresh_fav_event)

        adapter = FavoriteEventAdapter(ctx, favorites){
            startActivity<EventDetailActivity>(
                    "id" to "${it.eventId}",
                    "idhome" to "${it.idHome}",
                    "idaway" to "${it.idAway}"
            )
        }

        listEvent.adapter = adapter
        showFavorite()
        swipeRefresh.onRefresh {
            showFavorite()
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

    private fun showFavorite() {
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(FavoriteEvent.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<FavoriteEvent>())
            favorites.clear()
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

}
