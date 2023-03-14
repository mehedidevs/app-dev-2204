package com.ju.simplequiz2204.class26

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ju.simplequiz2204.R
import com.ju.simplequiz2204.class26.room_config.User
import com.ju.simplequiz2204.class26.room_config.UserDao
import com.ju.simplequiz2204.class26.room_config.UserDatabase
import com.ju.simplequiz2204.databinding.ActivityMain26Binding

class MainActivity26 : AppCompatActivity(), UserClickListener {
    lateinit var binding: ActivityMain26Binding
    lateinit var userList: List<User>

    private lateinit var userDao: UserDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain26Binding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.insertBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity26, CreateActivity::class.java))
        }
    }


    override fun onResume() {
        super.onResume()
        //  var mainActivity26: UserClickListener = MainActivity26()
        val userAdapter = UserAdapter(this)
        binding.userRcv.adapter = userAdapter
        userDao = UserDatabase.getInstance(this)
            .getUserDao()
        userList = userDao.getAllUser()

        userAdapter.submitList(userList)

    }

    override fun userDelete(user: User) {
        Log.i("TAG", "userDelete: ${user.userId} ")
        userDao.userDelete(user)

        //  Toast.makeText(this@MainActivity26, "Delete Clicked", Toast.LENGTH_LONG).show()

    }

    override fun userUpdate(user: User) {

        val intent = Intent(this, CreateActivity::class.java)

        intent.putExtra(CreateActivity.id_key, user.userId)
        intent.putExtra(CreateActivity.name_key, user.name)
        intent.putExtra(CreateActivity.age_key, user.age.toString())
        intent.putExtra(CreateActivity.mobile_key, user.mobile)

        startActivity(intent)

        Log.i("TAG", "userUpdate:${user.userId} ")

        // Toast.makeText(this@MainActivity26, "Update Clicked", Toast.LENGTH_LONG).show()

    }
}