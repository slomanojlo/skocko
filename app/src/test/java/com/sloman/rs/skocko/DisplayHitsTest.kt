package com.sloman.rs.skocko

import com.sloman.rs.skocko.viewmodel.displayHit
import com.sloman.rs.skocko.util.Constants
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized


/** Testing the DisplayHits algorithm with JUnit.*/
@RunWith(Parameterized::class)
class DisplayHitsTest(
    private val expectedResult: Int,
    private val imagePosTag: Int,
    private val hitsList: List<Int>,
    private val scenario: String
) {

    companion object {

        private const val noDisplay = 0
        private const val hit = Constants.HIT_SYMBOL
        private const val wrongPlace = Constants.WRONG_POS_SYMBOL

        @JvmStatic
        @Parameterized.Parameters(name = "displayHits:{3}")
        fun rounds() = listOf(
            arrayOf(hit, 0, listOf(4, 0), "All hits, zero position"),
            arrayOf(hit, 3, listOf(4, 0), "All hits, third position"),
            arrayOf(wrongPlace, 0, listOf(0, 3), "All wrong place hits, first position"),
            arrayOf(wrongPlace, 2, listOf(0, 4), "All wrong place hits, second position"),
            arrayOf(noDisplay, 2, listOf(0, 0), "No hits, second position"),
            arrayOf(noDisplay, 0, listOf(0, 0), "No hits, zero position"),
            arrayOf(wrongPlace, 3, listOf(2, 2), "Two hits and two wrong place, third position"),
            arrayOf(hit, 0, listOf(2, 2), "Two hits and two wrong place, zero position"),
            arrayOf(noDisplay, 3, listOf(0, 3), "Three wrong place, third position")
        )
    }

    @Test
    fun test_displayHit() {

        val actual = displayHit(
            hitsList,
            imagePosTag
        )

        assertEquals(expectedResult, actual)
    }
}
