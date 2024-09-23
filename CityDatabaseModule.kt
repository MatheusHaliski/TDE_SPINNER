package com.example.myintentapplication.di

import android.content.Context
import androidx.room.Room
import com.example.myintentapplication.CityDatabase
import com.example.myintentapplication.CityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CityDatabaseModule {

    @Provides
    @Singleton
    fun provideCityDatabase(context: Context): CityDatabase {
        return Room.databaseBuilder(
            context,
            CityDatabase::class.java,
            "city_database"
        ).build()
    }

    @Provides
    fun provideCityDao(cityDatabase: CityDatabase): CityDao {
        return cityDatabase.cityDao()
    }
}
