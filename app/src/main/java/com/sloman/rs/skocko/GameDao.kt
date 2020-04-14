package com.sloman.rs.skocko

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GameDao {
    @Transaction
//    @Query("SELECT * from game where id = (SELECT guesses FROM guess where id = :id)")
    @Query("SELECT * from game where id = (SELECT MAX(id) FROM game) LIMIT 1")
    fun getAll(): LiveData<GameWithGuesses?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOnlyGame(game: Game)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGuesses(guessList : List<Guess>)

    @Query("UPDATE game set state = :state where id = :id")
    fun setGameState (id: Int, state: String)


    @Transaction
    fun insertGame(game: Game, guessList: List<Guess>) {
        insertOnlyGame(game)
        insertGuesses(guessList)
    }
}