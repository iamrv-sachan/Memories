package com.rajdroid.memories

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="Place_Table")
data class Place (
    @PrimaryKey(autoGenerate=true)
    var id:Long=0L,
            @ColumnInfo( name = "title")
            var title : String,
                    var description : String,
                            var date : String

    )