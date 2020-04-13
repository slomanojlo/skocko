package com.sloman.rs.skocko

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, gameList: GameWithGuesses?) {
    val adapter = recyclerView.adapter as GameAdapter

    adapter.submitList(gameList?.guessList)
}
