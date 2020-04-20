package com.sloman.rs.skocko.util

import com.sloman.rs.skocko.R

/**Location for storing constant values used throughout the app*/
object Constants {

    /**List of [Int] representing vector drawable ids in use*/
    val SYMBOLS: List<Int> = listOf(
        R.drawable.ic_skocko,
        R.drawable.ic_bear,
        R.drawable.ic_android,
        R.drawable.ic_pig,
        R.drawable.ic_bug,
        R.drawable.ic_human
    )

    const val HIT_SYMBOL = R.drawable.ic_hit
    const val WRONG_POS_SYMBOL = R.drawable.ic_wrong_place

    const val IN_PROGRESS = "in_progress"
    const val GAME_OVER = "game_over"
    const val WON = "won"
    const val LOST = "lost"
    const val SEPARATOR = "," /** Separator used for parsing lists to DB */
    const val GUESS_SIZE = 4 /**Number of symbols in one guess */
    const val NO_DISPLAY: Int = 0

}