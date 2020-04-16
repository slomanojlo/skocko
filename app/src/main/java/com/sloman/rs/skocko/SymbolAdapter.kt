package com.sloman.rs.skocko

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sloman.rs.skocko.databinding.SymbolItemBinding
import kotlinx.android.synthetic.main.symbol_item.view.*


class SymbolAdapter (private val onClickListener: OnClickListenerSymbol) :
    ListAdapter<Symbol, SymbolAdapter.SymbolViewHolder>(DiffCallback) {


    class SymbolViewHolder(private var binding: SymbolItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(symbol: Symbol) {
            binding.property = symbol
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): SymbolViewHolder {

        return SymbolViewHolder(SymbolItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: SymbolAdapter.SymbolViewHolder, position: Int) {
        val symbol = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(symbol)
        }
        holder.bind(symbol)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Symbol>() {

        override fun areItemsTheSame(oldItem: Symbol, newItem: Symbol): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Symbol, newItem: Symbol): Boolean {
            return oldItem.id == newItem.id
        }
    }


    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [MarsProperty]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [MarsProperty]
     */
    class OnClickListenerSymbol(val clickListener: (symbol: Symbol) -> Unit) {
        fun onClick(symbol: Symbol) = clickListener(symbol)
    }

}