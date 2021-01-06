package com.gmail.cristiandeives.smashstat

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar

class EditMatchFragment : SaveMatchFragment() {
    override val viewModel by viewModels<EditMatchViewModel>(factoryProducer = { ViewModelFactory() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.v(TAG, "> onViewCreated(...)")
        super.onViewCreated(view, savedInstanceState)

        viewModel.readMatchState.observe(viewLifecycleOwner) { res: Resource<*>? ->
            Log.v(TAG, "> readMatchState#onChanged(t=$res)")

            when (res) {
                is Resource.Loading -> onReadMatchLoading()
                is Resource.Success -> onReadMatchSuccess()
                is Resource.Error -> onReadMatchError(res)
            }

            Log.v(TAG, "< readMatchState#onChanged(t=$res)")
        }

        Log.v(TAG, "< onViewCreated(...)")
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        Log.v(TAG, "> onPrepareOptionsMenu(menu=$menu)")
        super.onPrepareOptionsMenu(menu)

        menu.findItem(R.id.save_match).setTitle(R.string.edit_match_menu)

        Log.v(TAG, "< onPrepareOptionsMenu(menu=$menu)")
    }

    private fun onReadMatchLoading() {
        setUILoading(true)
    }

    private fun onReadMatchSuccess() {
        setUILoading(false)
    }

    private fun onReadMatchError(res: Resource.Error<*>) {
        setUILoading(false)

        res.exception?.consume()?.let { ex ->
            Snackbar.make(requireView(), getString(R.string.read_match_error, ex.message), Snackbar.LENGTH_SHORT).show()
        }
    }

    private inner class ViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            EditMatchViewModel(requireActivity().application, requireArguments().getInt(EXTRA_MATCH_ID)) as T
    }

    companion object {
        private const val EXTRA_MATCH_ID = "matchId"

        private val TAG = EditMatchFragment::class.java.simpleName

        fun newInstance(matchId: Int) = EditMatchFragment().apply {
            arguments = bundleOf(
                EXTRA_MATCH_ID to matchId
            )
        }
    }
}