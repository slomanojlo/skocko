package com.sloman.rs.skocko

import android.app.Application

class GameApplication : Application() {

    val gameRepo: GameRepository
        get() = GameRoomRepository(GameDatabase.getInstance(this.applicationContext)!!.gameDao)
}