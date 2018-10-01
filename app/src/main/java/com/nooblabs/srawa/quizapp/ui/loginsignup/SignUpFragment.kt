package com.nooblabs.srawa.quizapp.ui.loginsignup

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.nooblabs.srawa.quizapp.R
import com.nooblabs.srawa.quizapp.hideKeyboard
import com.nooblabs.srawa.quizapp.ui.HomeActivity
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
            addToBackStack(null)
            commit()
        }
    }

    private fun goToHome() {
        val intent = Intent(requireContext(), HomeActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun trySignUp() {
        val email = view!!.email_value.text?.toString()
        val password = view!!.password_value.text?.toString()
        val name = view!!.name_value.text?.toString()
        if (isCredentialsValid(email, password, name)) {
            auth.createUserWithEmailAndPassword(email!!, password!!)
                    .addOnCompleteListener {
                        hideLoading()
                        if (it.isSuccessful) {
                            val user = auth.currentUser!!
                            val profileUpdates = UserProfileChangeRequest.Builder()
                                    .setDisplayName(name!!)
                                    .build()
                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener {
                                        hideLoading()
                                        if (it.isSuccessful) {
                                            Toast.makeText(requireContext(), "Successfully created account", Toast.LENGTH_LONG).show()
                                            Toast.makeText(requireContext(), "Welcome : ${auth.currentUser?.displayName}", Toast.LENGTH_LONG).show()
                                            goToHome()
                                        } else {
                                            user.delete().addOnCompleteListener {
                                                if (it.isSuccessful) {
                                                    Toast.makeText(requireContext(), "Can't create account: ${it.exception?.message}", Toast.LENGTH_LONG).show()
                                                    Log.d("dev", "HERE :: ${it.exception?.message}")
                                                }
                                            }
                                        }
                                    }
                        } else {
                            Toast.makeText(requireContext(), "Can't create account: ${it.exception?.message}", Toast.LENGTH_LONG).show()
                            Log.d("dev", "HERE :: ${it.exception?.message}")
                        }
                    }
        } else {
            hideLoading()
        }
    }

    private fun isCredentialsValid(email: String?, password: String?, name: String?): Boolean {
        view?.let { view ->
            var invalid = false
            if (TextUtils.isEmpty(name)) {
                view.name_container.error = "Enter Valid Name"
                invalid = true
            }
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

    private fun showLoading() {
        view!!.progress_bar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        view!!.progress_bar.visibility = View.GONE
    }

}