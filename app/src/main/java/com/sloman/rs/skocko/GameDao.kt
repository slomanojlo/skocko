package com.sloman.rs.skocko

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GameDao {
    @Transaction
    @Query("SELECT * from game where id = (SELECT MAX(id) FROM game) LIMIT 1")
    fun getCurrentGame(): LiveData<GameWithGuesses?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOnlyGame(game: Game)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGuess(guess : Guess)

    @Query("UPDATE game set state = :state where id = :id")
    fun setGameState (id: Int, state: String) : Int


    @Transaction
    fun insertGame(game: Game, guess: Guess) {
        insertOnlyGame(game)
        insertGuess(guess)
    }
}