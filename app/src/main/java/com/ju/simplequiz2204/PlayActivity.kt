package com.ju.simplequiz2204

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.ju.simplequiz2204.databinding.ActivityPlayBinding

class PlayActivity : AppCompatActivity() {

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

    lateinit var binding: ActivityPlayBinding

    var quizIndex = 0
    var givenAnswer: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setQuiz(quizIndex)

        binding.nextQuizBtn.setOnClickListener {
            if (quizIndex < quizList.size) {
                setQuiz(quizIndex)
                quizIndex++
            } else {
                Toast.makeText(this, "No Question Available! ", Toast.LENGTH_SHORT).show()

            }


        }



        binding.radioGrp.setOnCheckedChangeListener { btn, id ->

            val clickedBtn = findViewById<RadioButton>(id)
            givenAnswer = clickedBtn.text.toString()
            checkAnswer(quizList[quizIndex].correctAnswer, givenAnswer)


        }


    }

    private fun setQuiz(quizIndex: Int) {

        var quiz: Quiz = quizList.get(quizIndex)
        binding.questionTv.text = quiz.question
        binding.option1.text = quiz.option1
        binding.option2.text = quiz.option2
        binding.option3.text = quiz.option3
        binding.option4.text = quiz.option4


    }

    private fun checkAnswer(correctAnswer: String, givenAnswer: String?) {

        if (correctAnswer == givenAnswer) {

            Toast.makeText(this,"Right Answer", Toast.LENGTH_SHORT).show()

           // binding.mainLayout.setBackgroundColor(getColor(R.color.green))
        } else {
            Toast.makeText(this,"Wrong Answer", Toast.LENGTH_SHORT).show()

            // binding.mainLayout.setBackgroundColor(getColor(R.color.red))
        }


    }
}