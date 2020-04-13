package com.sloman.rs.skocko

import androidx.lifecycle.LiveData

class GameRepository(private val database: GameDatabase){

    val game: LiveData<GameWithGuesses?> = database.gameDao.getAll(1)


    fun initialize() {
        ioThread {
            database.gameDao.getAll(1)
        }

    }

}
