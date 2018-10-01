package com.nooblabs.srawa.quizapp.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.nooblabs.srawa.quizapp.R
import com.nooblabs.srawa.quizapp.ui.quizlist.QuizListFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.nav_header.view.*

class HomeActivity : AppCompatActivity() {

    private lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp)
        }

        initDefaults()
        initListeners()
    }

    private fun initDefaults(){
        user = FirebaseAuth.getInstance().currentUser!!
        nav_view.getHeaderView(0).user_value.text = user.displayName
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.content_frame, QuizListFragment())
            commit()
        }
        nav_view.setCheckedItem(R.id.show_quizzes_item)
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
                R.id.sign_out_item -> {
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(this, StartActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
