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
        Symbol(0, Constants.SYMBOLS[0]),
        Symbol(1, Constants.SYMBOLS[1]),
        Symbol(2, Constants.SYMBOLS[2]),
        Symbol(3, Constants.SYMBOLS[3]),
        Symbol(4, Constants.SYMBOLS[4]),
        Symbol(5, Constants.SYMBOLS[5])))

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

            guessId = if (game.value!!.guessList.isNotEmpty()) game.value!!.guessList.size + 1 else 1

            Log.d("Slotest", guessId.toString())

            gameRepository.insertGuess(
                Guess(guessId, game.value!!.game.id, guess, hits)
            )

            when {
                hits[0] == GUESS_SIZE -> gameRepository.setGameState(game.value!!.game.id, Constants.WON)
                guessId == GUESS_MAX -> gameRepository.setGameState(game.value!!.game.id, Constants.LOST)

            }
            clearGuess()
            Log.d("Slotest", "Correct solution: " + game.value!!.game.solution.toString())
        }
    }


    fun addSymbol(input: Int) {

        if(guessList.value?.size != GUESS_SIZE){
            val tempList: MutableList<Symbol> = (guessList.value!!).toMutableList()
            tempList.add(Symbol(input, Constants.SYMBOLS[input]))
            guessList.value = tempList
        }
    }

    fun playAgain() {
        if(game.value!!.guessList.isNotEmpty()){
            gameRepository.playAgain(Game(0, createRandomArray(), ""))
        }

        clearGuess()
    }

    fun clearGuess() {
        guessList.value = mutableListOf()
    }

    fun createRandomArray(): List<Int> {

        return listOf(
            java.security.SecureRandom().nextInt(SYMBOL_NO),
            java.security.SecureRandom().nextInt(SYMBOL_NO),
            java.security.SecureRandom().nextInt(SYMBOL_NO),
            java.security.SecureRandom().nextInt(SYMBOL_NO)
        )
    }


    fun checkGuess(guessList: List<Int>, solutionList: List<Int>): List<Int> {

        require(guessList.size == 4)
        require(solutionList.size == 4)

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
}