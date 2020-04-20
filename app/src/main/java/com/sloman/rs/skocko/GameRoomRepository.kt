package com.sloman.rs.skocko

import androidx.lifecycle.LiveData

class GameRoomRepository(private val gameDao: GameDao) : GameRepository {

    val game: LiveData<GameWithGuesses?> = gameDao.getCurrentGame()


    override fun getCurrentGame(): LiveData<GameWithGuesses?> {
        return game
    }


    override fun insertGuess(guess: Guess) {
        ioThread {
            gameDao.insertGuess(guess)
        }
    }

    override fun setGameState(id: Int, state: String) {
        ioThread {
            gameDao.setGameState(id, state)
        }
    }

    override fun insertOnlyGame(game: Game) {
        ioThread {
            gameDao.insertOnlyGame(game)
        }
    }

}
