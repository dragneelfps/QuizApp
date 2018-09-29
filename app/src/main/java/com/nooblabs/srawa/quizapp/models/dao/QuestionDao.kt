package com.nooblabs.srawa.quizapp.models.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.nooblabs.srawa.quizapp.models.Question

@Dao
interface QuestionDao {

    @Insert
    fun insertQuestion(question: Question)

    @Query("SELECT * FROM question WHERE quesId = :quesId LIMIT 1")
    fun getQuestion(quesId: Long): LiveData<Question>

    @Query("SELECT * FROM question WHERE quizId = :quizId")
    fun getAllQuestions(quizId: Long): LiveData<List<Question>>

}