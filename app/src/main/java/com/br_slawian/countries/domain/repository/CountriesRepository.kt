package com.br_slawian.countries.domain.repository

import com.br_slawian.countries.domain.model.Country
import com.br_slawian.countries.domain.model.CountryItem
import com.br_slawian.countries.util.Resource
import kotlinx.coroutines.flow.Flow

interface CountriesRepository {
    fun getCountries(): Flow<Resource<List<CountryItem>>>
    fun getCountryByName(name: String): Flow<Resource<Country>>
}