package com.sloman.rs.skocko

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, gameList: GameWithGuesses?) {
    val adapter = recyclerView.adapter as GameAdapter

    adapter.submitList(gameList?.guessList)
}

@BindingAdapter("status")
fun bindStatus(stateTextView: TextView, status: String?) {
    when(status){
        "won" ->{
            stateTextView.visibility = View.VISIBLE
            stateTextView.text = "GAME WON"
        }
        else ->{
            stateTextView.visibility = View.INVISIBLE
            stateTextView.text = ""
        }
    }
}
