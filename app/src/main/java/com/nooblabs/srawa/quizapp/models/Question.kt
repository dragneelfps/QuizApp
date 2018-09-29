package com.nooblabs.srawa.quizapp.models

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = Quiz::class, parentColumns = arrayOf("quizId"), childColumns = arrayOf("quizId"),
        onDelete = ForeignKey.CASCADE)])
data class Question(var quizId: Long, var questionValue: String,
                    @Embedded var options: QuestionOptions,
                    var correctOption: Int) {
    @PrimaryKey(autoGenerate = true) var quesId: Long? = null
}