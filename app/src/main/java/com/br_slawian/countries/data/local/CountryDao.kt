package com.br_slawian.countries.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.br_slawian.countries.data.local.entity.CountryEntity

@Dao
interface CountryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(countries: List<CountryEntity>)

    @Query("SELECT * FROM CountryEntity WHERE name=:name")
    suspend fun getCountry(name: String): CountryEntity

    @Query("DELETE FROM CountryEntity WHERE name in(:name)")
    suspend fun deleteCountry(name: List<String>)

    @Query("SELECT * FROM CountryEntity")
    suspend fun getCountries(): List<CountryEntity>

}