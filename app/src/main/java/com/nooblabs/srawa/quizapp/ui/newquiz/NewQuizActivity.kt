package com.nooblabs.srawa.quizapp.ui.newquiz

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.nooblabs.srawa.quizapp.R
import kotlinx.android.synthetic.main.new_quiz_layout.*
import kotlinx.android.synthetic.main.new_quiz_list_item.view.*
import java.util.*

class NewQuizActivity : AppCompatActivity() {

    val questions = ArrayList<Question>()
    val questionsViews = HashMap<Long, View>()
    private lateinit var model: NewQuizViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_quiz_layout)

        initProperties()
        initListener()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_quiz_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        Log.d("dev", "AWDwaD")
        return when (item?.itemId) {
            R.id.save_quiz -> {
                Log.d("dev", "HERE")
                trySaving()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initProperties() {
        model = ViewModelProviders.of(this).get(NewQuizViewModel::class.java)
    }

    private fun initListener() {
        add_question_btn.setOnClickListener {
            val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val newQuestionView = inflater.inflate(R.layout.new_quiz_list_item, null)
            val randTag = Random().nextLong()
            questions_container.addView(newQuestionView)
            newQuestionView.delete_question_btn.setOnClickListener {
                questionsViews.remove(randTag)
                Log.d("dev", "removing with tag: ${randTag}")
                questions_container.removeView(newQuestionView)
                Log.d("dev", "after delete: ${questionsViews.size}")
            }
            Log.d("dev", "adding with tag: ${randTag}")
            questionsViews[randTag] = newQuestionView
            Log.d("dev", "after add: ${questionsViews.size}")
        }
    }

    private fun trySaving() {
        if (questionsViews.isEmpty()) {
            Toast.makeText(this, "Add at least one question to the quiz", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(quiz_title_value.text)) {
            quiz_title_value.error = "Enter Quiz Title"
            return
        } else {

            quiz_title_value.error = null
        }
        var isValid = true
        questionsViews.forEach { _, questionView ->
            Log.d("dev", "HERE")
            isValid = !TextUtils.isEmpty(questionView.question_value.text)
            if (!isValid) {
                questionView.question_value.error = "Invalid"
                return@forEach
            }
            Log.d("dev", "${questionView.options_group.checkedRadioButtonId}")
            isValid = questionView.options_group.checkedRadioButtonId != -1
            if (!isValid) {
                questionView.choice_1.error = "Select one choice"
                return@forEach
            } else {
                questionView.choice_1.error = null
            }
            isValid = !TextUtils.isEmpty(questionView.choice_1_value.text)
            if (!isValid) {
                questionView.choice_1_value.error = "Invalid"
                return@forEach
            } else {
                questionView.choice_1_value.error = null
            }
            isValid = !TextUtils.isEmpty(questionView.choice_2_value.text)
            if (!isValid) {
                questionView.choice_2_value.error = "Invalid"
                return@forEach
            } else {
                questionView.choice_2_value.error = null
            }
            isValid = !TextUtils.isEmpty(questionView.choice_3_value.text)
            if (!isValid) {
                questionView.choice_3_value.error = "Invalid"
                return@forEach
            } else {
                questionView.choice_3_value.error = null
            }
            isValid = !TextUtils.isEmpty(questionView.choice_4_value.text)
            if (!isValid) {
                questionView.choice_4_value.error = "Invalid"
                return@forEach
            } else {
                questionView.choice_4_value.error = null
            }
            isValid = !TextUtils.isEmpty(questionView.choice_5_value.text)
            if (!isValid) {
                questionView.choice_5_value.error = "Invalid"
                return@forEach
            } else {
                questionView.choice_5_value.error = null
            }
            questionView.apply {
                questions.add(Question(question_value.text.toString(),
                        choice_1_value.text.toString(), choice_2_value.text.toString(),
                        choice_3_value.text.toString(), choice_4_value.text.toString(),
                        choice_5_value.text.toString(), getCheckedOptionIndex(options_group.checkedRadioButtonId)))
            }

        }
        if (isValid) {
            saveData()
        } else {
            questions.clear()
            Toast.makeText(this, "Invalid data entered", Toast.LENGTH_LONG).show()
        }
    }

    private fun getCheckedOptionIndex(id: Int): Int {
        return when (id) {
            R.id.choice_1 -> 0
            R.id.choice_2 -> 1
            R.id.choice_3 -> 2
            R.id.choice_4 -> 3
            R.id.choice_5 -> 4
            else -> -1
        }
    }

    private fun saveData() {
        model.saveData(quiz_title_value.text!!.toString(), questions)
                .observe(this, Observer {
                    Log.d("dev", "YAY")
                    Log.d("dev", "$it")
                    Toast.makeText(this, "Created new quiz", Toast.LENGTH_LONG).show()
                    finish()
                })

    }


    data class Question(val question: String, val option1: String, val option2: String, val option3: String, val option4: String, val option5: String, val answer: Int)
}
