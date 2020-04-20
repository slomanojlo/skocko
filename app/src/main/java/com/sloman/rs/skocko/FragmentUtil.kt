package com.sloman.rs.skocko


import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun <T : ViewModel> Fragment.obtainViewModel(viewModelClass: Class<T>): T {
    val gameRepo = (this.activity?.application as GameApplication).gameRepo
    return ViewModelProvider(this, ViewModelFactory(gameRepo)).get(viewModelClass)


}

class GameApplication : Application() {

    val gameRepo: GameRepository
        get() = GameRoomRepository(GameDatabase.getInstance(this.applicationContext)!!.gameDao)
}