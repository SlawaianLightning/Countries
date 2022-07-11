package com.br_slawian.countries.presentation.screen

sealed class Screen(val route: String){
    object CountriesScreen: Screen("countries_screen")
    object CountryScreen: Screen("country_screen")

    fun withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach { arg->
                append("/$arg")
            }
        }
    }
}