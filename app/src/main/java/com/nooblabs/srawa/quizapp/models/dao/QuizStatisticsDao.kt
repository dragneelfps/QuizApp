package com.nooblabs.srawa.quizapp.models.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.nooblabs.srawa.quizapp.models.QuizStatistics

@Dao
interface QuizStatisticsDao {

    @Insert
    fun insertQuizStats(quizStatistics: QuizStatistics)

    @Query("SELECT * FROM quizstatistics WHERE quizId = :quizId AND playerEmail = :playerEmail LIMIT 1")
    fun getQuizStats(quizId: Long, playerEmail: String): QuizStatistics?

}