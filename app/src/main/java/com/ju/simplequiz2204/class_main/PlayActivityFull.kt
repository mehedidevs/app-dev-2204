package com.ju.simplequiz2204.class_main

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ju.simplequiz2204.R
import com.ju.simplequiz2204.databinding.ActivityPlayCustomBinding
import com.ju.simplequiz2204.databinding.CorrectAnswerBinding
import java.util.*
import java.util.concurrent.TimeUnit

class PlayActivityFull : AppCompatActivity() {

    private lateinit var binding: ActivityPlayCustomBinding

    //    timer
    private var countDownTimer: CountDownTimer? = null
    private val countDownInMilliSecond: Long = 30000
    private val countDownInterval: Long = 1000
    private var timeLeftMilliSeconds: Long = 0
    private var defaultColor: ColorStateList? = null
    private var score = 0
    private var correct = 0
    private var wrong = 0
    private var skip = 0
    private var qIndex = 0
    private var updateQueNo = 1
    private var hasFinished = false
    private val quizList = listOf<Quiz>(
        Quiz(
            "What is the capital of Bangladesh?",
            "Dhaka",
            "Bogra",
            "Noakhali",
            "B-Baria",
            "Dhaka"
        ),
        Quiz("What is the Currency of Bangladesh?", "Euro", "Rupi", "Taka", "USD", "Taka"),
        Quiz("What is the National Animal of Bangladesh?", "Cat", "Dog", "Lion", "Tiger", "Tiger"),
        Quiz(
            "What is the Victory day of Bangladesh?",
            "16 dec",
            "26 mar",
            "21 feb",
            "14 feb",
            "16 dec"
        ),
        Quiz(
            "What is the Independence day of Bangladesh?",
            "16 dec",
            "26 mar",
            "21 feb",
            "14 feb",
            "26 mar"
        )


    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayCustomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialQuestion()

        binding.nextQuestionBtn.setOnClickListener {
            if (binding.radiogrp.checkedRadioButtonId == -1) {
                Toast.makeText(
                    this@PlayActivityFull,
                    "Please select an options",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                showNextQuestion()
            }
        }
        binding.tvNoOfQues.text = "$updateQueNo/${quizList.size}"
        defaultColor = binding.quizTimer.textColors
        timeLeftMilliSeconds = countDownInMilliSecond
        statCountDownTimer()


    }

    private fun initialQuestion() {


        val quiz = quizList[qIndex]

        binding.apply {
            tvQuestion.text = quiz.question
            radioButton1.text = quiz.option1
            radioButton2.text = quiz.option2
            radioButton3.text = quiz.option3
            radioButton4.text = quiz.option4
        }


    }

    @SuppressLint("SetTextI18n")
    private fun showNextQuestion() {
        checkAnswer()
        binding.apply {
            if (updateQueNo < quizList.size) {
                tvNoOfQues.text = "${updateQueNo + 1}/{${quizList.size}}"
                updateQueNo++
            }
            if (qIndex <= quizList.size - 1) {

                var quiz = quizList[qIndex]

                tvQuestion.text = quiz.question
                radioButton1.text = quiz.option1
                radioButton2.text = quiz.option2
                radioButton3.text = quiz.option3
                radioButton4.text = quiz.option4
            } else {
                hasFinished = true
            }
            radiogrp.clearCheck()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun checkAnswer() {
        binding.apply {
            if (binding.radiogrp.checkedRadioButtonId == -1) {
                skip++
                timeOverAlertDialog()
            } else {
                val checkRadioButton =
                    findViewById<RadioButton>(radiogrp.checkedRadioButtonId)
                val checkAnswer = checkRadioButton.text.toString()
                if (checkAnswer == quizList[qIndex].correctAnswer) {
                    correct++
                    txtPlayScore.text = "Score : $correct"
                    correctAlertDialog()
                    countDownTimer?.cancel()
                } else {
                    wrong++
                    wrongAlertDialog()
                    countDownTimer?.cancel()
                }
            }
            qIndex++
        }
    }


    private fun statCountDownTimer() {
        countDownTimer = object : CountDownTimer(timeLeftMilliSeconds, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                binding.apply {
                    timeLeftMilliSeconds = millisUntilFinished
                    val second = TimeUnit.MILLISECONDS.toSeconds(timeLeftMilliSeconds).toInt()
                    // %02d format the integer with 2 digit
                    val timer = String.format(Locale.getDefault(), "Time: %02d", second)
                    quizTimer.text = timer
                    if (timeLeftMilliSeconds < 10000) {
                        quizTimer.setTextColor(Color.RED)
                    } else {
                        quizTimer.setTextColor(defaultColor)
                    }
                }
            }

            override fun onFinish() {
                showNextQuestion()
            }
        }.start()
    }

    @SuppressLint("SetTextI18n")
    private fun correctAlertDialog() {
        val builder = AlertDialog.Builder(this@PlayActivityFull)
        val correctAnswerBinding = CorrectAnswerBinding.inflate(layoutInflater)
        builder.setView(correctAnswerBinding.root)
        correctAnswerBinding.tvDialogScore.text = "Score : $correct"
        val alertDialog = builder.create()
        correctAnswerBinding.correctOk.setOnClickListener {
            goNext()
            timeLeftMilliSeconds = countDownInMilliSecond
            statCountDownTimer()
            alertDialog.dismiss()
        }
        try {
            alertDialog.show()
        } catch (e: WindowManager.BadTokenException) {
            Log.i("TAG", "${e.message} ")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun wrongAlertDialog() {
        val builder = AlertDialog.Builder(this@PlayActivityFull)
        val view = LayoutInflater.from(this@PlayActivityFull).inflate(R.layout.wrong_answer, null)
        builder.setView(view)
        val tvWrongDialogCorrectAns = view.findViewById<TextView>(R.id.tv_wrongDialog_correctAns)
        val wrongOk = view.findViewById<Button>(R.id.wrong_ok)
        tvWrongDialogCorrectAns.text = "Correct Answer : " + quizList[qIndex].correctAnswer
        val alertDialog = builder.create()
        wrongOk.setOnClickListener {
            goNext()
            timeLeftMilliSeconds =
                countDownInMilliSecond
            statCountDownTimer()
            alertDialog.dismiss()
        }
        try {
            alertDialog.show()
        } catch (e: WindowManager.BadTokenException) {
            Log.i("TAG", "${e.message} ")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun timeOverAlertDialog() {
        val builder = AlertDialog.Builder(this@PlayActivityFull)
        val view = LayoutInflater.from(this@PlayActivityFull).inflate(R.layout.time_over, null)
        builder.setView(view)
        val timeOverOk = view.findViewById<Button>(R.id.timeOver_ok)
        val alertDialog = builder.create()
        timeOverOk.setOnClickListener {
            goNext()
            timeLeftMilliSeconds = countDownInMilliSecond
            statCountDownTimer()
            alertDialog.dismiss()
        }

        try {
            alertDialog.show()
        } catch (e: WindowManager.BadTokenException) {
            Log.i("TAG", "${e.message} ")
        }


    }

    private fun goNext() {
        if (hasFinished) {
            score = correct
            val intent = Intent(this@PlayActivityFull, ResultActivity::class.java)
            intent.putExtra("correct", correct)
            intent.putExtra("wrong", wrong)
            intent.putExtra("skip", skip)
            startActivity(intent)
            finish()
        }


    }
}