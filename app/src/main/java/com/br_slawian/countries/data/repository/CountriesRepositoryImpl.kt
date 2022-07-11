package com.br_slawian.countries.data.repository

import com.apollographql.apollo3.exception.ApolloNetworkException
import com.br_slawian.countries.data.local.CountryDao
import com.br_slawian.countries.data.local.entity.CountryEntity
import com.br_slawian.countries.data.remote.CountriesApi
import com.br_slawian.countries.domain.model.Country
import com.br_slawian.countries.domain.model.CountryItem
import com.br_slawian.countries.domain.repository.CountriesRepository
import com.br_slawian.countries.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

class CountriesRepositoryImpl(
    private val api: CountriesApi,
    private val dao: CountryDao
) : CountriesRepository {

    override fun getCountries(): Flow<Resource<List<CountryItem>>> = flow {
        emit(Resource.Loading())
        val countries = dao.getCountries().map { it.toCountryItem() }
        emit(Resource.Loading(data = countries))
        try {
            val remoteCountries = api.getCountries() ?: emptyList()
            val localCountries = remoteCountries.map {
                CountryEntity(
                    it.name,
                    it.phone,
                    it.code,
                    it.continent.name,
                    it.emoji,
                    it.currency ?: "",
                    it.capital ?: "",
                    if (it.languages.size > 0) it.languages.get(0).name ?: "" else ""
                )
            }
            dao.deleteCountry(localCountries.map { it.name })
            dao.insertCountries(localCountries)
            emit(Resource.Success(data = localCountries.map { it.toCountryItem() }))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong!",
                    data = countries
                )
            )
        } catch (e: ApolloNetworkException) {
            emit(
                Resource.Error(
                    message = "Failed internet connection.",
                    data = countries
                )
            )
        }
    }

    override fun getCountryByName(name: String): Flow<Resource<Country>> = flow {
        emit(Resource.Loading())
        val country = dao.getCountry(name).toCountry()
        emit(Resource.Success(data = country))
    }

}