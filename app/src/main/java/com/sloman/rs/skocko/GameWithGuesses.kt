package com.sloman.rs.skocko

import androidx.room.Embedded
import androidx.room.Relation

data class GameWithGuesses (
    @Embedded
    val game: Game,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val guessList : List<Guess>
)