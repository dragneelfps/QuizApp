package com.nooblabs.srawa.quizapp.models.dev

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.nooblabs.srawa.quizapp.models.Question
import com.nooblabs.srawa.quizapp.models.QuestionOptions
import com.nooblabs.srawa.quizapp.models.Quiz
import com.nooblabs.srawa.quizapp.models.db.AppDatabase
import com.nooblabs.srawa.quizapp.models.db.AppRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class DBInitTask(val owner: AppCompatActivity, val context: Context): AsyncTask<Unit,Unit,Unit>() {
    override fun doInBackground(vararg params: Unit?) {
        initDB()
    }


    private fun getQuestions(_quizId: Long) = listOf<Question>(
            Question(_quizId, questionValue = "Atala Masjid which was built by Sultan Ibrahim is located at? ",
                    options = QuestionOptions("Jaunpur", "Kanpur", "Agra", "Mysore", "Delhi"), correctOption = 0),
            Question(_quizId, questionValue = "The caves and rock-cut temples at Ellora are? ",
                    options = QuestionOptions("Buddhist and Jain", "Hindu and Muslim", "Buddhist only", "Hindu, Buddhist and Jain", "Jain"), correctOption = 3),
            Question(_quizId, questionValue = "Who among the following built the famous Alai Darwaza? ",
                    options = QuestionOptions("Allaudin Khilji", "Babur", "Ibrahim Lodi", "Shahjahan", "Akbar"), correctOption = 0),
            Question(_quizId, questionValue = "Which Mughal ruler constructed A new city called as Din Panah on the bank of Yamuna river? ", options = QuestionOptions("Humayun", "Babur", "Jahangir", "Aurangzeb", "Akbar"), correctOption = 0)
    )

    private fun initDB() {
        val db = AppRepository.repository().db(context)
        dummy1(db)
        dummy2(db)

    }

    private fun dummy1(db: AppDatabase) {
        val _quiz = Quiz("Intro Quiz", "dev")

        Observable.fromCallable { db.quizDao().insertQuiz(_quiz) }
                .observeOn(Schedulers.io()).subscribeOn(Schedulers.io())
                .subscribe { _quizId ->
                    val _questions = getQuestions(_quizId)
                    Observable.fromCallable { db.questionDao().insertAllQuestions(*(_questions).toTypedArray()) }
                            .observeOn(Schedulers.io()).subscribeOn(Schedulers.io())
                            .subscribe {
                                db.quizDao().getAllQuizzes().observe(owner, Observer { quizzes ->
                                    quizzes ?: return@Observer
                                    Log.d("dev", "No. of quizzes : ${quizzes.size}")
                                    quizzes.forEach { quiz ->
                                        val questions = db.questionDao().getAllQuestions(quiz.quizId!!).observe(owner, Observer { questions ->
                                            questions ?: return@Observer
                                            questions.forEach {
                                                Log.d("dev", it.toString())
                                            }
                                        })
                                    }
                                })
                            }
                }

    }

    private fun dummy2(db: AppDatabase) {
        val _quiz = Quiz("Intro Quiz-2", "dev")

        Observable.fromCallable { db.quizDao().insertQuiz(_quiz) }
                .observeOn(Schedulers.io()).subscribeOn(Schedulers.io())
                .subscribe { _quizId ->
                    val _questions = getQuestions(_quizId)
                    Observable.fromCallable { db.questionDao().insertAllQuestions(*(_questions).toTypedArray()) }
                            .observeOn(Schedulers.io()).subscribeOn(Schedulers.io())
                            .subscribe {
                                db.quizDao().getAllQuizzes().observe(owner, Observer { quizzes ->
                                    quizzes ?: return@Observer
                                    Log.d("dev", "No. of quizzes : ${quizzes.size}")
                                    quizzes.forEach { quiz ->
                                        val questions = db.questionDao().getAllQuestions(quiz.quizId!!).observe(owner, Observer { questions ->
                                            questions ?: return@Observer
                                            questions.forEach {
                                                Log.d("dev", it.toString())
                                            }
                                        })
                                    }
                                })
                            }
                }

    }
}


fun initDB(owner: AppCompatActivity, context: Context) {
    DBInitTask(owner,context).execute()
}