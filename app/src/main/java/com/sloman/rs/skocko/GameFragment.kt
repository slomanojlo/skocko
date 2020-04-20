package com.sloman.rs.skocko

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sloman.rs.skocko.databinding.FragmentGameBinding

class GameFragment : Fragment() {

private lateinit var gameViewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        gameViewModel = obtainViewModel(GameViewModel::class.java)

        val binding = FragmentGameBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        val viewModel = ViewModelProvider(this, defaultViewModelProviderFactory)[GameViewModel::class.java]

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



