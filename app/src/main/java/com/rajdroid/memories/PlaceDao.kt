package com.rajdroid.memories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlaceDao {
    @Insert
suspend fun Insert(place:Place) : Long

    @Query("SELECT * FROM Place_table")
    suspend fun getAllUsers():List<Place>
}