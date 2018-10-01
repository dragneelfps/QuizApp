package com.nooblabs.srawa.quizapp.models.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.nooblabs.srawa.quizapp.models.QuizStatistics
import io.reactivex.Single

@Dao
interface QuizStatisticsDao {

    @Insert
    fun insertQuizStats(quizStatistics: QuizStatistics)

    @Query("SELECT * FROM quizstatistics WHERE quizId = :quizId AND playerEmail = :playerEmail LIMIT 1")
    fun getQuizStatsLive(quizId: Long, playerEmail: String): Single<List<QuizStatistics>>

    @Update
    fun updateQuizStatistics(quizStatistics: QuizStatistics)

    @Query("SELECT * FROM quizstatistics WHERE quizId = :quizId AND playerEmail = :playerEmail")
    fun getAllStats(quizId: Long, playerEmail: String): LiveData<List<QuizStatistics>>

}