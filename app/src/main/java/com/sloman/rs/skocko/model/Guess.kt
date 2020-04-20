package com.sloman.rs.skocko.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.sloman.rs.skocko.model.Game

@Entity (tableName = "guess",
    primaryKeys = ["guessId" , "id"],
    foreignKeys = [ForeignKey(
        entity = Game::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("id"))])
data class Guess(
    val guessId: Int,
    @ColumnInfo(index = true)
    val id: Int,
    val guesses: List<Int>,
    val hits: List<Int>
)