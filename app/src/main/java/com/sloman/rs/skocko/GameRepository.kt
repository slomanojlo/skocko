package com.sloman.rs.skocko

import androidx.lifecycle.LiveData

class GameRepository(private val database: GameDatabase){

    val game: LiveData<GameWithGuesses?> = database.gameDao.getCurrentGame()


    fun initialize() {
        ioThread {
            database.gameDao.getCurrentGame()
        }

    }

    fun insertGuess(guess: Guess){
        ioThread {
            database.gameDao.insertGuess(guess)
        }
    }

    fun setGameState(id:  Int, state : String){
        ioThread {
            database.gameDao.setGameState(id, state)
        }
    }

    fun insertOnlyGame(game : Game){
        ioThread {
            database.gameDao.insertOnlyGame(game)
        }
    }

}
