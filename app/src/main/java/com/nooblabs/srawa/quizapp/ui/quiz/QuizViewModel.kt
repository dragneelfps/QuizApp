package com.nooblabs.srawa.quizapp.ui.quiz

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.nooblabs.srawa.quizapp.models.Question
import com.nooblabs.srawa.quizapp.models.db.AppRepository

class QuizViewModel(application: Application) : AndroidViewModel(application) {

    fun getQuestions(quizId: Long): LiveData<List<Question>> {
        val db = AppRepository.repository().db(getApplication())
        return db.questionDao().getAllQuestions(quizId)
    }

}