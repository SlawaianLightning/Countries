package com.br_slawian.countries.domain.model

data class Country(
    val name: String,
    val phone: String,
    val code: String,
    val continent: String,
    val emoji: String,
    val currency: String,
    val capital: String,
    val language: String
)
