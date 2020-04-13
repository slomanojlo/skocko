package com.sloman.rs.skocko

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [Game::class, Guess::class],
    version = 1,
    exportSchema = false)
@TypeConverters(Converter::class)
abstract class GameDatabase : RoomDatabase() {

    abstract val gameDao : GameDao

    companion object {

        private const val DB_NAME = "game_DB"
        val PREPOPULATE_GAME =
            Game(1, listOf(1, 2, 3, 4), "progress")

        val PREPOPULATE_GUESS =  listOf(Guess(1, 1, listOf(0, 0, 0, 0)), Guess(2, 1, listOf(0, 0, 0, 1)))
                @Volatile
                private var INSTANCE: GameDatabase? = null

                fun getInstance(context: Context): GameDatabase =
                    INSTANCE ?: synchronized(this) {
                        INSTANCE ?: create(context).also { INSTANCE = it }
                    }

                    private fun create(context: Context): GameDatabase {
                        return Room.databaseBuilder(
                            context,
                            GameDatabase::class.java,
                            DB_NAME
                        )
                            .addCallback(object : Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    // insert the data on the IO Thread
                                    ioThread {
                                        getInstance(context).gameDao.insertGame(PREPOPULATE_GAME, PREPOPULATE_GUESS)
                                    }
                                }
                            })
                            .build()
                    }

    }
}