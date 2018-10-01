package com.nooblabs.srawa.quizapp.ui.newquiz

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import com.google.firebase.auth.FirebaseAuth
import com.nooblabs.srawa.quizapp.models.Question
import com.nooblabs.srawa.quizapp.models.QuestionOptions
import com.nooblabs.srawa.quizapp.models.Quiz
import com.nooblabs.srawa.quizapp.models.db.AppRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class NewQuizViewModel(application: Application) : AndroidViewModel(application) {

    private fun saveQuiz(quizTitle: String): Single<Long> {
        val user = FirebaseAuth.getInstance().currentUser!!
        val db = AppRepository.repository().db(getApplication())
        return Single.fromCallable { db.quizDao().insertQuiz(Quiz(quizTitle, user.email!!)) }
    }

    fun saveData(quizTitle: String, questions: List<NewQuizActivity.Question>): LiveData<List<Long>> {
        val db = AppRepository.repository().db(getApplication())
        return LiveDataReactiveStreams.fromPublisher(saveQuiz(quizTitle).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).flatMap { newQuizId ->
            val questionDataModels = questions.map {
                Question(newQuizId, it.question, QuestionOptions(it.option1, it.option2, it.option3, it.option4, it.option5), it.answer)
            }.toTypedArray()

            Single.fromCallable { db.questionDao().insertAllQuestions(*questionDataModels) }
        }.toFlowable())

    }

}