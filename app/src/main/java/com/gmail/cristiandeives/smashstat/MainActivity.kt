package com.gmail.cristiandeives.smashstat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.gmail.cristiandeives.smashstat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),
    View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v(TAG, "> onCreate(...)")

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            fab.setOnClickListener(this@MainActivity)
        }

        Log.v(TAG, "< onCreate(...)")
    }

    override fun onClick(view: View) {
        Log.v(TAG, "> onClick(view=$view)")

        when (view.id) {
            R.id.fab -> onFabClick()
            else -> {
                val idStr = resources.getResourceEntryName(view.id)
                throw IllegalArgumentException("click performed on an invalid ID [$idStr]")
            }
        }

        Log.v(TAG, "< onClick(view=$view)")
    }

    private fun onFabClick() {
        Log.i(TAG, "user tapped the FAB")
        val intent = Intent(this, AddMatchActivity::class.java)
        startActivity(intent)
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}