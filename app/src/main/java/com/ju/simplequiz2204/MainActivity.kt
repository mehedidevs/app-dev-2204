package com.ju.simplequiz2204

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import com.ju.simplequiz2204.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var countDownTimer: CountDownTimer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //starting the quiz game

        binding.playBtn.setOnClickListener {
            countDownTimer = object : CountDownTimer(3000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    Log.i("TAG", "onTick:  $millisUntilFinished")
                }

                override fun onFinish() {
                    val intent = Intent(applicationContext, PlayActivityFull::class.java)
                    startActivity(intent)
                }

            }.start()




        }


    }
}