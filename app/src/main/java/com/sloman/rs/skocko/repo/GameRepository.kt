package com.sloman.rs.skocko.repo

import androidx.lifecycle.LiveData
import com.sloman.rs.skocko.model.Game
import com.sloman.rs.skocko.model.GameWithGuesses
import com.sloman.rs.skocko.model.Guess

/** Implementing the repository pattern.
 * Not really needed at this stage, only when scaling up the data sources. */
interface GameRepository {

    fun getCurrentGame() : LiveData<GameWithGuesses?>

    fun insertGuess(guess: Guess)

    fun insertOnlyGame (game : Game)

    fun setGameState(id: Int, state: String)
}