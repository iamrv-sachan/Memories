package com.rajdroid.memories

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import java.time.Instant
import kotlin.reflect.KParameter

@Database(entities = arrayOf(Place::class),version = 1)
abstract class Placedatabase : RoomDatabase() {

    abstract fun placeDao(): PlaceDao
    companion object {
        @Volatile
        private var INSTANCE: Placedatabase? = null


        fun getInstance(context: Context): Placedatabase? {
            var instance = INSTANCE
            synchronized(this) {

                if (instance == null) {
                    instance = databaseBuilder(
                        context.applicationContext,
                        Placedatabase::class.java,
                        "subscriber_data_database"
                    ).build()
                }
            }
            return instance;
        }

    }
}