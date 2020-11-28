package com.gmail.cristiandeives.smashstat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.cristiandeives.smashstat.data.Match
import com.gmail.cristiandeives.smashstat.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Log.v(TAG, "> onCreateView(...)")

        binding = FragmentMainBinding.inflate(inflater, container, false)

        val view = binding.root
        Log.v(TAG, "< onCreateView(...): $view")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.v(TAG, "> onViewCreated(...)")
        super.onViewCreated(view, savedInstanceState)

        val matchAdapter = MatchListAdapter()
        binding.matchesRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = matchAdapter
        }

        viewModel.matches.observe(viewLifecycleOwner) { matches: List<Match>? ->
            Log.v(TAG, "> matches#observe(matches=#${matches?.size})")

            val actualMatches = (matches ?: emptyList())
            val hasData = actualMatches.isNotEmpty()

            matchAdapter.submitList(actualMatches)

            binding.apply {
                welcomeTextGroup.isVisible = !hasData
                matchesRecycler.isVisible = hasData
            }

            Log.v(TAG, "< matches#observe(matches=#${matches?.size})")
        }

        Log.v(TAG, "< onViewCreated(...)")
    }

    companion object {
        private val TAG = MainFragment::class.java.simpleName
    }
}