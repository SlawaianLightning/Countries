package com.br_slawian.countries.di

import android.app.Application
import androidx.room.Room
import com.apollographql.apollo3.ApolloClient
import com.br_slawian.countries.data.local.CountriesDataBase
import com.br_slawian.countries.data.remote.CountriesApi
import com.br_slawian.countries.data.remote.CountriesApiImpl
import com.br_slawian.countries.data.repository.CountriesRepositoryImpl
import com.br_slawian.countries.domain.repository.CountriesRepository
import com.br_slawian.countries.domain.use_case.GetCountries
import com.br_slawian.countries.domain.use_case.GetCountry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CountriesModule {

    @Provides
    @Singleton
    fun provideGetCountriesUseCase(repository: CountriesRepository): GetCountries {
        return GetCountries(repository)
    }

    @Provides
    @Singleton
    fun provideGetCountryUseCase(repository: CountriesRepository): GetCountry {
        return GetCountry(repository)
    }

    @Provides
    @Singleton
    fun provideCountriesRepository(
       db: CountriesDataBase,
        api: CountriesApi
    ): CountriesRepository {
        return CountriesRepositoryImpl(api, db.countriesDao)
    }

    @Provides
    @Singleton
    fun provideCountriesDataBase(app: Application): CountriesDataBase {
        return Room.databaseBuilder(
            app, CountriesDataBase::class.java, CountriesDataBase.DATA_BASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideCountriesApi(): CountriesApi {
        return CountriesApiImpl(
            ApolloClient.Builder()
                .serverUrl(CountriesApi.BASE_URL)
                .build()
        )
    }
}
