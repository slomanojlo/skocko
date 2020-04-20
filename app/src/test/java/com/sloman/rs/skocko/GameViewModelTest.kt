package com.sloman.rs.skocko

import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class GameViewModelTest {

    @get:Rule
    val exceptionRule = ExpectedException.none()


    @Test
    fun test_getCurrentGame() {
        val expected = 1

        val repo: GameRepository = mock()
        whenever(repo.getCurrentGame())
            .thenReturn(
                (MutableLiveData(
                    GameWithGuesses(
                        Game(1, listOf(0, 0, 0, 0), ""),
                        listOf(Guess(0, 0, listOf(0, 0, 0, 0), listOf(1, 1)))
                    )
                ))
            )
        val model = GameViewModel(repo)

        val currentGame = model.game

        assertNotNull(currentGame)
        assertEquals(expected, currentGame.value?.game?.id)
    }

    @Test
    fun test_setGameState(){
        val repo: GameRepository = mock()
        val state = "fake"
        val model = GameViewModel(repo)

        model.setGameState(0, state)

        verify(repo)
            .setGameState(0, state)

    }


}