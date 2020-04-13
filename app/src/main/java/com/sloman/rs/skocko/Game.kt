package com.sloman.rs.skocko

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "game")
data class Game constructor(
    @PrimaryKey
    val id: Int,
    val solution : List<Int>,
    val state : String
)

