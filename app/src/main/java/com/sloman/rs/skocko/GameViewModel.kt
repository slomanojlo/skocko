package com.sloman.rs.skocko

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class GameViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val GUESS_MAX = 8 //Maximum ammount of guesses
        const val SYMBOL_NO = 5 //Number of symbols in game
        const val GUESS_SIZE = 4 //Number of symbols in one solution
    }

    private val gameRepository = GameRepository(GameDatabase.getInstance(application))

    private val _game = gameRepository.game

    val symbol: MutableLiveData<String?> = MutableLiveData("")

    val symbolList: LiveData<List<Symbol>> = MutableLiveData(listOf(
        Symbol(0, R.drawable.ic_launcher_foreground),
        Symbol(1, R.drawable.ic_launcher_foreground),
        Symbol(2, R.drawable.ic_launcher_foreground),
        Symbol(3, R.drawable.ic_launcher_foreground),
        Symbol(4, R.drawable.ic_launcher_foreground),
        Symbol(5, R.drawable.ic_launcher_background)))

    val game: LiveData<GameWithGuesses?>
        get() = _game

    private var guessId = 0

    init {
        gameRepository.initialize()
    }

    fun makeGuess(inputText: String) {

        if (game.value!!.guessList.size != GUESS_MAX) {

            val guess = parseGuess(inputText)
            val hits = checkGuess(guess, game.value!!.game.solution)

            guessId =
                if (game.value!!.guessList.isNotEmpty()) game.value!!.guessList.size + 1 else 1
            Log.d("Slotest", guessId.toString())

            gameRepository.insertGuess(
                Guess(guessId, game.value!!.game.id, guess, hits)
            )

            when {
                hits[0] == GUESS_SIZE -> gameRepository.setGameState(game.value!!.game.id, "won")
                guessId == GUESS_MAX -1 -> gameRepository.setGameState(game.value!!.game.id, "lost")
                else -> clearGuess()
            }
            Log.d("Slotest", "Solution incorrect: " + game.value!!.game.solution.toString())
        }
    }


    fun addSymbol(input: String) {
        symbol.value = if (symbol.value?.length == 4) symbol.value else symbol.value + input
        Log.d("Slotest", symbol.value.toString())
    }

    fun playAgain() {
        gameRepository.playAgain(Game(game.value!!.game.id + 1, createRandomArray(), ""))
        clearGuess()
    }

    fun clearGuess() {
        symbol.value = ""
    }

    private fun createRandomArray(): List<Int> {

        return listOf(
            java.security.SecureRandom().nextInt(SYMBOL_NO),
            java.security.SecureRandom().nextInt(SYMBOL_NO),
            java.security.SecureRandom().nextInt(SYMBOL_NO),
            java.security.SecureRandom().nextInt(SYMBOL_NO)
        )
    }

    fun parseGuess(guess: String): List<Int> {

        return listOf(
            guess.substring(0, 1).toInt(), guess.substring(1, 2).toInt(),
            guess.substring(2, 3).toInt(), guess.substring(3, 4).toInt()
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

}