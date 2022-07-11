package com.br_slawian.countries.presentation.state

import com.br_slawian.countries.domain.model.CountryItem

data class CountriesState(
    val countries: List<CountryItem> = emptyList(),
    val isLoading: Boolean = false
)