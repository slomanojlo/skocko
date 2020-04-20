package com.sloman.rs.skocko.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sloman.rs.skocko.databinding.GameItemBinding
import com.sloman.rs.skocko.model.Guess

/** Used for creating, populating, modifying [RecyclerView] */
class GameAdapter(private val onClickListener: OnClickListenerGuess) :
    ListAdapter<Guess, GameAdapter.GameViewHolder>(
        DiffCallback
    ) {

    /**The GameViewHolder constructor takes the binding variable from the associated
     * item, which nicely gives it access to the full [Guess] information.*/
    class GameViewHolder(private var binding: GameItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(guess: Guess) {
            binding.property = guess
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /** Allows the RecyclerView to determine which items have changed when the [List] of [Guess]
     * has been updated. Not really necessary in our case, but imprescindible for scaling up*/
    companion object DiffCallback : DiffUtil.ItemCallback<Guess>() {

        override fun areItemsTheSame(oldItem: Guess, newItem: Guess): Boolean {
            return oldItem.guessId == newItem.guessId
        }

        override fun areContentsTheSame(oldItem: Guess, newItem: Guess): Boolean {
            return oldItem.guesses == newItem.guesses
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): GameViewHolder {

        return GameViewHolder(
            GameItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    /** Replaces the contents of a view (invoked by the layout manager)*/
    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val guess = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(guess)
        }

        holder.bind(guess)
    }

    /**Custom listener that handles clicks on [RecyclerView] items.  Passes the [Guess]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Guess]*/
    class OnClickListenerGuess(val clickListener: (guess: Guess) -> Unit) {
        fun onClick(guess: Guess) = clickListener(guess)
    }

}