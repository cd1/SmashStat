package com.gmail.cristiandeives.smashstat.data

import androidx.annotation.StringRes
import com.gmail.cristiandeives.smashstat.R

data class Fighter(val id: Int, @StringRes val nameRes: Int, val isEpsilon: Boolean = false) {
    companion object {
        val MARIO = Fighter(1, R.string.fighter_mario)
        val DONKEY_KONG = Fighter(2, R.string.fighter_donkey_kong)
        val LINK = Fighter(3, R.string.fighter_link)
        val SAMUS = Fighter(4, R.string.fighter_samus)
        val DARK_SAMUS = Fighter(4, R.string.fighter_dark_samus, true)
        val YOSHI = Fighter(5, R.string.fighter_yoshi)
        val KIRBY = Fighter(6, R.string.fighter_kirby)
        val FOX = Fighter(7, R.string.fighter_fox)
        val PIKACHU = Fighter(8, R.string.fighter_pikachu)
        val LUIGI = Fighter(9, R.string.fighter_luigi)
        val NESS = Fighter(10, R.string.fighter_ness)
        val CAPTAIN_FALCON = Fighter(11, R.string.fighter_captain_falcon)
        val JIGGLYPUFF = Fighter(12, R.string.fighter_jigglypuff)
        val PEACH = Fighter(13, R.string.fighter_peach)
        val DAISY = Fighter(13, R.string.fighter_daisy, true)
        val BOWSER = Fighter(14, R.string.fighter_bowser)
        val ICE_CLIMBERS = Fighter(15, R.string.fighter_ice_climbers)
        val SHEIK = Fighter(16, R.string.fighter_sheik)
        val ZELDA = Fighter(17, R.string.fighter_zelda)
        val DR_MARIO = Fighter(18, R.string.fighter_dr_mario)
        val PICHU = Fighter(19, R.string.fighter_pichu)
        val FALCO = Fighter(20, R.string.fighter_falco)
        val MARTH = Fighter(21, R.string.fighter_marth)
        val LUCINA = Fighter(21, R.string.fighter_lucina, true)
        val YOUNG_LINK = Fighter(22, R.string.fighter_young_link)
        val GANONDORF = Fighter(23, R.string.fighter_ganondorf)
        val MEWTWO = Fighter(24, R.string.fighter_mewtwo)
        val ROY = Fighter(25, R.string.fighter_roy)
        val CHROM = Fighter(25, R.string.fighter_chrom, true)
        val MR_GAME_AND_WATCH = Fighter(26, R.string.fighter_mr_game_and_watch)
        val META_KNIGHT = Fighter(27, R.string.fighter_meta_knight)
        val PIT = Fighter(28, R.string.fighter_pit)
        val DARK_PIT = Fighter(28, R.string.fighter_dark_pit, true)
        val ZERO_SUIT_SAMUS = Fighter(29, R.string.fighter_zero_suit_samus)
        val WARIO = Fighter(30, R.string.fighter_wario)
        val SNAKE = Fighter(31, R.string.fighter_snake)
        val IKE = Fighter(32, R.string.fighter_ike)
        val POKEMON_TRAINER = Fighter(33, R.string.fighter_pokemon_trainer)
        val DIDDY_KONG = Fighter(36, R.string.fighter_diddy_kong)
        val LUCAS = Fighter(37, R.string.fighter_lucas)
        val SONIC = Fighter(38, R.string.fighter_sonic)
        val KING_DEDEDE = Fighter(39, R.string.fighter_king_dedede)
        val OLIMAR = Fighter(40, R.string.fighter_olimar)
        val LUCARIO = Fighter(41, R.string.fighter_lucario)
        val ROB = Fighter(42, R.string.fighter_rob)
        val TOON_LINK = Fighter(43, R.string.fighter_toon_link)
        val WOLF = Fighter(44, R.string.fighter_wolf)
        val VILLAGER = Fighter(45, R.string.fighter_villager)
        val MEGA_MAN = Fighter(46, R.string.fighter_mega_man)
        val WII_FIT_TRAINER = Fighter(47, R.string.fighter_wii_fit_trainer)
        val ROSALINA_AND_LUMA = Fighter(48, R.string.fighter_rosalina_and_luma)
        val LITTLE_MAC = Fighter(49, R.string.fighter_little_mac)
        val GRENINJA = Fighter(50, R.string.fighter_greninja)
        val MII_BRAWLER = Fighter(51, R.string.fighter_mii_brawler)
        val MII_SWORDFIGHTER = Fighter(52, R.string.fighter_mii_swordfighter)
        val MII_GUNNER = Fighter(53, R.string.fighter_mii_gunner)
        val PALUTENA = Fighter(54, R.string.fighter_palutena)
        val PAC_MAN = Fighter(55, R.string.fighter_pac_man)
        val ROBIN = Fighter(56, R.string.fighter_robin)
        val SHULK = Fighter(57, R.string.fighter_shulk)
        val BOWSER_JR = Fighter(58, R.string.fighter_bowser_jr)
        val DUCK_HUNT = Fighter(59, R.string.fighter_duck_hunt)
        val RYU = Fighter(60, R.string.fighter_ryu)
        val KEN = Fighter(60, R.string.fighter_ken, true)
        val CLOUD = Fighter(61, R.string.fighter_cloud)
        val CORRIN = Fighter(62, R.string.fighter_corrin)
        val BAYONETTA = Fighter(63, R.string.fighter_bayonetta)
        val INKLING = Fighter(64, R.string.fighter_inkling)
        val RIDLEY = Fighter(65, R.string.fighter_ridley)
        val SIMON = Fighter(66, R.string.fighter_simon)
        val RICHTER = Fighter(66, R.string.fighter_richter, true)
        val KING_K_ROOL = Fighter(67, R.string.fighter_king_k_rool)
        val ISABELLE = Fighter(68, R.string.fighter_isabelle)
        val INCINEROAR = Fighter(69, R.string.fighter_incineroar)
        val PIRANHA_PLANT = Fighter(70, R.string.fighter_piranha_plant)
        val JOKER = Fighter(71, R.string.fighter_joker)
        val HERO = Fighter(72, R.string.fighter_hero)
        val BANJO_AND_KAZOOIE = Fighter(73, R.string.fighter_banjo_and_kazooie)
        val TERRY = Fighter(74, R.string.fighter_terry)
        val BYLETH = Fighter(75, R.string.fighter_byleth)
        val MIN_MIN = Fighter(76, R.string.fighter_min_min)
        val STEVE = Fighter(77, R.string.fighter_steve)

        val ALL_VALUES = listOf(
            MARIO,
            DONKEY_KONG,
            LINK,
            SAMUS,
            DARK_SAMUS,
            YOSHI,
            KIRBY,
            FOX,
            PIKACHU,
            LUIGI,
            NESS,
            CAPTAIN_FALCON,
            JIGGLYPUFF,
            PEACH,
            DAISY,
            BOWSER,
            ICE_CLIMBERS,
            SHEIK,
            ZELDA,
            DR_MARIO,
            PICHU,
            FALCO,
            MARTH,
            LUCINA,
            YOUNG_LINK,
            GANONDORF,
            MEWTWO,
            ROY,
            CHROM,
            MR_GAME_AND_WATCH,
            META_KNIGHT,
            PIT,
            DARK_PIT,
            ZERO_SUIT_SAMUS,
            WARIO,
            SNAKE,
            IKE,
            POKEMON_TRAINER,
            DIDDY_KONG,
            LUCAS,
            SONIC,
            KING_DEDEDE,
            OLIMAR,
            LUCARIO,
            ROB,
            TOON_LINK,
            WOLF,
            VILLAGER,
            MEGA_MAN,
            WII_FIT_TRAINER,
            ROSALINA_AND_LUMA,
            LITTLE_MAC,
            GRENINJA,
            MII_BRAWLER,
            MII_SWORDFIGHTER,
            MII_GUNNER,
            PALUTENA,
            PAC_MAN,
            ROBIN,
            SHULK,
            BOWSER_JR,
            DUCK_HUNT,
            RYU,
            KEN,
            CLOUD,
            CORRIN,
            BAYONETTA,
            INKLING,
            RIDLEY,
            SIMON,
            RICHTER,
            KING_K_ROOL,
            ISABELLE,
            INCINEROAR,
            PIRANHA_PLANT,
            JOKER,
            HERO,
            BANJO_AND_KAZOOIE,
            TERRY,
            BYLETH,
            MIN_MIN,
            STEVE,
        )
    }
}