package com.gmail.cristiandeives.smashstat

import android.util.Log
import android.view.Menu
import androidx.fragment.app.viewModels

class AddMatchFragment : SaveMatchFragment() {
    override val viewModel by viewModels<AddMatchViewModel>()

    override fun onPrepareOptionsMenu(menu: Menu) {
        Log.v(TAG, "> onPrepareOptionsMenu(menu=$menu)")
        super.onPrepareOptionsMenu(menu)

        menu.findItem(R.id.save_match).setTitle(R.string.add_match_menu)

        Log.v(TAG, "< onPrepareOptionsMenu(menu=$menu)")
    }

    companion object {
        private val TAG = AddMatchFragment::class.java.simpleName

        fun newInstance() = AddMatchFragment()
    }
}
