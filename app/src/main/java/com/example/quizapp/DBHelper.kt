package com.example.quizapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "quizapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "questions"
        private const val COL_ID = "id"
        private const val COL_QUESTION = "question"
        private const val COL_OPTION1 = "option1"
        private const val COL_OPTION2 = "option2"
        private const val COL_OPTION3 = "option3"
        private const val COL_OPTION4 = "option4"
        private const val COL_CORRECT_ANSWER = "correct_answer"
    }

    override fun onCreate(db: SQLiteDatabase?) {
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
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addQuestion(question: QuestionModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COL_QUESTION, question.question)
            put(COL_OPTION1, question.options[0])
            put(COL_OPTION2, question.options[1])
            put(COL_OPTION3, question.options[2])
            put(COL_OPTION4, question.options[3])
            put(COL_CORRECT_ANSWER, question.correctAnswer)
        }

        val result = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return result
    }

    fun getAllQuestions(): List<QuestionModel> {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)
        val questions = mutableListOf<QuestionModel>()

        if (cursor.moveToFirst()) {
            do {
                val questionText = cursor.getString(cursor.getColumnIndex(COL_QUESTION))
                val option1 = cursor.getString(cursor.getColumnIndex(COL_OPTION1))
                val option2 = cursor.getString(cursor.getColumnIndex(COL_OPTION2))
                val option3 = cursor.getString(cursor.getColumnIndex(COL_OPTION3))
                val option4 = cursor.getString(cursor.getColumnIndex(COL_OPTION4))
                val correctAnswer = cursor.getString(cursor.getColumnIndex(COL_CORRECT_ANSWER))

                val question = QuestionModel(questionText, listOf(option1, option2, option3, option4), correctAnswer)
                questions.add(question)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return questions
    }
}
