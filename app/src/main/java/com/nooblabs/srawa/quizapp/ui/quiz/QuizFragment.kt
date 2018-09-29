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
import kotlinx.android.synthetic.main.quiz_screen.view.*

class QuizFragment: Fragment() {

    private var questions: List<Question>? = null
        set(value) {
            field = value
            value?.let {
                currQuestion = if(it.isNotEmpty()) 0 else null
            }
        }
    private var currQuestion: Int? = null
    private var quizId: Long? = null
    private lateinit var model: QuizViewModel
    private var quizOngoingFragment: QuizOngoingFragment? = null
    private var quizResultFragment: QuizResultFragment? = null
    private var quesStats = ArrayList<QuestionStat>()


    companion object {
        const val QUIZ_ID_KEY = "quiz_id"
        data class QuestionStat(val quesIndex: Int, val answer: Int)
        data class QuizResult(val totalQuestion: Int, val correctAnswers: Int) {
            val incorrectAnswers: Int = totalQuestion - correctAnswers
            val attemptedQuestions: Int = totalQuestion
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
        questions ?: currQuestion ?: return
        val questions = questions!!
        val currIndex = currQuestion!!



        if(currIndex == questions.size - 1) {
            Log.d("dev","quiz end reached")

            val quizStat = getQuizStat()

            this.currQuestion = null
            this.questions = null
            saveQuizStat(quizStat)
            showResult(quizStat)
        } else {
            Log.d("dev","go to next quest from curr = $currIndex")

            saveCurrQuestionOption(currIndex)

            val nextQuesIndex = currIndex + 1
            this.currQuestion = nextQuesIndex

            showNextQuestion()
        }
    }



    private fun saveQuizStat(quizStat: QuizResult?) {

    }

    private fun showNextQuestion(){
        quizOngoingFragment?.showQuestion(currQuestion!!, questions!![currQuestion!!])
    }

    private fun saveCurrQuestionOption(quesIndex: Int) {
        val selectedOption = quizOngoingFragment!!.getSelectedOption()
        quesStats.add(QuestionStat(quesIndex, selectedOption))
        Log.d("dev","Current ques stat: " )
        quesStats.forEach {
            Log.d("dev","$it" )
        }
    }

    private fun getQuizStat(): QuizResult? {
        questions ?: return null
        val questions = questions!!
        var correctAnswers: Int = questions.asSequence().filterIndexed { index, question -> question.correctOption == quesStats[index].answer }.count()
        return QuizResult(questions.size, correctAnswers)

    }

    private fun hideLoading() {

    }

    private fun showOngoingScreen(totalQuestion: Int) {
        quizOngoingFragment = QuizOngoingFragment()
        quizOngoingFragment!!.arguments = Bundle().apply {
            putInt(QuizOngoingFragment.QUESTION_COUNT, totalQuestion)
        }
        childFragmentManager.beginTransaction().replace(R.id.quiz_content_frame, quizOngoingFragment!!).commit()
    }

    private fun showResult(quizStat: QuizResult?) {
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
        }
    }

}