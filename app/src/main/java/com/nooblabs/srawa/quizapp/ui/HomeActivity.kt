package com.nooblabs.srawa.quizapp.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.nooblabs.srawa.quizapp.R
import com.nooblabs.srawa.quizapp.ui.quizlist.QuizListFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initDefaults()
        initListeners()
    }

    private fun initDefaults(){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.content_frame, QuizListFragment())
            commit()
        }
    }

    private fun initListeners(){
        nav_view.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            drawer_layout.closeDrawers()

            when(menuItem.itemId) {
                R.id.show_quizzes_item -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.content_frame, QuizListFragment())
                        commit()
                    }
                }
                R.id.past_quizzes_item -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.content_frame, Fragment())
                        commit()
                    }
                }
            }
            true
        }
    }
}
