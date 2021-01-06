package com.gmail.cristiandeives.smashstat

class AddMatchActivity : SaveMatchActivity() {
    override fun createContentFragment() = AddMatchFragment.newInstance()
}