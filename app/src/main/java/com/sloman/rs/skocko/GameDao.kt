package com.sloman.rs.skocko

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GameDao {
    @Transaction
//    @Query("SELECT * from game where id = (SELECT guesses FROM guess where id = :id)")
    @Query("SELECT * from game where id = :id")
    fun getAll(id: Int): LiveData<GameWithGuesses?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOnlyGame(game: Game)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGuesses(guessList : List<Guess>)


    @Transaction
    fun insertGame(game: Game, guessList : List<Guess>){
        insertOnlyGame(game)
        insertGuesses(guessList)
    }
}