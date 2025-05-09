package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {

    private lateinit var tvHighScore: TextView
    private lateinit var btnStartQuiz: Button
    private lateinit var btnCreateQuiz: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        tvHighScore = findViewById(R.id.tvHighScore)
        btnStartQuiz = findViewById(R.id.btnStartQuiz)
        btnCreateQuiz = findViewById(R.id.btnCreateQuiz)

        // Load high score from SharedPreferences
        val prefs = getSharedPreferences("QuizApp", MODE_PRIVATE)
        val highScore = prefs.getInt("HIGH_SCORE", 0)
        tvHighScore.text = "High Score: $highScore"

        btnStartQuiz.setOnClickListener {
            startActivity(Intent(this, TakeQuizActivity::class.java))
        }

//        btnCreateQuiz.setOnClickListener {
//            startActivity(Intent(this, CreateQuizActivity::class.java))
//        }
    }
}
