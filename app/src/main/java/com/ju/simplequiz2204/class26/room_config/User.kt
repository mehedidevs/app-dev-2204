package com.ju.simplequiz2204.class26.room_config

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(

    @PrimaryKey(autoGenerate = true)
    var userId: Int,

    @ColumnInfo(name = "user_name")
    var name: String,

    @ColumnInfo(name = "user_age")
    var age: Int,

    @ColumnInfo(name = "user_mobile")
    var mobile: String
)
