package com.gmail.cristiandeives.smashstat

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.gmail.cristiandeives.smashstat.data.Fighter
import java.text.NumberFormat

class FighterSpinnerAdapter(ctx: Context) : ArrayAdapter<Fighter>(ctx, android.R.layout.simple_spinner_item) {
    private val numberFormatter = NumberFormat.getInstance().apply {
        minimumIntegerDigits = 2
    }

    var data = listOf<Fighter>()

    init {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

    override fun getCount() = data.size

    override fun getItem(position: Int) = data[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val fighter = getItem(position)

        val res = context.resources
        (view as TextView).text = res.getString(fighter.nameRes)

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val fighter = getItem(position)

        var formattedId = numberFormatter.format(fighter.id)
        if (fighter.isEpsilon) {
            formattedId += EPSILON_SYMBOL
        }

        val res = context.resources
        (view as TextView).text = res.getString(R.string.fighter_name_with_id, formattedId, res.getString(fighter.nameRes))

        return view
    }

    companion object {
        private const val EPSILON_SYMBOL = "ε"
    }
}