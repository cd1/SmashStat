package com.gmail.cristiandeives.smashstat

import android.content.Context
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.RecyclerView
import com.gmail.cristiandeives.smashstat.databinding.ViewHolderMatchBinding

class MatchViewHolder(private val binding: ViewHolderMatchBinding) : RecyclerView.ViewHolder(binding.root) {
    val context: Context = binding.root.context

    var mainText: String
        get() = binding.mainText.text.toString()

        set(value) {
            binding.mainText.text = value
        }

    var subText: String
        get() = binding.subText.text.toString()

        set(value) {
            binding.subText.text = value
        }

    var backgroundColor: Int
        get() = (binding.root.background as ColorDrawable).color

        set(value) {
            binding.root.setBackgroundColor(value)
        }
}