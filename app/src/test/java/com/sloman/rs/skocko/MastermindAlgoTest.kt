package com.sloman.rs.skocko

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class MastermindAlgoTest(
    private val expectedList: List<Int>,
    private val guessList: List<Int>,
    private val solutionList: List<Int>,
    private val scenario : String){

    companion object{

        @JvmStatic
        @Parameterized.Parameters(name = "checkGuess:{3}")
        fun rounds() = listOf(
            arrayOf(listOf(4,0), listOf(1,1,1,1), listOf(1,1,1,1), "All hits"),
            arrayOf(listOf(4,0), listOf(1,2,3,4), listOf(1,2,3,4), "All four different No hits"),
            arrayOf(listOf(4,0), listOf(4,1,4,1), listOf(4,1,4,1), "Two same, two different hits"),
            arrayOf(listOf(0,0), listOf(4,4,0,0), listOf(1,1,1,1), "Two same, two different no hits"),
            arrayOf(listOf(0,0), listOf(1,1,1,1), listOf(0,0,0,0), "All four non-matching"),
            arrayOf(listOf(0,0), listOf(1,2,3,4), listOf(5,5,5,5), "Different Nos, no matching"),
            arrayOf(listOf(2,0), listOf(1,1,0,3), listOf(1,1,4,2), "Two hits"),
            arrayOf(listOf(3,0), listOf(2,2,2,0), listOf(2,2,2,4), "Three hits"),
            arrayOf(listOf(0,4), listOf(1,2,3,4), listOf(4,3,2,1), "All wrong place hits"),
            arrayOf(listOf(0,1), listOf(1,0,0,0), listOf(2,2,2,1), "One wrong place hit"),
            arrayOf(listOf(2,2), listOf(2,3,4,5), listOf(2,3,5,4), "Two hits with two wrong place hits"),
            arrayOf(listOf(2,1), listOf(2,3,4,5), listOf(2,3,0,4), "Two hits with one wrong place hit"),
            arrayOf(listOf(2,1), listOf(2,3,4,5), listOf(2,3,0,4), "Two hits with one wrong place hit")

        )
    }

    @Test

    fun test_checkGuess(){

        require(guessList.size == Constants.GUESS_SIZE)
        require(solutionList.size == Constants.GUESS_SIZE)

        val actual = checkGuess(guessList, solutionList)

        assertEquals(expectedList, actual)
    }
}