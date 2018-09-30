package com.nooblabs.srawa.quizapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.nooblabs.srawa.quizapp.models.dev.initDB
import com.nooblabs.srawa.quizapp.ui.HomeActivity
import com.nooblabs.srawa.quizapp.ui.StartActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(isFirstTime()) {
            Log.d("dev","INIT DB START..")
            initDB(this, applicationContext)
            Log.d("dev","INIT DB DONE!")
            getPreferences(Context.MODE_PRIVATE).edit().apply {
                putBoolean(FIRST_TIME, false)
                apply()
            }

        }
        if(isAlreadyLoggedIn()) {
            goToHome()
        } else {
            goToLogin()
        }
    }

    companion object {
        const val FIRST_TIME = "first_time"
    }

    private fun isFirstTime(): Boolean {
        return getPreferences(Context.MODE_PRIVATE).getBoolean(FIRST_TIME, true)
    }

    private fun goToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun isAlreadyLoggedIn(): Boolean {
        val auth = FirebaseAuth.getInstance()
        return auth.currentUser != null
    }

    private fun goToLogin() {
        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
        finish()
    }
}
