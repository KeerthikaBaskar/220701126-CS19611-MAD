package com.example.quizapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CreateQuizActivity : AppCompatActivity() {

    private lateinit var etQuestion: EditText
    private lateinit var etOption1: EditText
    private lateinit var etOption2: EditText
    private lateinit var etOption3: EditText
    private lateinit var etOption4: EditText
    private lateinit var etCorrectAnswer: EditText
    private lateinit var btnSave: Button

    private lateinit var dbHelper: QuizDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_quiz)

        // Initializing Views
        etQuestion = findViewById(R.id.etQuestion)
        etOption1 = findViewById(R.id.etOption1)
        etOption2 = findViewById(R.id.etOption2)
        etOption3 = findViewById(R.id.etOption3)
        etOption4 = findViewById(R.id.etOption4)
        etCorrectAnswer = findViewById(R.id.etCorrectAnswer)
        btnSave = findViewById(R.id.btnSave)

        dbHelper = QuizDbHelper(this)

        btnSave.setOnClickListener {
            val questionText = etQuestion.text.toString()
            val option1 = etOption1.text.toString()
            val option2 = etOption2.text.toString()
            val option3 = etOption3.text.toString()
            val option4 = etOption4.text.toString()
            val correctAnswer = etCorrectAnswer.text.toString()

            if (questionText.isEmpty() || option1.isEmpty() || option2.isEmpty() || option3.isEmpty() || option4.isEmpty() || correctAnswer.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Saving the question to the database
            val question = QuestionModel(questionText, listOf(option1, option2, option3, option4), correctAnswer)
            dbHelper.addQuestion(question)

            Toast.makeText(this, "Question added successfully", Toast.LENGTH_SHORT).show()

            // Clear the fields
            etQuestion.text.clear()
            etOption1.text.clear()
            etOption2.text.clear()
            etOption3.text.clear()
            etOption4.text.clear()
            etCorrectAnswer.text.clear()
        }
    }
}
