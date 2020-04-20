package com.sloman.rs.skocko

import androidx.lifecycle.LiveData

interface GameRepository {

    fun getCurrentGame() : LiveData<GameWithGuesses?>

    fun insertGuess(guess: Guess)

    fun insertOnlyGame (game : Game)

    fun setGameState(id: Int, state: String)
}