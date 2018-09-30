package com.nooblabs.srawa.quizapp.ui.quiz

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nooblabs.srawa.quizapp.R
import com.nooblabs.srawa.quizapp.models.Question
import com.nooblabs.srawa.quizapp.ui.quizlist.QuizListFragment
import kotlinx.android.synthetic.main.quiz_screen.view.*

class QuizFragment: Fragment() {

    private lateinit var questions: List<Question>
    private var currQuestion: Int = -1
    private var quizId: Long? = null
    private lateinit var model: QuizViewModel
    private var quizOngoingFragment: QuizOngoingFragment? = null
    private var quizResultFragment: QuizResultFragment? = null
    private var quesStats = HashMap<Long,Int>()


    companion object {
        const val QUIZ_ID_KEY = "quiz_id"
        data class QuizResult(val totalQuestion: Int, val attemptedQuestions: Int, val correctAnswers: Int) {
            val incorrectAnswers: Int = totalQuestion - correctAnswers
            fun isSuccessful(): Int {
                return if(totalQuestion == correctAnswers) 1 else 0
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            quizId = it.getLong(QUIZ_ID_KEY)
        }
        model = ViewModelProviders.of(this).get(QuizViewModel::class.java)
        quizId?.let { quizId ->
            model.getQuestions(quizId).observe(this, Observer { questions ->
                questions?.let {
                    this.questions = it
                    currQuestion = 0
                    showOngoingScreen(it.size)
                }
                hideLoading()
            })
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.quiz_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.next_btn.setOnClickListener {
            nextEvent()
        }
    }

    private fun nextEvent() {
        if(currQuestion == questions.size - 1) {
            Log.d("dev","quiz end reached")
            showResult()
        } else {
            Log.d("dev","go to next quest from curr = $currQuestion")
            currQuestion += 1
            showNextQuestion()
        }
    }

    private fun showNextQuestion(){
        quizOngoingFragment?.showQuestion(currQuestion, questions[currQuestion])
    }

    private fun hideLoading() {

    }

    private fun showOngoingScreen(totalQuestion: Int) {
        quizOngoingFragment = QuizOngoingFragment()
        quizOngoingFragment!!.optionSelectedListener = object : QuizOngoingFragment.OptionSelectedListener {
            override fun onOptionSelected(quesId: Long, selectedOption: Int) {
                quesStats[quesId] = selectedOption
            }
        }
        quizOngoingFragment!!.arguments = Bundle().apply {
            putInt(QuizOngoingFragment.QUESTION_COUNT, totalQuestion)
            putSerializable(QuizOngoingFragment.INIT_QUESTION, questions[0])
        }
        childFragmentManager.beginTransaction().replace(R.id.quiz_content_frame, quizOngoingFragment!!).commit()
    }

    private fun showResult(){
        val stats = calcStat()
        saveResultToDB(stats)
        goToResultScreen(stats)
    }

    private fun saveResultToDB(quizStat: QuizResult) {
        Log.d("dev","(saving to db)................")
        model.saveQuizStatistics(this, quizId!!, quizStat)
        Log.d("dev","(done: saved to db)................")
    }

    private fun calcStat(): QuizResult {
        var correctAnswers: Int = 0
        var attemptedQuestions: Int = 0
        questions.forEach {
            val selectedOption = quesStats[it.quesId]
            if( selectedOption != null) {
                attemptedQuestions += 1
                if(selectedOption == it.correctOption)
                    correctAnswers += 1
            }
        }
        return QuizResult(totalQuestion = questions.size, attemptedQuestions = attemptedQuestions, correctAnswers = correctAnswers)
    }

    private fun goToResultScreen(quizStat: QuizResult?) {
        quizStat?.let { quizStat ->
            quizResultFragment = QuizResultFragment().apply {
                arguments = Bundle().apply {
                    putLong(QuizResultFragment.QUIZ_ID, quizId!!)
                    putInt(QuizResultFragment.TOTAL_QUESTIONS, quizStat.totalQuestion)
                    putInt(QuizResultFragment.ATTEMPTED_QUESTIONS, quizStat.attemptedQuestions)
                    putInt(QuizResultFragment.CORRECT_ANSWERS, quizStat.correctAnswers)
                }
            }
            childFragmentManager.beginTransaction().replace(R.id.quiz_content_frame, quizResultFragment!!).commit()
            view!!.next_btn.apply {
                text = "Finish"
                setOnClickListener {
                    goToHomeScreen()
                }
            }
        }
    }

    private fun goToHomeScreen() {
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.content_frame, QuizListFragment())
            commit()
        }
    }

}