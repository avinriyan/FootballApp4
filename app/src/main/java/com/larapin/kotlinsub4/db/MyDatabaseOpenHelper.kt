package com.larapin.kotlinsub4.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

/**
 * Created by Avin on 11/09/2018.
 */
class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteEvent.db", null, 1){
    companion object {
        private var instance: MyDatabaseOpenHelper? = null
        
        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper{
            if(instance == null){
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(FavoriteEvent.TABLE_FAVORITE, true,
                FavoriteEvent.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteEvent.EVENT_ID to TEXT + UNIQUE,
                FavoriteEvent.EVENT_DATE to TEXT,
                FavoriteEvent.EVENT_TIME to TEXT,
                FavoriteEvent.ID_HOME to TEXT,
                FavoriteEvent.TEAM_HOME to TEXT,
                FavoriteEvent.SCORE_HOME to TEXT,
                FavoriteEvent.ID_AWAY to TEXT,
                FavoriteEvent.TEAM_AWAY to TEXT,
                FavoriteEvent.SCORE_AWAY to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(FavoriteEvent.TABLE_FAVORITE,true)
    }
}

val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)