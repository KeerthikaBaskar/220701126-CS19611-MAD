package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    private lateinit var tvScore: TextView
    private lateinit var btnRetry: Button
    private lateinit var btnDashboard: Button
    private lateinit var btnReport: Button

    private var score: Int = 0
    private var total: Int = 0
    private var answers: ArrayList<AnswerModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        tvScore = findViewById(R.id.tvScore)
        btnRetry = findViewById(R.id.btnRetry)
        btnDashboard = findViewById(R.id.btnDashboard)
        btnReport = findViewById(R.id.btnReport)

        score = intent.getIntExtra("SCORE", 0)
        total = intent.getIntExtra("TOTAL", 0)
        answers = intent.getParcelableArrayListExtra("USER_ANSWERS")

        tvScore.text = "Your Score: $score / $total"

        btnRetry.setOnClickListener {
            startActivity(Intent(this, TakeQuizActivity::class.java))
            finish()
        }

        btnDashboard.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }

        btnReport.setOnClickListener {
            val intent = Intent(this, ReportActivity::class.java)
            intent.putParcelableArrayListExtra("USER_ANSWERS", answers)
            startActivity(intent)
        }
    }
}
