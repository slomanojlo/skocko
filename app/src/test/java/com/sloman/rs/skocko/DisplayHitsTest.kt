package com.sloman.rs.skocko

import androidx.databinding.BindingAdapter
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class DisplayHitsTest(
    private val expectedResult: Int,
    private val imagePosTag: Int,
    private val hitsList: List<Int>,
    private val scenario: String
) {

    companion object {

        val noDisplay = 0
        val hit = Constants.HIT_SYMBOL
        val wrongPlace = Constants.WRONG_POS_SYMBOL

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
            arrayOf(hit, 0, listOf(2, 2), "Two hits and two wrong place,, zero position"),
            arrayOf(noDisplay, 3, listOf(0, 3), "Three wrong place, third position")
        )
    }

    @Test
    fun test_displayHit() {

        val actual = diplayHit(hitsList, imagePosTag)

        assertEquals(expectedResult, actual)
    }
}
