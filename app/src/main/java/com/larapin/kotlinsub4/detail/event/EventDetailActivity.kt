package com.larapin.kotlinsub4.detail.event

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import com.google.gson.Gson
import com.larapin.kotlinsub4.R
import com.larapin.kotlinsub4.R.drawable.ic_add_to_favorites
import com.larapin.kotlinsub4.R.drawable.ic_added_to_favorites
import com.larapin.kotlinsub4.R.id.add_to_favorite
import com.larapin.kotlinsub4.R.menu.detail_menu
import com.larapin.kotlinsub4.api.ApiRepository
import com.larapin.kotlinsub4.db.FavoriteEvent
import com.larapin.kotlinsub4.db.database
import com.larapin.kotlinsub4.model.EventDetail
import com.larapin.kotlinsub4.util.invisible
import com.larapin.kotlinsub4.util.visible
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_event_detail.*
import okhttp3.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.onRefresh
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class EventDetailActivity : AppCompatActivity(), EventDetailView {
    private lateinit var presenter: EventDetailPresenter
    private lateinit var details: EventDetail
    private lateinit var idEvent: String
    private var idHome: String = ""
    private var idAway: String = ""
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    val client = OkHttpClient()

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)
        supportActionBar?.title = "Match Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        progressBar = find(R.id.progress_bar_detail)
        swipeRefresh = find(R.id.swipe_refresh_detail)

        val intent = intent
        idEvent = intent.getStringExtra("id")
        idHome = intent.getStringExtra("idhome")
        idAway = intent.getStringExtra("idaway")

        favoriteState()
        val request = ApiRepository()
        val gson = Gson()
        presenter = EventDetailPresenter(this, request, gson)
        presenter.getEventDetail(idEvent)
        swipeRefresh.onRefresh {
            presenter.getEventDetail(idEvent)
        }
        val logo = arrayOf(idHome, idAway)
        getBadge(logo)
    }

    private fun favoriteState(){
        database.use {
            val result = select(FavoriteEvent.TABLE_FAVORITE)
                    .whereArgs("(EVENT_ID = {id})",
                            "id" to idEvent)
            val favorite = result.parseList(classParser<FavoriteEvent>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showEventDetail(data: List<EventDetail>) {
        details = EventDetail(data[0].eventId,
                data[0].eventDate,
                data[0].eventTime,
                data[0].idHome,
                data[0].teamHome,
                data[0].scoreHome,
                data[0].formationHome,
                data[0].goalHome,
                data[0].shotHome,
                data[0].gkHome,
                data[0].defHome,
                data[0].midHome,
                data[0].forwHome,
                data[0].idAway,
                data[0].teamAway,
                data[0].scoreAway,
                data[0].formationAway,
                data[0].goalAway,
                data[0].shotAway,
                data[0].gkAway,
                data[0].defAway,
                data[0].midAway,
                data[0].forwAway)
        swipeRefresh.isRefreshing = false
        val tanggal = SimpleDateFormat("EEE, d MMM yyyy")
                .format(toGMTFormat(data[0].eventDate, data[0].eventTime))
        val waktu = SimpleDateFormat("HH:mm")
                .format(toGMTFormat(data[0].eventDate, data[0].eventTime))
        tv_date_detail.text = tanggal
        tv_time_detail.text = waktu
        tv_home_detail.text = data[0].teamHome
        tv_away_detail.text = data[0].teamAway
        if(data[0].scoreHome.isNullOrEmpty() && data[0].scoreAway.isNullOrEmpty()){
            tv_skor_detail.text = "0 vs 0"
        }else{
            tv_skor_detail.text = data[0].scoreHome+"  vs  "+data[0].scoreAway
        }
        tv_home_formation.text = data[0].formationHome
        tv_away_formation.text = data[0].formationAway
        tv_home_goals.text = data[0].goalHome?.replace(";", "\n")
        tv_away_goals.text = data[0].goalAway?.replace(";", "\n")
        tv_home_shots.text = data[0].shotHome
        tv_away_shots.text = data[0].shotAway
        tv_home_gk.text = biarRapi(data[0].gkHome)
        tv_away_gk.text = biarRapi(data[0].gkAway)
        tv_home_def.text = biarRapi(data[0].defHome)
        tv_away_def.text = biarRapi(data[0].defAway)
        tv_home_mid.text = biarRapi(data[0].midHome)
        tv_away_mid.text = biarRapi(data[0].midAway)
        tv_home_forward.text = biarRapi(data[0].forwHome)
        tv_away_forward.text = biarRapi(data[0].forwAway)
    }

    private fun getBadge(logo: Array<String>) {
        for(i in 0..1){
            val request = Request.Builder()
                    .url("https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id="+logo[i])
                    .build()
            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {}
                override fun onResponse(call: Call, response: Response) {
                    var a = response.body()?.string()
                    runOnUiThread {
                        run(){
                            var json = JSONObject(a)
                            var badge = json.getJSONArray("teams").getJSONObject(0).getString("strTeamBadge")
                            if(i == 0) {
                                Picasso.with(ctx).load(badge).into(img_home)
                            }else{
                                Picasso.with(ctx).load(badge).into(img_away)
                            }
                        }
                    }
                }
            })
        }
    }

    private fun toGMTFormat(date: String?, time: String?): Date? {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val dateTime = "$date $time"
        return formatter.parse(dateTime)
    }

    //merapikan string (mengganti <semicolon> dengan <enter>)
    private fun biarRapi(pemain: String?): String? {
        return pemain?.replace("; ", "\n")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite(){
        try {
            database.use {
                insert(FavoriteEvent.TABLE_FAVORITE,
                        FavoriteEvent.EVENT_ID to details.eventId,
                        FavoriteEvent.EVENT_DATE to details.eventDate,
                        FavoriteEvent.EVENT_TIME to details.eventTime,
                        FavoriteEvent.ID_HOME to details.idHome,
                        FavoriteEvent.TEAM_HOME to details.teamHome,
                        FavoriteEvent.SCORE_HOME to details.scoreHome,
                        FavoriteEvent.ID_AWAY to details.idAway,
                        FavoriteEvent.TEAM_AWAY to details.teamAway,
                        FavoriteEvent.SCORE_AWAY to details.scoreAway)
            }
            snackbar(swipeRefresh, "Added to favorite").show()
        }catch (e: SQLiteConstraintException){
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(FavoriteEvent.TABLE_FAVORITE, "(EVENT_ID = {id})",
                        "id" to idEvent)
            }
            snackbar(swipeRefresh, "Removed from favorite").show()
        }catch (e: SQLiteConstraintException){
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }
}