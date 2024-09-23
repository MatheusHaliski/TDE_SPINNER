package com.example.myintentapplication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete

@Dao
interface CityDao {
    @Insert
    suspend fun insert(city: City)

    @Update
    suspend fun update(city: City)

    @Query("DELETE FROM city")
    suspend fun deleteAllCities()

    @Query("SELECT * FROM city")
    suspend fun getAllCities(): List<City>

    @Query("SELECT * FROM city WHERE id = :id")
    suspend fun getCityById(id: Int): City?

}
