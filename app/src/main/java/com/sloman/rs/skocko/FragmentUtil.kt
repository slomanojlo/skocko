package com.sloman.rs.skocko


import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun <T : ViewModel> Fragment.obtainViewModel(viewModelClass: Class<T>): T {
    val gameRepo = (this.activity?.application as GameApplication).gameRepo
    return ViewModelProvider(this, ViewModelFactory(gameRepo)).get(viewModelClass)


}

