package com.sloman.rs.skocko

import org.junit.Assert.assertEquals
import org.junit.Test

class WrongPlaceEqualityPegsTest {


    val size = 4

    @Test
    fun testOneBadPlacePeg() {

        val guessArray = intArrayOf(0, 0, 1, 1)
        val solutionArray = intArrayOf(1, 2, 3, 4)
        val solutionMap: HashMap<Int, Int> = HashMap()

        val expectedExact = 0
        var actualExact = 0

        val expectedWrong = 1
        var actualWrong = 0

        solutionArray.forEachIndexed { i, element ->
            if (element == guessArray[i])
                actualExact++
            else
                solutionMap[element] = if (solutionMap.contains(element)) solutionMap.getValue(element) + 1 else 1
        }


        for((i, element) in guessArray.withIndex()){
            if (element == solutionArray[i]) continue

            if (solutionMap.containsKey(element)) {
                actualWrong++
                if (solutionMap[element] == 1) {
                    solutionMap.remove(element)
                } else
                    solutionMap[element] = solutionMap.getValue(element) - 1
            }
        }

        assertEquals(expectedExact, actualExact)
        assertEquals(expectedWrong, actualWrong)

    }
}

