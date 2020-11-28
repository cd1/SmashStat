package com.gmail.cristiandeives.smashstat

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.gmail.cristiandeives.smashstat.data.Match
import com.gmail.cristiandeives.smashstat.databinding.ViewHolderMatchBinding
import java.text.NumberFormat

class MatchListAdapter : ListAdapter<Match, MatchViewHolder>(DiffUtilItemCallback) {
    private val percentFormat = NumberFormat.getPercentInstance()
    private val ratioFormat = NumberFormat.getNumberInstance().apply {
        minimumFractionDigits = 1
        maximumFractionDigits = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val view = ViewHolderMatchBinding.inflate(inflater, parent, false)
        return MatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val match = getItem(position)

        val context = holder.context

        val backgroundColorAttr = when (match.winner) {
            Match.Player.SELF -> R.attr.colorVictory
            Match.Player.THE_OTHER -> R.attr.colorDefeat
            else -> 0
        }

        if (backgroundColorAttr > 0) {
            val typedValue = TypedValue()
            context.theme.resolveAttribute(backgroundColorAttr, typedValue, true)

            holder.backgroundColor = ContextCompat.getColor(context, typedValue.resourceId)
        }

        val myCharacterNameRes = (match.myCharacter?.nameRes ?: R.string.my_character_generic)
        val myCharacterName = context.getString(myCharacterNameRes)

        val theirCharacterNameRes = (match.theirCharacter?.nameRes ?: R.string.their_character_generic)
        val theirCharacterName = context.getString(theirCharacterNameRes)

        holder.mainText = context.getString(R.string.match_characters_text, myCharacterName, theirCharacterName)

        val givenDamageStr = match.givenDamage.takeIf { it != null }?.let { damage ->
            percentFormat.format(damage / 100.0)
        } ?: context.getString(R.string.unknown_damage)

        val takenDamageStr = match.takenDamage.takeIf { it != null }?.let { damage ->
            percentFormat.format(damage / 100.0)
        } ?: context.getString(R.string.unknown_damage)

        val subText = if (match.givenDamage == null || match.takenDamage == null) {
            context.getString(R.string.match_damage_simple_text, givenDamageStr, takenDamageStr)
        } else {
            val ratio = ratioFormat.format((match.givenDamage.toDouble()) / (match.takenDamage.toDouble()))
            context.getString(R.string.match_damage_complete_text, givenDamageStr, takenDamageStr, ratio)
        }
        holder.subText = subText
    }

    override fun getItemViewType(position: Int) = (getItem(position).winner?.ordinal ?: -1)

    private object DiffUtilItemCallback : DiffUtil.ItemCallback<Match>() {
        override fun areItemsTheSame(oldItem: Match, newItem: Match) = (oldItem.id == newItem.id)

        override fun areContentsTheSame(oldItem: Match, newItem: Match) = (oldItem == newItem)
    }
}