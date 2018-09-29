package com.nooblabs.srawa.quizapp.models.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.nooblabs.srawa.quizapp.models.Question
import com.nooblabs.srawa.quizapp.models.Quiz
import com.nooblabs.srawa.quizapp.models.QuizStatistics
import com.nooblabs.srawa.quizapp.models.dao.QuestionDao
import com.nooblabs.srawa.quizapp.models.dao.QuizDao
import com.nooblabs.srawa.quizapp.models.dao.QuizStatisticsDao

@Database(entities = [Quiz::class, QuizStatistics::class, Question::class],
        version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun quizDao(): QuizDao
    abstract fun quizStatisticsDao(): QuizStatisticsDao
    abstract fun questionDao(): QuestionDao

}