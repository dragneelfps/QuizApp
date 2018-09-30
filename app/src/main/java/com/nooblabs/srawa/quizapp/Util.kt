package com.nooblabs.srawa.quizapp

import android.app.Activity
import android.view.inputmethod.InputMethodManager


fun hideKeyboard(activity: Activity) {
    val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
    //Find the currently focused view, so we can grab the correct window token from it.
    activity.currentFocus?.let {
        imm?.hideSoftInputFromWindow(it.windowToken, 0)
    }
}