package com.nooblabs.srawa.quizapp.models.db

import android.arch.persistence.room.Room
import android.content.Context

class AppRepository private constructor() {

    companion object {
        private var INSTANCE: AppRepository? = null
        fun repository(): AppRepository {
            return INSTANCE ?: kotlin.run {
                INSTANCE = AppRepository()
                INSTANCE!!
            }
        }
    }

    private var DB_INSTANCE: AppDatabase? = null

    fun db(context: Context): AppDatabase {
        return DB_INSTANCE ?: kotlin.run {
            DB_INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, "quiz-db").allowMainThreadQueries().build()
            DB_INSTANCE!!
        }
    }


}