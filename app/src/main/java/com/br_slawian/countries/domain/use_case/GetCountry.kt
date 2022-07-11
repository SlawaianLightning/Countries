package com.br_slawian.countries.domain.use_case

import com.br_slawian.countries.domain.model.Country
import com.br_slawian.countries.domain.repository.CountriesRepository
import com.br_slawian.countries.util.Resource
import kotlinx.coroutines.flow.Flow

class GetCountry(
    private val countriesRepository: CountriesRepository
) {
    operator fun invoke(name: String): Flow<Resource<Country>> {
        return countriesRepository.getCountryByName(name)
    }
}