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
import androidx.lifecycle.observe
import com.gmail.cristiandeives.smashstat.data.Fighter
import com.gmail.cristiandeives.smashstat.databinding.FragmentSaveMatchBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import com.google.android.material.snackbar.Snackbar
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

abstract class SaveMatchFragment : Fragment(),
    View.OnClickListener,
    MaterialPickerOnPositiveButtonClickListener<Long> {

    private lateinit var binding: FragmentSaveMatchBinding
    abstract val viewModel: SaveMatchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Log.v(TAG, "> onCreateView(...)")

        binding = FragmentSaveMatchBinding.inflate(inflater, container, false).apply {
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
            dateButton.setOnClickListener(this@SaveMatchFragment)
            myCharacterSpinner.adapter = FighterSpinnerAdapter(ctx).apply {
                data = Fighter.values().toList()
            }
            theirCharacterSpinner.adapter = FighterSpinnerAdapter(ctx).apply {
                data = Fighter.values().toList()
            }
            eliteLabel.setOnClickListener(this@SaveMatchFragment)
            rematchLabel.setOnClickListener(this@SaveMatchFragment)
            winnerLabel.setOnClickListener(this@SaveMatchFragment)
            disconnectedLabel.setOnClickListener(this@SaveMatchFragment)
            selfDeathLabel.setOnClickListener(this@SaveMatchFragment)
            teabaggingLabel.setOnClickListener(this@SaveMatchFragment)
            lagLabel.setOnClickListener(this@SaveMatchFragment)
            funLabel.setOnClickListener(this@SaveMatchFragment)
        }

        viewModel.saveMatchState.observe(owner = viewLifecycleOwner) { res: Resource<*>? ->
            Log.v(TAG, "> saveMatchState#onChanged(res=$res)")

            when (res) {
                is Resource.Loading -> onSaveMatchLoading()
                is Resource.Success -> onSaveMatchSuccess()
                is Resource.Error -> onSaveMatchError(res)
            }

            Log.v(TAG, "< saveMatchState#onChanged(res=$res)")
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

        inflater.inflate(R.menu.fragment_save_match, menu)

        Log.v(TAG, "< onCreateOptionsMenu(...)")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.v(TAG, "> onOptionsItemSelected(item=$item)")

        val handled = when (item.itemId) {
            R.id.save_match -> {
                onSaveButtonClick()

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

    private fun onSaveButtonClick() {
        Log.i(TAG, "user tapped the save match button")
        viewModel.saveMatch()
    }

    private fun setViewGroupEnabled(group: ViewGroup, isEnabled: Boolean) {
        for (child in group.children) {
            if (child is ViewGroup) {
                setViewGroupEnabled(child, isEnabled)
            }
            child.isEnabled = isEnabled
        }
    }

    protected fun setUILoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
        setViewGroupEnabled(requireView() as ViewGroup, !isLoading)
    }

    private fun onSaveMatchLoading() {
        setUILoading(true)
    }

    private fun onSaveMatchSuccess() {
        requireActivity().finish()
    }

    private fun onSaveMatchError(res: Resource.Error<*>) {
        setUILoading(false)

        res.exception?.consume()?.let { ex ->
            Snackbar.make(requireView(), getString(R.string.save_match_error, ex.message), Snackbar.LENGTH_SHORT).show()
        }
    }

    companion object {
        private val TAG = SaveMatchFragment::class.java.simpleName

        private const val SELECT_DATE_DIALOG_TAG = "selectDate"
    }
}