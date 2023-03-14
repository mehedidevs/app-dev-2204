package com.ju.simplequiz2204.class26

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ju.simplequiz2204.R
import com.ju.simplequiz2204.class26.room_config.User
import com.ju.simplequiz2204.class26.room_config.UserDao
import com.ju.simplequiz2204.class26.room_config.UserDatabase
import com.ju.simplequiz2204.databinding.ActivityCreateBinding

class CreateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateBinding

    private var userID = 0
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userDao = UserDatabase.getInstance(this)
            .getUserDao()

        if (intent.hasExtra(id_key)) {

            binding.submitBtn.text = update
            userID = intent.getIntExtra(id_key, 0)
            binding.name.setText(intent.getStringExtra(name_key))
            binding.age.setText(intent.getStringExtra(age_key))
            binding.email.setText(intent.getStringExtra(mobile_key))

        }


        binding.submitBtn.setOnClickListener {
            if (binding.submitBtn.text.toString() == update) {
                val name = binding.name.text.toString()
                val age = binding.age.text.toString()
                val email = binding.email.text.toString()
                val user = User(userID, name, age.toInt(), email)
                userDao.userUpdate(user)


            } else {

                val name = binding.name.text.toString()
                val age = binding.age.text.toString()
                val email = binding.email.text.toString()
                val user = User(0, name, age.toInt(), email)
                userDao.userInsert(user)
            }


        }
    }

    companion object {
        const val id_key = "id"
        const val name_key = "name"
        const val age_key = "age"
        const val mobile_key = "mobile"
        const val update = "update"
    }


}