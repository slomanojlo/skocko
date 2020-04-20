package com.sloman.rs.skocko.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sloman.rs.skocko.viewmodel.GameViewModel
import com.sloman.rs.skocko.repo.GameRepository

/** Custom Factory to retrieve or create (only when needed) the [ViewModel]  */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(private val gameRepo: GameRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(GameViewModel::class.java) ->
                    GameViewModel(gameRepo)
                else ->
                    throw IllegalArgumentException("ViewModel class (${modelClass.name}) is not mapped")
            }
        } as T
}