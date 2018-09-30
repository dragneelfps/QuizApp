package com.nooblabs.srawa.quizapp.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.nooblabs.srawa.quizapp.R
import com.nooblabs.srawa.quizapp.ui.loginsignup.LoginFragment

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_layout)
        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.start_content_frame, LoginFragment())
                commit()
            }
        }
    }
}
