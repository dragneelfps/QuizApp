package com.nooblabs.srawa.quizapp.ui.loginsignup

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.nooblabs.srawa.quizapp.R
import com.nooblabs.srawa.quizapp.hideKeyboard
import kotlinx.android.synthetic.main.signup_screen.view.*

class SignUpFragment: Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.signup_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.sign_up_btn.setOnClickListener {
            showLoading()
            trySignUp()
            hideKeyboard(requireActivity())
        }
        view.login_btn.setOnClickListener {
            goToLogin()
        }
    }

    private fun goToLogin() {
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.start_content_frame, LoginFragment())
            commit()
        }
    }

    private fun trySignUp() {
        val email = view!!.email_value.text.toString()
        val password = view!!.password_value.text.toString()
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    hideLoading()
                    if(it.isSuccessful) {
                        Toast.makeText(requireContext(), "Successfully created account", Toast.LENGTH_LONG).show()
                        goToLogin()
                    } else {
                        Toast.makeText(requireContext(), "Can't create account: ${it.exception?.message}", Toast.LENGTH_LONG).show()
                        Log.d("dev","HERE :: ${it.exception?.message}")
                    }
                }
    }

    private fun showLoading() {
        view!!.progress_bar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        view!!.progress_bar.visibility = View.GONE
    }

}