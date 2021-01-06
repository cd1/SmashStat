package com.gmail.cristiandeives.smashstat

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment

class EditMatchActivity : SaveMatchActivity() {
    override fun createContentFragment(): Fragment {
        val matchId = intent.getIntExtra(EXTRA_MATCH_ID, -1)
        if (matchId < 0) {
            throw IllegalArgumentException("no match ID was specified ($matchId)")
        }

        return EditMatchFragment.newInstance(matchId)
    }

    companion object {
        private const val EXTRA_MATCH_ID = "matchId"

        fun createIntent(context: Context, matchId: Int) =
            Intent(context, EditMatchActivity::class.java).apply {
                putExtra(EXTRA_MATCH_ID, matchId)
            }
    }
}