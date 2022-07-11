package com.br_slawian.countries.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.br_slawian.countries.data.local.entity.CountryEntity

@Database(
    entities = [CountryEntity::class],
    version = 1
)
abstract class CountriesDataBase: RoomDatabase() {

    abstract val countriesDao: CountryDao

    companion object {
        const val DATA_BASE_NAME = "countries_db"
    }

}