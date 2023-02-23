package com.ju.simplequiz2204

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ju.simplequiz2204.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var nameList = listOf<String>("Mehedi", "Masum", "Avijit", "Erina", "Ismam", "Mubassir",
        "Mehedi", "Masum", "Avijit", "Erina", "Ismam", "Mubassir",
        "Mehedi", "Masum", "Avijit", "Erina", "Ismam", "Mubassir",
        "Mehedi", "Masum", "Avijit", "Erina", "Ismam", "Mubassir",
        "Mehedi", "Masum", "Avijit", "Erina", "Ismam", "Mubassir",

        )

    lateinit var nameAdapter: NameAdapter


    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nameAdapter = NameAdapter()
        nameAdapter.submitList(nameList)

        binding.nameRCV.adapter = nameAdapter


    }
}