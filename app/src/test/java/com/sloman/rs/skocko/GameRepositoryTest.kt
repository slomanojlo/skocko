package com.sloman.rs.skocko

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.jraska.livedata.test
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
open class GameRepositoryTest {

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @get:Rule
    val exceptionRule = ExpectedException.none()

    private lateinit var db: GameDatabase

    @Before
    fun setup(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, GameDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }


    @Test
    open fun test_getCurrentGame() {
        val dao = spy(db.gameDao)
        val testGame = Game(0, listOf(0, 0, 0, 0), "")

        val repo = GameRoomRepository(dao)

        repo.insertOnlyGame(testGame)

        val expected = 1
        val actual  = repo.getCurrentGame().test().value()?.game?.id

        assertEquals(actual, expected)
        verify(dao).getCurrentGame()
    }

    @Test
    open fun test_insertThreeGames() {
        val dao = spy(db.gameDao)
        val testGame = Game(0, listOf(0, 0, 0, 0), "")

        db.gameDao.insertOnlyGame(testGame)
        db.gameDao.insertOnlyGame(testGame)
        db.gameDao.insertOnlyGame(testGame)

        val repo = GameRoomRepository(dao)

        val expected = 3
        val actual  = repo.getCurrentGame().test().value()?.game?.id

        assertEquals(actual, expected)
    }

    @Test
    open fun test_insertGuess() {
        val dao = spy(db.gameDao)
        val testGame = Game(0, listOf(0, 0, 0, 0), "")
        val testGuess = Guess(0,1, listOf(0, 0, 0, 0), listOf(0,0))

        db.gameDao.insertOnlyGame(testGame)
        db.gameDao.insertGuess(testGuess)

        val repo = GameRoomRepository(dao)

        val expected = 1
        val actual  = repo.getCurrentGame().test().value()?.guessList?.size

        assertEquals(actual, expected)
    }

    @Test
    open fun test_setState() {
        val dao = spy(db.gameDao)
        val testGame = Game(0, listOf(0, 0, 0, 0), "")
        val testGuess = Guess(0,1, listOf(0, 0, 0, 0), listOf(0,0))
        val expected = "won"

        db.gameDao.insertOnlyGame(testGame)
        db.gameDao.setGameState(1, expected)

        val repo = GameRoomRepository(dao)

        val actual  = repo.getCurrentGame().test().value()?.game?.state

        assertEquals(actual, expected)
    }



    @After
    fun tearDown() {
        db.close()
    }

}