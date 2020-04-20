package com.sloman.rs.skocko.util

import android.app.Application
import com.sloman.rs.skocko.db.GameDatabase
import com.sloman.rs.skocko.repo.GameRepository
import com.sloman.rs.skocko.repo.GameRoomRepository

/** Top level class needed for our MVVM implementation. */
class GameApplication : Application() {

    val gameRepo: GameRepository
        get() = GameRoomRepository(
            GameDatabase.getInstance(
                this.applicationContext).gameDao
        )
}