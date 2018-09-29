package com.nooblabs.srawa.quizapp.ui.quiz

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nooblabs.srawa.quizapp.R
import com.nooblabs.srawa.quizapp.models.Question
import kotlinx.android.synthetic.main.quiz_question_content.view.*

class QuizOngoingFragment: Fragment() {

    private var countQuestions: Int = -1
    companion object {
        const val QUESTION_COUNT = "ques_count"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            countQuestions = it.getInt(QUESTION_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.quiz_question_main, container, false)
    }

    fun showQuestion(quesIndex: Int, question: Question) {
        view?.let { view ->
            view.question_header.text = "Question ${quesIndex + 1} of $countQuestions"
            view.question_value.text = question.questionValue
            view.choice_1.text = question.options.option1
            view.choice_2.text = question.options.option2
            view.choice_3.text = question.options.option3
            view.choice_4.text = question.options.option4
            view.choice_5.text = question.options.option5
        }
    }

    fun getSelectedOption(): Int {
        return when(view!!.options_group.checkedRadioButtonId) {
            R.id.choice_1 -> 0
            R.id.choice_2 -> 1
            R.id.choice_3 -> 2
            R.id.choice_4 -> 3
            R.id.choice_5 -> 4
            else -> -1
        }
    }


}