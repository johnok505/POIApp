package com.jok.poiapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jok.poiapp.mdoel.PlaceOfInterest

@Database(
    entities = [PlaceOfInterest::class],
    version = 14,
    exportSchema = false
)
abstract class PoiDatabase : RoomDatabase() {

    abstract fun poiDao() : PoiDao

    companion object {

        @Volatile
        private var INSTANCE: PoiDatabase? = null

        fun getDatabaseClient(context: Context) : PoiDatabase {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, PoiDatabase::class.java, "POI_DATABASE")
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!

            }
        }

    }

}