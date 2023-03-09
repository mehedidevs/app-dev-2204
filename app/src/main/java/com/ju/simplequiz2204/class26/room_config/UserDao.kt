package com.ju.simplequiz2204.class26.room_config

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

@Dao
interface UserDao {

    @Insert
    fun userInsert(user: User)

    @Update
    fun userUpdate(user: User)

    @Delete
    fun userDelete(user: User)




}