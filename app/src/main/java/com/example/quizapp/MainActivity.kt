package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvQuestion: TextView
    private lateinit var btnOption1: Button
    private lateinit var btnOption2: Button
    private lateinit var btnOption3: Button
    private lateinit var btnOption4: Button
    private lateinit var btnNext: Button

    private lateinit var questions: List<QuestionModel>
    private var currentQuestionIndex = 0
    private var selectedAnswer: String = ""
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvQuestion = findViewById(R.id.tvQuestion)
        btnOption1 = findViewById(R.id.btnOption1)
        btnOption2 = findViewById(R.id.btnOption2)
        btnOption3 = findViewById(R.id.btnOption3)
        btnOption4 = findViewById(R.id.btnOption4)
        btnNext = findViewById(R.id.btnNext)

        val dbHelper = QuizDbHelper(this)
        questions = dbHelper.getAllQuestions()

        if (questions.isEmpty()) {
            Toast.makeText(this, "No questions found. Please add some quizzes first.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        setQuestion()

        btnOption1.setOnClickListener { selectAnswer(btnOption1.text.toString()) }
        btnOption2.setOnClickListener { selectAnswer(btnOption2.text.toString()) }
        btnOption3.setOnClickListener { selectAnswer(btnOption3.text.toString()) }
        btnOption4.setOnClickListener { selectAnswer(btnOption4.text.toString()) }

        btnNext.setOnClickListener {
            if (selectedAnswer.isEmpty()) {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val correctAnswer = questions[currentQuestionIndex].correctAnswer
            if (selectedAnswer == correctAnswer) {
                score++
            }
            currentQuestionIndex++

            if (currentQuestionIndex < questions.size) {
                setQuestion()
            } else {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("score", score)
                intent.putExtra("total", questions.size)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun setQuestion() {
        selectedAnswer = ""
        val question = questions[currentQuestionIndex]
        tvQuestion.text = question.question
        btnOption1.text = question.options[0]
        btnOption2.text = question.options[1]
        btnOption3.text = question.options[2]
        btnOption4.text = question.options[3]
    }

    private fun selectAnswer(answer: String) {
        selectedAnswer = answer
    }
}
