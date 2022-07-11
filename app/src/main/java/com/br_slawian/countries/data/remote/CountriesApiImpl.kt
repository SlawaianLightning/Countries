package com.br_slawian.countries.data.remote

import com.apollographql.apollo3.ApolloClient
import com.br_slawian.countries.GetCountriesQuery

class CountriesApiImpl(private val apolloClient: ApolloClient): CountriesApi {

    override suspend fun getCountries(): List<GetCountriesQuery.Country>? {
        return apolloClient.query(GetCountriesQuery()).execute().data?.countries
    }

}