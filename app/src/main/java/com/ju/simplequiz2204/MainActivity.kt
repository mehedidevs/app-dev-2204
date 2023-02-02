package com.ju.simplequiz2204

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ju.simplequiz2204.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //starting the quiz game

        binding.playBtn.setOnClickListener {
            val intent = Intent(applicationContext, PlayActivityFull::class.java)
            startActivity(intent)


        }


    }
}