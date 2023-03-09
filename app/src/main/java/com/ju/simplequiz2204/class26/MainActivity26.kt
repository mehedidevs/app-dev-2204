package com.ju.simplequiz2204.class26

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ju.simplequiz2204.R
import com.ju.simplequiz2204.class26.room_config.User
import com.ju.simplequiz2204.class26.room_config.UserDatabase
import com.ju.simplequiz2204.databinding.ActivityMain26Binding

class MainActivity26 : AppCompatActivity() {
    lateinit var binding: ActivityMain26Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain26Binding.inflate(layoutInflater)

        setContentView(binding.root)

        var user = User(0, "Mehedi", 32, "1456456456")

        var userDao = UserDatabase.getInstance(this)
            .getUserDao()


        binding.insertBtn.setOnClickListener {

            userDao.userInsert(user)
        }




    }
}