package com.sloman.rs.skocko.repo

import androidx.lifecycle.LiveData
import com.sloman.rs.skocko.db.GameDao
import com.sloman.rs.skocko.util.ioThread
import com.sloman.rs.skocko.model.Game
import com.sloman.rs.skocko.model.GameWithGuesses
import com.sloman.rs.skocko.model.Guess

/** Making the bond between the [GameRoomRepository] and the [GameDao] */
class GameRoomRepository(private val gameDao: GameDao) :
    GameRepository {

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
