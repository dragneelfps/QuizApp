package com.nooblabs.srawa.quizapp.models.dev

import android.content.Context

fun initDB(context: Context) {
//    val db = AppRepository.repository().db(context)
//
//    //Creating dummy quiz
//
//    val _quiz = Quiz("Intro Quiz", "dev")
//
//    val _quizId = db.quizDao().insertQuiz(_quiz)
//
//    val _questions = listOf<Question>(Question(_quizId, questionValue = "What is your name?", options = QuestionOptions("a","b","c","d","e"), correctOption = 0),
//        Question(_quizId, questionValue = "What is your age?", options = QuestionOptions("a","b","c","d","e"), correctOption = 2),
//        Question(_quizId, questionValue = "Where do you live?", options = QuestionOptions("a","b","c","d","e"), correctOption = 1),
//        Question(_quizId, questionValue = "How old are you?", options = QuestionOptions("a","b","c","d","e"), correctOption = 3))
//
//    _questions.forEach { db.questionDao().insertQuestion(it) }
//
//
//    //Testing
//    val quizzes = db.quizDao().getAllQuizzes()
//    Log.d("dev","No. of quizzes : ${quizzes.size}")
//    quizzes.forEach { quiz ->
//        val questions = db.questionDao().getAllQuestions(quiz.quizId!!)
//        questions.forEach {
//            Log.d("dev", it.toString())
//        }
//    }


}