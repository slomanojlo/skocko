package com.sloman.rs.skocko.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sloman.rs.skocko.databinding.GuessItemBinding
import com.sloman.rs.skocko.model.Symbol

/** Used for creating, populating, modifying [RecyclerView] */
class GuessAdapter(private val onClickListener: OnClickListenerGuess) :
    ListAdapter<Symbol, GuessAdapter.GuessViewHolder>(
        DiffCallback
    ) {

    /** Allows the RecyclerView to determine which items have changed when the [List] of [Guess]
     * has been updated.*/
    class GuessViewHolder(private var binding: GuessItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(symbol: Symbol) {
            binding.property = symbol
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuessViewHolder {

        return GuessViewHolder(
            GuessItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    /** Replaces the contents of a view (invoked by the layout manager)*/
    override fun onBindViewHolder(holder: GuessViewHolder, position: Int) {
        val symbol = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(symbol)
        }
        holder.bind(symbol)
    }

    /** Allows the RecyclerView to determine which items have changed when the [List] of [Guess]
     * has been updated. Not really necessary in our case, but imprescindible for scaling up*/
    companion object DiffCallback : DiffUtil.ItemCallback<Symbol>() {

        override fun areItemsTheSame(oldItem: Symbol, newItem: Symbol): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Symbol, newItem: Symbol): Boolean {
            return oldItem.id == newItem.id
        }
    }


    /**Custom listener that handles clicks on [RecyclerView] items.  Passes the [Guess]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Guess]*/
    class OnClickListenerGuess(val clickListener: (symbol: Symbol) -> Unit) {
        fun onClick(symbol: Symbol) = clickListener(symbol)
    }

}