package com.br_slawian.countries.data.remote

import com.br_slawian.countries.GetCountriesQuery

interface CountriesApi {

    suspend fun getCountries(): List<GetCountriesQuery.Country>?

    companion object {
        const val BASE_URL = "https://countries.trevorblades.com/"
    }
}