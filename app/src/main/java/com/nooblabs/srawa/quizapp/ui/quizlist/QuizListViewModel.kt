package com.nooblabs.srawa.quizapp.ui.quizlist

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.util.Log
import com.nooblabs.srawa.quizapp.models.Quiz
import com.nooblabs.srawa.quizapp.models.db.AppRepository

class QuizListViewModel(application: Application) : AndroidViewModel(application) {

    private var quizList: LiveData<List<QuizStat>>? = null

    fun getQuizList(): LiveData<List<QuizStat>> {
        return quizList ?: kotlin.run {
            val db = AppRepository.repository().db(getApplication())
            val quizzesLiveDate = db.quizDao().getAllQuizzes()
            quizList = Transformations.map(quizzesLiveDate) { quizList ->
                Log.d("dev","Found ${quizList.size} quizzes in db")
                val quizzesStat = ArrayList<QuizStat>()
                quizList.forEach {
                    val tmp = db.quizStatisticsDao().getQuizStats(it.quizId!!, "dev")
                    val quizStat = QuizStat(it, tmp?.successRate ?: 0, tmp?.gamesPlayed ?: 0)
                    quizzesStat.add(quizStat)
                }
                quizzesStat
            }
            quizList!!
        }
    }

    data class QuizStat(var quiz: Quiz, var successRate: Int, var totalPlayed: Int)


}