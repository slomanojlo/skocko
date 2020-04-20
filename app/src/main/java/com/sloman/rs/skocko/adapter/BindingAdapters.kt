package com.sloman.rs.skocko.adapter

import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sloman.rs.skocko.util.Constants.NO_DISPLAY
import com.sloman.rs.skocko.model.GameWithGuesses
import com.sloman.rs.skocko.model.Symbol
import com.sloman.rs.skocko.util.Constants
import com.sloman.rs.skocko.viewmodel.diplayHit

/**BindingAdapter for the game rounds RecyclerView ListAdapter having guesses and hits*/
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, gameList: GameWithGuesses?) {
    val adapter = recyclerView.adapter as GameAdapter

    adapter.submitList(gameList?.guessList)
}

/**BindingAdapter for the static [RecyclerView] ListAdapter displaying possible symbols to choose*/
@BindingAdapter("symbolData")
fun bindSymbolList(recyclerView: RecyclerView, symbolList: List<Symbol>?) {

    val adapter = recyclerView.adapter as SymbolAdapter
    adapter.submitList(symbolList)

}

/**BindingAdapter for the dynamic [RecyclerView] ListAdapter displaying user's current symbols choice*/
@BindingAdapter("guessData")
fun bindGuesslList(recyclerView: RecyclerView, symbolList: List<Symbol>?) {

    val adapter = recyclerView.adapter as GuessAdapter
    adapter.submitList(symbolList)

}

/**BindingAdapter for two [ConstraintLayout] to switch from in_progress to game_over mode  */
@BindingAdapter("checkStatus")
fun checkStatus(constraintLayout: ConstraintLayout, status: String?) {

    val tag = constraintLayout.tag.toString()

    if (tag == Constants.IN_PROGRESS)
        constraintLayout.visibility =
            if (status != null && status.isEmpty()) View.VISIBLE else View.INVISIBLE
    else if (tag == Constants.GAME_OVER)
        constraintLayout.visibility =
            if (status != null && status.isNotEmpty()) View.VISIBLE else View.INVISIBLE

}

/**BindingAdapter for [ImageView] used to inject images to views  */
@BindingAdapter("bindImageUrl")
fun bindImageUrl(imgView: ImageView, imgUri: Int) {
    imgUri.let {
        Glide.with(imgView.context)
            .load(Constants.SYMBOLS[imgUri])
            .into(imgView)
    }
}

/**BindingAdapter to display [ImageView] (hits or wrong place hits) using [diplayHit] algorithm */
@BindingAdapter("bindImageHit")
fun bindImageHit(imgView: ImageView, hitsList: List<Int>) {

    val tag =
        imgView.let { it.resources.getResourceName(it.id).split(":id/")[1].takeLast(1).toInt() }

    val hitSymbol = diplayHit(hitsList, tag)


    if (hitSymbol != NO_DISPLAY) {
        imgView.visibility = View.VISIBLE
        imgView.let {
            Glide.with(it.context)
                .load(hitSymbol)
                .into(it)
        }
    } else {
        imgView.visibility = View.INVISIBLE
    }
}

/**Enable [Button] if the [guessList] reached [Constants.GUESS_SIZE], otherwise disable */

@BindingAdapter("bindCheckGuess")
fun bindCheckGuess(btn: Button, guessList: List<Symbol>) {

    btn.isEnabled.apply { guessList.size == Constants.GUESS_SIZE }

}