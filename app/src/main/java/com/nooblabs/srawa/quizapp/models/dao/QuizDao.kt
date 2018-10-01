package com.nooblabs.srawa.quizapp.models.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.nooblabs.srawa.quizapp.models.Quiz

@Dao
interface QuizDao {

    @Insert
    fun insertQuiz(quiz: Quiz): Long

    @Insert
    fun insertAllQuiz(vararg quiz: Quiz): List<Long>

    @Query("SELECT * FROM quiz")
    fun getAllQuizzes(): LiveData<List<Quiz>>
}