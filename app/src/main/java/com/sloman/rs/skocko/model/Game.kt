package com.sloman.rs.skocko.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "game")
data class Game constructor(
    @PrimaryKey (autoGenerate = true)
    val id: Int = 0,
    val solution : List<Int>,
    val state : String
)