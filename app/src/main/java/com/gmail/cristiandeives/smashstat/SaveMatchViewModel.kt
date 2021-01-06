package com.gmail.cristiandeives.smashstat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gmail.cristiandeives.smashstat.data.Fighter
import com.gmail.cristiandeives.smashstat.data.Match
import com.gmail.cristiandeives.smashstat.data.Repository
import java.time.LocalDate

abstract class SaveMatchViewModel(app: Application) : AndroidViewModel(app) {
    protected val repo = Repository.getInstance(app)

    protected val _saveMatchState = MutableLiveData<Resource<*>>()
    val saveMatchState: LiveData<Resource<*>> = _saveMatchState

    val date = MutableLiveData<LocalDate>().apply {
        value = LocalDate.now()
    }
    var dateValue: LocalDate?
        get() = (date.value ?: LocalDate.now())
        set(value) {
            date.value = value
        }

    val myCharacter = MutableLiveData<Fighter>().apply {
        value = DEFAULT_CHARACTER
    }
    var myCharacterValue: Fighter?
        get() = (myCharacter.value ?: DEFAULT_CHARACTER)
        set(value) {
            myCharacter.value = value
        }

    val theirCharacter = MutableLiveData<Fighter>().apply {
        value = DEFAULT_CHARACTER
    }
    var theirCharacterValue: Fighter?
        get() = (theirCharacter.value ?: DEFAULT_CHARACTER)
        set(value) {
            theirCharacter.value = value
        }

    val theirNickname = MutableLiveData<String>().apply {
        value = ""
    }
    var theirNicknameValue: String
        get() = theirNickname.value.orEmpty()
        set(value) {
            theirNickname.value = value
        }

    val eliteCheckedButton = MutableLiveData<Int>()
    var eliteValue: Boolean?
        get() = when (eliteCheckedButton.value) {
            R.id.elite_yes -> true
            R.id.elite_no -> false
            else -> null
        }
        set(value) {
            eliteCheckedButton.value = when (value) {
                true -> R.id.elite_yes
                false -> R.id.elite_no
                else -> null
            }
        }

    val rematchCheckedButton = MutableLiveData<Int>()
    var rematchValue: Boolean?
        get() = when (rematchCheckedButton.value) {
            R.id.rematch_yes -> true
            R.id.rematch_no -> false
            else -> null
        }
        set(value) {
            rematchCheckedButton.value = when (value) {
                true -> R.id.rematch_yes
                false -> R.id.rematch_no
                else -> null
            }
        }

    val winnerCheckedButton = MutableLiveData<Int>()
    var winnerValue: Match.Player?
        get() = when (winnerCheckedButton.value) {
            R.id.winner_self -> Match.Player.SELF
            R.id.winner_other -> Match.Player.THE_OTHER
            else -> null
        }
        set(value) {
            winnerCheckedButton.value = when (value) {
                Match.Player.SELF -> R.id.winner_self
                Match.Player.THE_OTHER -> R.id.winner_other
                else -> null
            }
        }

    val givenDamageText = MutableLiveData<String>()
    var givenDamageValue: Int?
        get() = givenDamageText.value?.toIntOrNull()
        set(value) {
            givenDamageText.value = value?.toString()
        }

    val takenDamageText = MutableLiveData<String>()
    var takenDamageValue: Int?
        get() = takenDamageText.value?.toIntOrNull()
        set(value) {
            takenDamageText.value = value?.toString()
        }

    val newGspText = MutableLiveData<String>()
    var newGspValue: Int?
        get() = newGspText.value?.toIntOrNull()
        set(value) {
            newGspText.value = value?.toString()
        }

    val disconnectedCheckedButton = MutableLiveData<Int>()
    var disconnectedValue: Match.Player?
        get() = when (disconnectedCheckedButton.value) {
            R.id.disconnected_self -> Match.Player.SELF
            R.id.disconnected_other -> Match.Player.THE_OTHER
            else -> null
        }
        set(value) {
            disconnectedCheckedButton.value = when (value) {
                Match.Player.SELF -> R.id.disconnected_self
                Match.Player.THE_OTHER -> R.id.disconnected_other
                else -> null
            }
        }

    var selfDeathCheckedButton = MutableLiveData<Int>()
    var selfDeathValue: Match.Player?
        get() = when (selfDeathCheckedButton.value) {
            R.id.self_death_self -> Match.Player.SELF
            R.id.self_death_other -> Match.Player.THE_OTHER
            else -> null
        }
        set(value) {
            selfDeathCheckedButton.value = when (value) {
                Match.Player.SELF -> R.id.self_death_self
                Match.Player.THE_OTHER -> R.id.self_death_other
                else -> null
            }
        }

    var teabaggingCheckedButton = MutableLiveData<Int>()
    var teabaggingValue: Match.Player?
        get() = when (teabaggingCheckedButton.value) {
            R.id.teabagging_self -> Match.Player.SELF
            R.id.teabagging_other -> Match.Player.THE_OTHER
            else -> null
        }
        set(value) {
            teabaggingCheckedButton.value = when (value) {
                Match.Player.SELF -> R.id.teabagging_self
                Match.Player.THE_OTHER -> R.id.teabagging_other
                else -> null
            }
        }

    var lagCheckedButton = MutableLiveData<Int>()
    var lagValue: Boolean?
        get() = when (lagCheckedButton.value) {
            R.id.lag_yes -> true
            R.id.lag_no -> false
            else -> null
        }
        set(value) {
            lagCheckedButton.value = when (value) {
                true -> R.id.lag_yes
                false -> R.id.lag_no
                else -> null
            }
        }

    var funCheckedButton = MutableLiveData<Int>()
    var funValue: Boolean?
        get() = when (funCheckedButton.value) {
            R.id.fun_yes -> true
            R.id.fun_no -> false
            else -> null
        }
        set(value) {
            funCheckedButton.value = when (value) {
                true -> R.id.fun_yes
                false -> R.id.fun_no
                else -> null
            }
        }

    protected fun matchValue() = Match(
        date = dateValue,
        myCharacter = myCharacterValue,
        theirCharacter = theirCharacterValue,
        theirNickname = theirNicknameValue,
        isEliteSmash = eliteValue,
        isRematch = rematchValue,
        winner = winnerValue,
        givenDamage = givenDamageValue,
        takenDamage = takenDamageValue,
        newGsp = newGspValue,
        disconnected = disconnectedValue,
        selfDeath = selfDeathValue,
        teabagging = teabaggingValue,
        lag = lagValue,
        funMatch = funValue,
    )

    abstract fun saveMatch()

    companion object {
        val DEFAULT_CHARACTER = Fighter.MARIO
    }
}