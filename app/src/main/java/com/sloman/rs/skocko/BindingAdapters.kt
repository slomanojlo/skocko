package com.sloman.rs.skocko

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, gameList: GameWithGuesses?) {
    val adapter = recyclerView.adapter as GameAdapter

    adapter.submitList(gameList?.guessList)
}

@BindingAdapter("symbolData")
fun bindSymbolList(recyclerView: RecyclerView, symbolList: List<Symbol>?) {

    val adapter = recyclerView.adapter as SymbolAdapter
    adapter.submitList(symbolList)

}

@BindingAdapter("guessData")
fun bindGuesslList(recyclerView: RecyclerView, symbolList: List<Symbol>?) {

    val adapter = recyclerView.adapter as GuessAdapter
    adapter.submitList(symbolList)

}

@BindingAdapter("status")
fun bindStatus(stateTextView: TextView, status: String?) {
    when (status) {
        "won" -> {
            stateTextView.visibility = View.VISIBLE
            stateTextView.text = "GAME WON!"
        }
        "lost" -> {
            stateTextView.visibility = View.VISIBLE
            stateTextView.text = "GAME LOST"
        }
        else -> {
            stateTextView.visibility = View.INVISIBLE
            stateTextView.text = ""
        }
    }
}

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


@BindingAdapter("bindImageUrl")
fun bindImageUrl(imgView: ImageView, imgUrl: Int) {
    imgUrl.let {
        Glide.with(imgView.context)
            .load(Constants.SYMBOLS[imgUrl])
            .into(imgView)
    }
}

@BindingAdapter("bindImageHit")
fun bindImageHit(imgView: ImageView, hitsList: List<Int>) {

    val tag = imgView.resources.getResourceName(imgView.id).split(":id/")[1].takeLast(1).toInt();

    val hitSymbol = when {
        (hitsList[0] - 1 >= tag) -> Constants.HIT_SYMBOL
        (hitsList[1] - 1 + hitsList[0] >= tag) -> Constants.WRONG_POS_SYMBOL
        else -> 0
    }


    if (hitSymbol != 0) {
        imgView.visibility = View.VISIBLE
        imgView.let {
            Glide.with(imgView.context)
                .load(hitSymbol)
                .into(imgView)
        }
    } else {
        imgView.visibility = View.INVISIBLE
    }

}



