package com.sloman.rs.skocko.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sloman.rs.skocko.util.Constants
import com.sloman.rs.skocko.util.Constants.GUESS_SIZE
import com.sloman.rs.skocko.model.Game
import com.sloman.rs.skocko.model.GameWithGuesses
import com.sloman.rs.skocko.model.Guess
import com.sloman.rs.skocko.model.Symbol
import com.sloman.rs.skocko.repo.GameRepository


/** Core of the app, where all the magic happens!*/
class GameViewModel(private val gameRepo : GameRepository) : ViewModel() {

    companion object {
        const val GUESS_MAX = 8 /**Maximum amount of guesses */
        const val SYMBOL_NO = 5 /**Number of symbols in game*/
    }

    /** Retrieve the current game and thus initialize the screen. */
    private val _game = gameRepo.getCurrentGame()

    /**GuessList of current guesses made by user*/
    val guessList: MutableLiveData<List<Symbol>> = MutableLiveData(mutableListOf())

    /** List of [Symbol] used in the game*/
    val symbolList: LiveData<List<Symbol>> = MutableLiveData(
        listOf(
            Symbol(0, Constants.SYMBOLS[0]),
            Symbol(1, Constants.SYMBOLS[1]),
            Symbol(2, Constants.SYMBOLS[2]),
            Symbol(3, Constants.SYMBOLS[3]),
            Symbol(4, Constants.SYMBOLS[4]),
            Symbol(5, Constants.SYMBOLS[5])
        )
    )

    val game: LiveData<GameWithGuesses?>
        get() = _game

    private var guessId = 0


    private fun insertOnlyGame(game : Game){
        gameRepo.insertOnlyGame(game)
    }

    fun setGameState(id : Int, state : String){
        gameRepo.setGameState(id, state)
    }

    private fun insertGuess(guess : Guess){
        gameRepo.insertGuess(guess)
    }

    /** Triggered when user chooses all symbols and hits the Guess button. */
    fun makeGuess() {

        val guess: MutableList<Int> = mutableListOf()

        for (symbol in guessList.value!!) {
            guess.add(symbol.id)
        }

        val hits = checkGuess(
            guess,
            game.value!!.game.solution
        )

        guessId =
            if (game.value!!.guessList.isNotEmpty()) game.value!!.guessList.size + 1 else 1


        insertGuess(
            Guess(
                guessId,
                game.value!!.game.id,
                guess,
                hits
            )
        )

        updateGameState(hits)
        clearGuess()
        Log.d("Slotest", "Correct solution: " + game.value!!.game.solution.toString())
    }

    /** Updates the game state when the game is over!*/
    private fun updateGameState(hits: List<Int>) {
        when {
            hits[0] == GUESS_SIZE -> setGameState(
                game.value!!.game.id,
                Constants.WON
            )
            guessId == GUESS_MAX -> setGameState(
                game.value!!.game.id,
                Constants.LOST
            )

        }
    }

    /** Triggered when clicked on a [Symbol], displaying it on the screen.*/
    fun addSymbol(input: Int) {

        if (guessList.value?.size != GUESS_SIZE) {
            val tempList: MutableList<Symbol> = (guessList.value!!).toMutableList()
            tempList.add(
                Symbol(
                    input,
                    Constants.SYMBOLS[input]
                )
            )
            guessList.value = tempList
        }
    }

    /** Triggered onButtonClick when the user wants to start a new game. */
    fun playAgain() {
        if (game.value!!.guessList.isNotEmpty()) {
            insertOnlyGame(
                Game(
                    0,
                    createRandomArray(),
                    ""
                )
            )
        }
        clearGuess()
    }

    /** Clears the current guess from the screen. */
    fun clearGuess() {
        guessList.value = mutableListOf()
    }

    private fun createRandomArray(): List<Int> {

        return listOf(
            java.security.SecureRandom().nextInt(SYMBOL_NO),
            java.security.SecureRandom().nextInt(SYMBOL_NO),
            java.security.SecureRandom().nextInt(SYMBOL_NO),
            java.security.SecureRandom().nextInt(SYMBOL_NO)
        )
    }
}

/** Algorithm that compares the [guessList] and the [solutionList]
 *  and returns a [List] of two [Int]: hits and wrong place hits */
fun checkGuess(guessList: List<Int>, solutionList: List<Int>): List<Int> {

    val solutionMap: HashMap<Int, Int> = HashMap()
    var exactMatch = 0
    var wrongPlaceMatch = 0


    solutionList.forEachIndexed { i, element ->
        if (element == guessList[i])
            exactMatch++
        else
            solutionMap[element] =
                if (solutionMap.contains(element)) solutionMap.getValue(element) + 1 else 1
    }


    for ((i, element) in guessList.withIndex()) {
        if (element == solutionList[i]) continue

        if (solutionMap.containsKey(element)) {
            wrongPlaceMatch++
            if (solutionMap[element] == 1) {
                solutionMap.remove(element)
            } else
                solutionMap[element] = solutionMap.getValue(element) - 1
        }
    }

    return listOf(exactMatch, wrongPlaceMatch)


}

/** Algorithm that determines if the current position
 *  shall be blank or display a hit / wrong place hit */
fun displayHit(hitsList: List<Int>, tag: Int): Int {
    return when {
        (hitsList[0] - 1 >= tag) -> Constants.HIT_SYMBOL
        (hitsList[1] - 1 + hitsList[0] >= tag) -> Constants.WRONG_POS_SYMBOL
        else -> Constants.NO_DISPLAY
    }
}