package com.sloman.rs.skocko.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sloman.rs.skocko.R

class GameActivity : AppCompatActivity() {

    /** Main entrance point to the game.*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
    }
}