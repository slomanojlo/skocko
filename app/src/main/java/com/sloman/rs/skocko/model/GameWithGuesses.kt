package com.sloman.rs.skocko.model

import androidx.room.Embedded
import androidx.room.Relation
import com.sloman.rs.skocko.model.Game
import com.sloman.rs.skocko.model.Guess

/** Created by joining [Game] and list of [Guess]. */
data class GameWithGuesses (
    @Embedded
    val game: Game,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val guessList : List<Guess>
)