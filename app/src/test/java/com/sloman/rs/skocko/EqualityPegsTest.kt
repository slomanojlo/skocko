package com.sloman.rs.skocko

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class EqualityPegsTest {

    val size = 4

    @Test
    fun testEqualityZeroResults() {
        val guessArray = intArrayOf(1, 1, 1, 1)
        val solution = intArrayOf(0, 0, 0, 0)

        val expected = 0

        var actual : Int  = 0

        for (i in 0 until size) {


            if (guessArray[i] == solution[i]) {
                actual++
            }
        }

        assertEquals(expected, actual)

    }

    @Test
    fun testEqualityOneResult() {
        val guessArray = intArrayOf(0, 1, 1, 1)
        val solution = intArrayOf(0, 0, 0, 0)

        val expected = 1

        var actual : Int  = 0

        for (i in 0 until size) {
            if (guessArray[i] == solution[i]) {
                actual++
            }
        }

        assertEquals(expected, actual)

    }

    @Test
    fun testEqualityAllResult() {
        val guessArray = intArrayOf(0, 0, 0, 0)
        val solutionArray = intArrayOf(0, 0, 0, 0)

        val expected = 4

        var actual : Int  = 0

        for (i in 0 until size) {
            if (guessArray[i] == solutionArray[i]) {
                actual++
            }
        }

        assertEquals(expected, actual)

    }
}
