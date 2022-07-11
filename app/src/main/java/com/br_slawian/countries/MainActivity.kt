package com.br_slawian.countries

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.br_slawian.countries.presentation.view_model.CountriesViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.br_slawian.countries.presentation.screen.Screen
import com.br_slawian.countries.presentation.screen.countriesScreen
import com.br_slawian.countries.presentation.screen.countryScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation()
        }

    }

    @Composable
    fun Navigation(){
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Screen.CountriesScreen.route
        ) {
            composable(route = Screen.CountriesScreen.route) {
                countriesScreen(navController)
            }
            composable(
                route = Screen.CountryScreen.route+"/{country}",
                arguments = listOf(
                    navArgument("country"){
                        type = NavType.StringType
                        defaultValue = ""
                        nullable = true
                    }
                )
            ) { entry->
                countryScreen(country = entry.arguments?.getString("country"))
            }
        }
    }
}