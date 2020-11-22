package com.gmail.cristiandeives.smashstat

import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class AddMatchActivity : AppCompatActivity(R.layout.activity_add_match) {
    override fun onSupportNavigateUp(): Boolean {
        Log.v(TAG, "> onSupportNavigateUp()")

        onBackPressed()

        val handled = true
        Log.v(TAG, "< onSupportNavigateUp(): $handled")
        return handled
    }

    companion object {
        private val TAG = AddMatchActivity::class.java.simpleName
    }
}