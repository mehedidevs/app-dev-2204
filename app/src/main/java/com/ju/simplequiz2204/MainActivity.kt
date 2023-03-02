package com.ju.simplequiz2204

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ju.simplequiz2204.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var nameList = listOf<String>("Mehedi", "Masum", "Avijit", "Erina", "Ismam", "Mubassir")
    val celebList = listOf<Celeb>(
        Celeb("Amir Khan", amirBio, amirImg),
        Celeb("Akshay Kumar", akhshayBio, akhshayImg),
        Celeb(" Anushka Shetty", anushkaBio, anushkaBioImg)
    )


    lateinit var nameAdapter: NameAdapter
    lateinit var celebAdapter: CelebAdapter;


    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nameAdapter = NameAdapter()
        nameAdapter.submitList(nameList)
        celebAdapter = CelebAdapter(this@MainActivity)
        celebAdapter.submitList(celebList)
        binding.nameRCV.adapter = celebAdapter


    }
}