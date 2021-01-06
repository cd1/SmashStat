package com.gmail.cristiandeives.smashstat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.gmail.cristiandeives.smashstat.data.Fighter
import com.gmail.cristiandeives.smashstat.databinding.FragmentAddMatchBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import com.google.android.material.snackbar.Snackbar
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

class AddMatchFragment : Fragment(),
    View.OnClickListener,
    MaterialPickerOnPositiveButtonClickListener<Long> {

    private lateinit var binding: FragmentAddMatchBinding
    private val viewModel by viewModels<AddMatchViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Log.v(TAG, "> onCreateView(...)")

        binding = FragmentAddMatchBinding.inflate(inflater, container, false).apply {
            vm = viewModel

            lifecycleOwner = viewLifecycleOwner
        }

        val view = binding.root
        Log.v(TAG, "< onCreateView(...): $view")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.v(TAG, "> onViewCreated(...)")
        super.onViewCreated(view, savedInstanceState)

        val ctx = requireContext()

        binding.apply {
            dateButton.setOnClickListener(this@AddMatchFragment)
            myCharacterSpinner.adapter = FighterSpinnerAdapter(ctx).apply {
                data = Fighter.values().toList()
            }
            theirCharacterSpinner.adapter = FighterSpinnerAdapter(ctx).apply {
                data = Fighter.values().toList()
            }
            eliteLabel.setOnClickListener(this@AddMatchFragment)
            rematchLabel.setOnClickListener(this@AddMatchFragment)
            winnerLabel.setOnClickListener(this@AddMatchFragment)
            disconnectedLabel.setOnClickListener(this@AddMatchFragment)
            selfDeathLabel.setOnClickListener(this@AddMatchFragment)
            teabaggingLabel.setOnClickListener(this@AddMatchFragment)
            lagLabel.setOnClickListener(this@AddMatchFragment)
            funLabel.setOnClickListener(this@AddMatchFragment)
        }

        viewModel.addMatchState.observe(owner = viewLifecycleOwner) { res: Resource<*>? ->
            Log.v(TAG, "> addMatchState#onChanged(res=$res)")

            when (res) {
                is Resource.Loading -> onAddMatchLoading()
                is Resource.Success -> onAddMatchSuccess()
                is Resource.Error -> onAddMatchError(res)
            }

            Log.v(TAG, "< addMatchState#onChanged(res=$res)")
        }

        // reset the MaterialDatePicker listener after a configuration change (e.g. screen rotation)
        (parentFragmentManager.findFragmentByTag(SELECT_DATE_DIALOG_TAG) as? MaterialDatePicker<Long>)
                ?.addOnPositiveButtonClickListener(this)

        setHasOptionsMenu(true)

        Log.v(TAG, "< onViewCreated(...)")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        Log.v(TAG, "> onCreateOptionsMenu(...)")
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.fragment_add_match, menu)

        Log.v(TAG, "< onCreateOptionsMenu(...)")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.v(TAG, "> onOptionsItemSelected(item=$item)")

        val handled = when (item.itemId) {
            R.id.add_match -> {
                onAddButtonClick()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }

        Log.v(TAG, "< onOptionsItemSelected(item=$item): $handled")
        return handled
    }

    override fun onClick(view: View) {
        Log.v(TAG, "> onClick(view=$view)")

        when (view.id) {
            R.id.date_button -> onSelectDateButtonClick()
            R.id.elite_label -> binding.eliteValues.clearCheck()
            R.id.rematch_label -> binding.rematchValues.clearCheck()
            R.id.winner_label -> binding.winnerValues.clearCheck()
            R.id.disconnected_label -> binding.disconnectedValues.clearCheck()
            R.id.self_death_label -> binding.selfDeathValues.clearCheck()
            R.id.teabagging_label -> binding.teabaggingValues.clearCheck()
            R.id.lag_label -> binding.lagValues.clearCheck()
            R.id.fun_label -> binding.funValues.clearCheck()
            else -> {
                val idStr = resources.getResourceEntryName(view.id)
                throw IllegalArgumentException("click performed on an invalid ID [$idStr]")
            }
        }

        Log.v(TAG, "< onClick(view=$view)")
    }

    override fun onPositiveButtonClick(selection: Long) {
        Log.v(TAG, "> onPositiveButtonClick(selection=$selection)")

        val selectedDate = Instant.ofEpochMilli(selection).atZone(ZoneOffset.UTC).toLocalDate()
        Log.i(TAG, "user selected date=$selectedDate")
        viewModel.date.value = selectedDate

        Log.v(TAG, "< onPositiveButtonClick(selection=$selection)")
    }

    private fun onSelectDateButtonClick() {
        Log.i(TAG, "user started to select date")

        val currentDate = viewModel.date.value ?: LocalDate.now()
        val currentDateInUtcMilliseconds = currentDate
            .atStartOfDay()
            .atZone(ZoneOffset.UTC)
            .toInstant()
            .toEpochMilli()

        val constraints = CalendarConstraints.Builder()
            .setEnd(MaterialDatePicker.thisMonthInUtcMilliseconds())
            .setValidator(MaxDateValidator.untilToday())
            .build()
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(R.string.select_date_dialog_title)
            .setSelection(currentDateInUtcMilliseconds)
            .setCalendarConstraints(constraints)
            .build()
        datePicker.addOnPositiveButtonClickListener(this)
        datePicker.show(parentFragmentManager, SELECT_DATE_DIALOG_TAG)
    }

    private fun onAddButtonClick() {
        Log.i(TAG, "user tapped the add match button")
        viewModel.addMatch()
    }

    private fun setViewGroupEnabled(group: ViewGroup, isEnabled: Boolean) {
        for (child in group.children) {
            if (child is ViewGroup) {
                setViewGroupEnabled(child, isEnabled)
            }
            child.isEnabled = isEnabled
        }
    }

    private fun onAddMatchLoading() {
        binding.progressBar.isVisible = true
        setViewGroupEnabled(requireView() as ViewGroup, false)
    }

    private fun onAddMatchSuccess() {
        requireActivity().finish()
    }

    private fun onAddMatchError(res: Resource.Error<*>) {
        binding.progressBar.isVisible = false
        setViewGroupEnabled(requireView() as ViewGroup, true)

        res.exception?.consume()?.let { ex ->
            Snackbar.make(requireView(), getString(R.string.add_match_error, ex.message), Snackbar.LENGTH_SHORT).show()
        }
    }

    companion object {
        private val TAG = AddMatchFragment::class.java.simpleName

        private const val SELECT_DATE_DIALOG_TAG = "selectDate"
    }
}