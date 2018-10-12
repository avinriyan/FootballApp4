package com.larapin.kotlinsub4.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.larapin.kotlinsub4.R
import com.larapin.kotlinsub4.model.Event
import kotlinx.android.synthetic.main.item_event.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.text.SimpleDateFormat

/**
 * Created by Avin on 04/09/2018.
 */
class EventAdapter(private val context: Context, private val events: List<Event>, private val listener: (Event) -> Unit)
    : RecyclerView.Adapter<EventViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(LayoutInflater.from(context).inflate(R.layout.item_event, parent, false))
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bindItem(events[position], listener)
    }

}

class EventViewHolder(view: View): RecyclerView.ViewHolder(view) {
    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    fun bindItem(events: Event, listener: (Event) -> Unit){
        if(events.scoreHome.isNullOrEmpty() && events.scoreAway.isNullOrEmpty()){
            itemView.tv_skor.text = "VS"
        }else{
            itemView.tv_skor.text = events.scoreHome+" VS "+events.scoreAway
        }
        var tanggalBaru = SimpleDateFormat("EEE, d MMM yyyy")
                .format(SimpleDateFormat("yyyy-MM-dd")
                        .parse(events.eventDate))
        itemView.tv_date.text = tanggalBaru
        itemView.tv_home.text = events.teamHome
        itemView.tv_away.text = events.teamAway
        itemView.onClick { listener(events) }
    }
}
