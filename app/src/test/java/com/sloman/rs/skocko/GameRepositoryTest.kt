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
        val actual  = dao.getCurrentGame().test().value()?.game?.id

        assertEquals(actual, expected)
        verify(dao).getCurrentGame()
    }




    @After
    fun tearDown() {
        db.close()
    }

}