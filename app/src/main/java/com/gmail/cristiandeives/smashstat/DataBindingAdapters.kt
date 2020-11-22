package com.gmail.cristiandeives.smashstat

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.gmail.cristiandeives.smashstat.data.Fighter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

object DataBindingAdapters {
    @JvmStatic
    @BindingConversion
    fun formatLocalDateToString(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
            .withLocale(Locale.getDefault())
        return formatter.format(date)
    }

    @JvmStatic
    @BindingAdapter("fighter")
    fun Spinner.setFighter(fighter: Fighter) {
        for (index in 0 until adapter.count) {
            if (getItemAtPosition(index) == fighter) {
                setSelection(index)
                return
            }
        }
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "fighter")
    fun Spinner.getFighter() = selectedItem as Fighter

    @JvmStatic
    @BindingAdapter("fighterAttrChanged")
    fun Spinner.setFighterAttrChanged(listener: InverseBindingListener) {
        onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                listener.onChange()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                listener.onChange()
            }
        }
    }
}