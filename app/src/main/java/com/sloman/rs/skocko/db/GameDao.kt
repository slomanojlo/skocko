package com.sloman.rs.skocko.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sloman.rs.skocko.model.Game
import com.sloman.rs.skocko.model.GameWithGuesses
import com.sloman.rs.skocko.model.Guess


/** [Dao] interface for accessing the [Room] database */
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


    /** Insert a whole game with guesses. I was just playing around with some test cases
     * for [Transaction], didn't include them but left the [Dao] method. */
    @Transaction
    fun insertGame(game: Game, guess: Guess) {
        insertOnlyGame(game)
        insertGuess(guess)
    }
}