package com.br_slawian.countries.domain.use_case

import com.br_slawian.countries.domain.model.CountryItem
import com.br_slawian.countries.domain.repository.CountriesRepository
import com.br_slawian.countries.util.Resource
import kotlinx.coroutines.flow.Flow

class GetCountries(
    private val countriesRepository: CountriesRepository
) {
    operator fun invoke(): Flow<Resource<List<CountryItem>>> {
        return countriesRepository.getCountries()
    }
}