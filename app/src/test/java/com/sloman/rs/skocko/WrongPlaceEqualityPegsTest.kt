package com.sloman.rs.skocko

import org.junit.Assert.assertEquals
import org.junit.Test

class WrongPlaceEqualityPegsTest {


    val size = 4

    @Test
    fun testOneBadPlacePeg() {

        val guessArray = intArrayOf(1, 1, 1, 1)
        val solutionArray = intArrayOf(1, 1, 1, 4)
        val solutionMap: HashMap<Int, Int> = HashMap()

        val expectedExact = 4
        var actualExact = 0

        val expectedWrong = 0
        var actualWrong = 0

        solutionArray.forEachIndexed { i, element ->
            if (element == guessArray[i])
                actualExact++
            else
                solutionMap[element] = if (solutionMap.contains(element)) solutionMap.getValue(element) + 1 else 1
        }


        guessArray.forEachIndexed { i, element ->
            if (element == solutionArray[i]) return

            if (solutionMap.containsKey(element)) {
                actualWrong++
                if (solutionMap[element] == 1) {
                    solutionMap.remove(i)
                } else
                    solutionMap[element] = solutionMap.getValue(element) - 1
            }
        }

        assertEquals(expectedExact, actualExact)
        assertEquals(expectedWrong, actualWrong)

    }
}

