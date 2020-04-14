package com.sloman.rs.skocko

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity (tableName = "guess",
    primaryKeys = ["guessId", "id"],
    foreignKeys = [ForeignKey(
        entity = Game::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("id"))])
data class Guess(
    val guessId: Int,
    val id: Int,
    val guesses: List<Int>,
    val hits: List<Int>
)