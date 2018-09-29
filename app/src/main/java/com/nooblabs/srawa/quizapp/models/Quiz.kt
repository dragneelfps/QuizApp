package com.nooblabs.srawa.quizapp.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Quiz(var name: String, var createdByEmail: String) {
    @PrimaryKey(autoGenerate = true) var quizId: Long? = null
}