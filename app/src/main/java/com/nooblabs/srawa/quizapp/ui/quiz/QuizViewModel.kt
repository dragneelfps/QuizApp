package com.nooblabs.srawa.quizapp.ui.quiz

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.nooblabs.srawa.quizapp.models.Question
import com.nooblabs.srawa.quizapp.models.QuizStatistics
import com.nooblabs.srawa.quizapp.models.db.AppRepository
import io.reactivex.schedulers.Schedulers

class QuizViewModel(application: Application) : AndroidViewModel(application) {

    fun getQuestions(quizId: Long): LiveData<List<Question>> {
        val db = AppRepository.repository().db(getApplication())
        return db.questionDao().getAllQuestions(quizId)
    }

    fun saveQuizStatistics(owner: QuizFragment, quizId: Long, quizStat: QuizFragment.Companion.QuizResult) {
        val auth = FirebaseAuth.getInstance()
        val playerEmail = auth.currentUser?.email ?: "dev"
        val qs = QuizStatistics(quizId, playerEmail, quizStat.isSuccessful(), 1)
        val db = AppRepository.repository().db(getApplication())

        db.quizStatisticsDao().getQuizStatsLive(quizId, playerEmail)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe { statsList ->
                    if(statsList.isEmpty()) {
                        db.quizStatisticsDao().insertQuizStats(qs)
                    } else {
                        val stats = statsList[0]
                        stats.gamesPlayed += 1
                        stats.successRate += quizStat.isSuccessful()
                        db.quizStatisticsDao().updateQuizStatistics(stats)
                    }
                }

    }

}