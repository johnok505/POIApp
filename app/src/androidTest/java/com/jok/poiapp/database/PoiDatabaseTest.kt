package com.jok.poiapp.database

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.jok.poiapp.getOrAwaitValue
import com.jok.poiapp.mdoel.PlaceOfInterest
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class PoiDatabaseTest : TestCase() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var database: PoiDatabase
    private lateinit var dao: PoiDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, PoiDatabase::class.java).build()
        dao = database.poiDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun testInsertAndGetPoi(): Unit = runBlocking {
        val poiList = listOf(
            PlaceOfInterest(
                id = "15",
                title = "new place",
                image = "test_image.jpg"
            )
        )
        dao.insertPois(poiList)

        val poisInDatabase = dao.getPois().getOrAwaitValue()
        assertThat(poisInDatabase.contains(poiList[0]))
    }

}