package com.sloman.rs.skocko

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val gameRepository = GameRepository(GameDatabase.getInstance(application))

    private val _game = gameRepository.game

    val game: LiveData<GameWithGuesses?>
        get() = _game

    //TODO Make GuessId consistent
    var guessId = 0

    init {
        gameRepository.initialize()
    }

    fun makeGuess(inputText: String) {

        val guess = parseGuess(inputText)
        val hits = checkGuess(guess, game.value!!.game.solution)

//        guessId = if(game.value.guessList.isNotEmpty())
        guessId = if(game.value!!.guessList.isNotEmpty()) game.value!!.guessList.size + 1 else 1
        Log.d("Loguj", guessId.toString())

        gameRepository.insertGuess(
            Guess(guessId, game.value!!.game.id, guess, hits))

        if (hits[0] == 4)  {
            gameRepository.setGameState(game.value!!.game.id, "won")
            Log.d("Sloman", "Solution correct")
        } else {
            Log.d("Sloman", "Solution incorrect: " + game.value!!.game.solution.toString())

        }
    }

    fun playAgain() {
        gameRepository.playAgain(Game(game.value!!.game.id + 1, createRandomArray(), ""))
    }

    private fun createRandomArray(): List<Int> {

        return listOf(
            java.security.SecureRandom().nextInt(4),
            java.security.SecureRandom().nextInt(4),
            java.security.SecureRandom().nextInt(4),
            java.security.SecureRandom().nextInt(4)
        )
    }

    fun parseGuess(guess : String) : List<Int>{


        return listOf(
            guess.substring(0, 1).toInt(), guess.substring(1, 2).toInt(),
            guess.substring(2, 3).toInt(), guess.substring(3, 4).toInt())


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


        for((i, element) in guessList.withIndex()){
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