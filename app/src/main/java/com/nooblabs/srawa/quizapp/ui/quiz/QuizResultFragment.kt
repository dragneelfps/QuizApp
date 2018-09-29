package com.nooblabs.srawa.quizapp.ui.quiz

import android.os.Bundle

import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nooblabs.srawa.quizapp.R
import kotlinx.android.synthetic.main.quiz_result_content.view.*

class QuizResultFragment: Fragment() {


    private var quizStat: QuizStat? = null

    companion object {
        data class QuizStat(val quizId: Long, val totalQuestions: Int, val attemptedQuestions: Int, val correctAnswers: Int) {
            val incorrectAnswers: Int = attemptedQuestions - correctAnswers
        }
        const val QUIZ_ID = "quiz_id"
        const val TOTAL_QUESTIONS = "tot"
        const val ATTEMPTED_QUESTIONS = "att"
        const val CORRECT_ANSWERS = "corr"
        const val SCORE_MULTIPLIER = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            quizStat = QuizStat(it.getLong(QUIZ_ID, -1), it.getInt(TOTAL_QUESTIONS, -1),
                    it.getInt(ATTEMPTED_QUESTIONS, -1), it.getInt(CORRECT_ANSWERS, -1))
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.quiz_result_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quizStat?.let { stat ->
            view.result_value.text = calcScore(stat)
            view.max_score_value.text = "/${calcMaxScore(stat)}"
            view.total_questions_value.text = "${stat.totalQuestions}"
            view.attempted_questions_value.text = "${stat.attemptedQuestions}"
            view.correct_answers_value.text = "${stat.correctAnswers}"
            view.wrong_answers_value.text = "${stat.incorrectAnswers}"
        }
    }

    private fun calcScore(stat: QuizStat): String {
        return (stat.correctAnswers * SCORE_MULTIPLIER).toString()
    }
    private fun calcMaxScore(stat: QuizStat): String {
        return (stat.totalQuestions * SCORE_MULTIPLIER).toString()
    }


}