package com.sloman.rs.skocko

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity (tableName = "guess",
    foreignKeys = [ForeignKey(
        entity = Game::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("id")
    )])
data class Guess(
    @PrimaryKey (autoGenerate = true)
    val guessId: Int,
    val id: Int,
    val guesses: List<Int>
)