package com.example.quizapp

import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReportActivity : AppCompatActivity() {

    private lateinit var layoutReport: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        layoutReport = findViewById(R.id.layoutReport)

        val answers = intent.getParcelableArrayListExtra<AnswerModel>("USER_ANSWERS")

        answers?.forEachIndexed { index, answer ->
            val questionView = TextView(this)
            questionView.text = "Q${index + 1}:"
            questionView.setTextColor(Color.BLACK)
            questionView.textSize = 18f

            val userAnsView = TextView(this)
            userAnsView.text = "Your Answer: ${answer.userAnswer}"
            userAnsView.setTextColor(if (answer.userAnswer == answer.correctAnswer) Color.GREEN else Color.RED)

            val correctAnsView = TextView(this)
            correctAnsView.text = "Correct Answer: ${answer.correctAnswer}"
            correctAnsView.setTextColor(Color.GREEN)

            layoutReport.addView(questionView)
            layoutReport.addView(userAnsView)
            layoutReport.addView(correctAnsView)
        }
    }
}
