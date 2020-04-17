package com.sloman.rs.skocko

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class GameViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val GUESS_MAX = 8 //Maximum amount of guesses
        const val SYMBOL_NO = 5 //Number of symbols in game
        const val GUESS_SIZE = 4 //Number of symbols in one solution
    }

    private val gameRepository = GameRepository(GameDatabase.getInstance(application))

    private val _game = gameRepository.game

    val guessList: MutableLiveData<List<Symbol>> = MutableLiveData(mutableListOf())


    val symbolList: LiveData<List<Symbol>> = MutableLiveData(listOf(
        Symbol(0, R.drawable.ic_human),
        Symbol(1, R.drawable.ic_globe),
        Symbol(2, R.drawable.ic_android),
        Symbol(3, R.drawable.ic_shield),
        Symbol(4, R.drawable.ic_plane),
        Symbol(5, R.drawable.ic_tram)))

    val game: LiveData<GameWithGuesses?>
        get() = _game

    private var guessId = 0

    init {
        gameRepository.initialize()
    }

    fun makeGuess() {

        if (guessList.value!!.size == GUESS_SIZE) {

            val guess : MutableList<Int> = mutableListOf()

            for(symbol in guessList.value!!){
                guess.add(symbol.id)
            }


            val hits = checkGuess(guess, game.value!!.game.solution)

            guessId =
                if (game.value!!.guessList.isNotEmpty()) game.value!!.guessList.size + 1 else 1
            Log.d("Slotest", guessId.toString())

            gameRepository.insertGuess(
                Guess(guessId, game.value!!.game.id, guess, hits)
            )

            when {
                hits[0] == GUESS_SIZE -> gameRepository.setGameState(game.value!!.game.id, "won")
                guessId == GUESS_MAX -> gameRepository.setGameState(game.value!!.game.id, "lost")
                else -> clearGuess()
            }
            Log.d("Slotest", "Solution incorrect: " + game.value!!.game.solution.toString())
        }
    }


    fun addSymbol(input: Int) {

        if(guessList.value?.size != GUESS_SIZE){
            val tempList: MutableList<Symbol> = (guessList.value!!).toMutableList()
            tempList.add(Symbol(input, Constants.SYMBOLS[input]))
            guessList.value = tempList
            guessList.notifyObserver()
        }
    }

    fun playAgain() {
        gameRepository.playAgain(Game(game.value!!.game.id + 1, createRandomArray(), ""))
        clearGuess()
    }

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

    fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

}