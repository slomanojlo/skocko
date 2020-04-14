package com.sloman.rs.skocko

import androidx.lifecycle.LiveData

class GameRepository(private val database: GameDatabase){

    val game: LiveData<GameWithGuesses?> = database.gameDao.getAll()


    fun initialize() {
        ioThread {
            database.gameDao.getAll()
        }

    }

    fun insertGuess(guess: Guess){
        ioThread {
            database.gameDao.insertGuesses(listOf(guess))
        }
    }

    fun setGameState(id:  Int, state : String){
        ioThread {
            database.gameDao.setGameState(id, state)
        }
    }

    fun playAgain(game : Game){
        ioThread {
            database.gameDao.insertOnlyGame(game)
        }
    }

}
