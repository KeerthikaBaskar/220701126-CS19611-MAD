package com.example.quizapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class QuizDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "quiz.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "quiz"
        const val COL_ID = "id"
        const val COL_QUESTION = "question"
        const val COL_OPTION1 = "option1"
        const val COL_OPTION2 = "option2"
        const val COL_OPTION3 = "option3"
        const val COL_OPTION4 = "option4"
        const val COL_CORRECT_ANSWER = "correct_answer"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Create table query
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_QUESTION TEXT,
                $COL_OPTION1 TEXT,
                $COL_OPTION2 TEXT,
                $COL_OPTION3 TEXT,
                $COL_OPTION4 TEXT,
                $COL_CORRECT_ANSWER TEXT
            )
        """
        db?.execSQL(createTableQuery)

        // Insert predefined questions
        val insertQuery = """
            INSERT INTO $TABLE_NAME ($COL_QUESTION, $COL_OPTION1, $COL_OPTION2, $COL_OPTION3, $COL_OPTION4, $COL_CORRECT_ANSWER)
            VALUES
            ('What is the capital of France?', 'Berlin', 'Madrid', 'Paris', 'Rome', 'Paris'),
            ('What is 2 + 2?', '3', '4', '5', '6', '4'),
            ('Which planet is known as the Red Planet?', 'Earth', 'Mars', 'Jupiter', 'Saturn', 'Mars'),
            ('Who wrote the play "Romeo and Juliet"?', 'William Shakespeare', 'Charles Dickens', 'Jane Austen', 'George Orwell', 'William Shakespeare'),
            ('What is the largest mammal in the world?', 'Elephant', 'Blue Whale', 'Giraffe', 'Rhinoceros', 'Blue Whale'),
            ('In which year did the Titanic sink?', '1900', '1912', '1920', '1930', '1912'),
            ('What is the square root of 64?', '6', '8', '10', '12', '8'),
            ('Who discovered gravity?', 'Albert Einstein', 'Isaac Newton', 'Nikola Tesla', 'Galileo Galilei', 'Isaac Newton'),
            ('What is the hardest natural substance on Earth?', 'Gold', 'Diamond', 'Platinum', 'Iron', 'Diamond'),
            ('What is the smallest planet in our solar system?', 'Mercury', 'Venus', 'Mars', 'Pluto', 'Mercury')
        """
        db?.execSQL(insertQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Method to add a new question to the database
    fun addQuestion(question: QuestionModel) {
        val db = writableDatabase

        val values = ContentValues().apply {
            put(COL_QUESTION, question.question)
            put(COL_OPTION1, question.options[0])
            put(COL_OPTION2, question.options[1])
            put(COL_OPTION3, question.options[2])
            put(COL_OPTION4, question.options[3])
            put(COL_CORRECT_ANSWER, question.correctAnswer)
        }

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    // Method to retrieve all questions from the database
    fun getAllQuestions(): List<QuestionModel> {
        val questionList = mutableListOf<QuestionModel>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val question = cursor.getString(cursor.getColumnIndex(COL_QUESTION))
                val option1 = cursor.getString(cursor.getColumnIndex(COL_OPTION1))
                val option2 = cursor.getString(cursor.getColumnIndex(COL_OPTION2))
                val option3 = cursor.getString(cursor.getColumnIndex(COL_OPTION3))
                val option4 = cursor.getString(cursor.getColumnIndex(COL_OPTION4))
                val correctAnswer = cursor.getString(cursor.getColumnIndex(COL_CORRECT_ANSWER))

                questionList.add(QuestionModel(question, listOf(option1, option2, option3, option4), correctAnswer))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return questionList
    }
}
