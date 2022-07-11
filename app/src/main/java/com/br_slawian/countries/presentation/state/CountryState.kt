package com.br_slawian.countries.presentation.state

import com.br_slawian.countries.domain.model.Country

data class CountryState(
    val country: Country? = null,
    val isLoading: Boolean = false
)