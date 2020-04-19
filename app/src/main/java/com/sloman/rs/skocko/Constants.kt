package com.sloman.rs.skocko

object Constants {

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
    const val SEPARATOR = ","
    const val GUESS_SIZE = 4 //Number of symbols in one solution
    const val NO_DISPLAY: Int = 0

}