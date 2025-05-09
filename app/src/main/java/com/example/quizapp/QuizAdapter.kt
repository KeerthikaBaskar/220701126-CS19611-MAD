package com.example.quizapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*

class QuizAdapter(private val context: Context, private val quizzes: List<QuizResultModel>) : BaseAdapter() {

    override fun getCount(): Int = quizzes.size

    override fun getItem(position: Int): Any = quizzes[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val result = quizzes[position]
        val layout = view ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false)

        val title = layout.findViewById<TextView>(android.R.id.text1)
        val subtitle = layout.findViewById<TextView>(android.R.id.text2)

        val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        title.text = "Score: ${result.score}/${result.total}"
        subtitle.text = "Date: ${sdf.format(result.date)}"

        return layout
    }
}
