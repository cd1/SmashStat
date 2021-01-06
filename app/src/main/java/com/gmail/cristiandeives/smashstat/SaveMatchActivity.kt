package com.gmail.cristiandeives.smashstat

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

abstract class SaveMatchActivity : AppCompatActivity(R.layout.activity_save_match) {
    abstract fun createContentFragment(): Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (supportFragmentManager.findFragmentByTag(FRAGMENT_TAG) == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container, createContentFragment(), FRAGMENT_TAG)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        Log.v(TAG, "> onSupportNavigateUp()")

        onBackPressed()

        val handled = true
        Log.v(TAG, "< onSupportNavigateUp(): $handled")
        return handled
    }

    companion object {
        private const val FRAGMENT_TAG = "saveMatchFragment"

        private val TAG = SaveMatchActivity::class.java.simpleName
    }
}