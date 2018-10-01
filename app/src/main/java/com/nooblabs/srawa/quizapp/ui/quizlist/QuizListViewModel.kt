package com.nooblabs.srawa.quizapp.ui.quizlist

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.nooblabs.srawa.quizapp.models.Quiz
import com.nooblabs.srawa.quizapp.models.QuizStatistics
import com.nooblabs.srawa.quizapp.models.db.AppRepository

class QuizListViewModel(application: Application) : AndroidViewModel(application) {

    fun getAllQuizzes(): LiveData<List<Quiz>> {
        val db = AppRepository.repository().db(getApplication())
        return db.quizDao().getAllQuizzes()
    }

    fun getQuizStat(quizId: Long): LiveData<List<QuizStatistics>> {
        val db = AppRepository.repository().db(getApplication())
        val auth = FirebaseAuth.getInstance()
        val playerEmail = auth.currentUser?.email ?: "dev"
        return db.quizStatisticsDao().getAllStats(quizId, playerEmail)
    }

    data class QuizStat(var quiz: Quiz, var successRate: Int, var totalPlayed: Int)


}