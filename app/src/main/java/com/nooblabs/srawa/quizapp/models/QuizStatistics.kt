package com.nooblabs.srawa.quizapp.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = Quiz::class, parentColumns = arrayOf("quizId"), childColumns = arrayOf("quizId"),
        onDelete = ForeignKey.CASCADE)])
data class QuizStatistics(var quizId: Long, var playerEmail: String, var successRate: Int, var gamesPlayed: Int) {
    @PrimaryKey(autoGenerate = true)
    var statId: Long? = null
}