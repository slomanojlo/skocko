package com.sloman.rs.skocko.util


import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/** Kotlin extension method (woohoo!) setting up [GameRepository] from [GameApplication].
 * It's one of the rare places in the app where the app context is used.
 * Than, the ViewModelProvider returns the correct [ViewModel] using [ViewModelFactory]*/
fun <T : ViewModel> Fragment.obtainViewModel(viewModelClass: Class<T>): T {
    val gameRepo = (this.activity?.application as GameApplication).gameRepo
    return ViewModelProvider(this,
        ViewModelFactory(gameRepo)
    ).get(viewModelClass)


}

