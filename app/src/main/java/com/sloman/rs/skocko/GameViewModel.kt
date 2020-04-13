package com.sloman.rs.skocko

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val gameRepository = GameRepository(GameDatabase.getInstance(application))


      private val _game = gameRepository.game

    val game: LiveData<GameWithGuesses?>
        get() = _game


    init {
        gameRepository.initialize()
    }

}