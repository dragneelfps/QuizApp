package com.nooblabs.srawa.quizapp.ui.loginsignup

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.nooblabs.srawa.quizapp.R
import com.nooblabs.srawa.quizapp.hideKeyboard
import com.nooblabs.srawa.quizapp.ui.HomeActivity
import kotlinx.android.synthetic.main.login_screen.view.*

class LoginFragment: Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.login_btn.setOnClickListener {
            showLoading()
            tryLogin()
            hideKeyboard(requireActivity())
        }
        view.sign_up_btn.setOnClickListener {
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.start_content_frame, SignUpFragment())
                addToBackStack(null)
                commit()
            }
        }
    }

    private fun tryLogin() {
        val email = view!!.email_value.text?.toString()
        val password = view!!.password_value.text?.toString()
        if (isCredentialsValid(email, password)) {
            auth.signInWithEmailAndPassword(email!!, password!!)
                    .addOnCompleteListener {
                        hideLoading()
                        if (it.isSuccessful) {
                            Toast.makeText(requireContext(), "Welcome back: ${auth.currentUser?.displayName}", Toast.LENGTH_LONG).show()
                            goToHome()
                        } else {
                            Toast.makeText(requireContext(), "Check credentials ${it.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
        } else {
            hideLoading()
        }
    }

    private fun isCredentialsValid(email: String?, password: String?): Boolean {
        view?.let { view ->
            var invalid = false
            if (TextUtils.isEmpty(email)) {
                view.email_container.error = "Enter Valid Email"
                invalid = true
            }
            if (TextUtils.isEmpty(password) || password!!.length < 6) {
                view.password_container.error = "Password should contain at least have 6 characters"
                invalid = true
            }
            return !invalid
        }
        return false
    }

    private fun goToHome() {
        val intent = Intent(requireContext(), HomeActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun showLoading() {
        view!!.progress_bar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        view!!.progress_bar.visibility = View.GONE
    }

}