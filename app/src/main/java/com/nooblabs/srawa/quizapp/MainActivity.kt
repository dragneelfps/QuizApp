package com.nooblabs.srawa.quizapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nooblabs.srawa.quizapp.models.dev.initDB

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDB(applicationContext)

    }
}
