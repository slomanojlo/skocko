package com.sloman.rs.skocko.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.sloman.rs.skocko.R
import com.sloman.rs.skocko.adapter.GameAdapter
import com.sloman.rs.skocko.adapter.GuessAdapter
import com.sloman.rs.skocko.adapter.SymbolAdapter
import com.sloman.rs.skocko.databinding.FragmentGameBinding
import com.sloman.rs.skocko.util.obtainViewModel
import com.sloman.rs.skocko.viewmodel.GameViewModel

/** Fragment with minimal responsibilities: bind the [GameViewModel] and setup adapters / listeners*/
class GameFragment : Fragment() {

private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /** Instantiating or retrieving our [GameViewModel]*/
        viewModel = obtainViewModel(GameViewModel::class.java)

        val binding = FragmentGameBinding.inflate(inflater)

        /** Allows Data Binding to Observe LiveData with the lifecycle of this Fragment */
        binding.lifecycleOwner = this

        /** Giving the binding access to the OverviewViewModel */
        binding.viewModel = viewModel


        binding.rwGuesses.adapter =
            GameAdapter(GameAdapter.OnClickListenerGuess {})
        binding.rwSymbols.adapter =
            SymbolAdapter(SymbolAdapter.OnClickListenerSymbol {
                viewModel.addSymbol(it.id)
            })
        binding.rwGuessTry.adapter =
            GuessAdapter(GuessAdapter.OnClickListenerGuess { viewModel.clearGuess() })

        binding.btnGuess.setOnClickListener { viewModel.makeGuess()}

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_game, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_replay -> {
                viewModel.playAgain()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}



