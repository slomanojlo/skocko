package com.sloman.rs.skocko

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sloman.rs.skocko.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    /**
     * Lazily initialize our [GameViewModel]. n
     */
    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory)[GameViewModel::class.java]
    }

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentGameBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

//         Sets the adapter of the photosGrid RecyclerView with clickHandler lambda that
//         tells the viewModel when our property is clicked
        binding.rwGuesses.adapter = GameAdapter(GameAdapter.OnClickListenerGuess {})

        binding.rwSymbols.adapter = SymbolAdapter(SymbolAdapter.OnClickListenerSymbol { viewModel.addSymbol(it.id) })

        binding.rwGuessTry.adapter = GuessAdapter(GuessAdapter.OnClickListenerGuess { viewModel.clearGuess() })

        binding.btnGuess.setOnClickListener { viewModel.makeGuess()}

        binding.btnPlayAgain.setOnClickListener { viewModel.playAgain()}
        binding.btnPlayAgainGameOver.setOnClickListener { viewModel.playAgain()}



        return binding.root
    }

}



