package com.ju.simplequiz2204.class26.room_config

import androidx.room.*

@Dao
interface UserDao {

    @Insert
    fun userInsert(user: User)

    @Update
    fun userUpdate(user: User)

    @Delete
    fun userDelete(user: User)


    @Query("SELECT * FROM User")
    fun getAllUser(): List<User>


}