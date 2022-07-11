package com.br_slawian.countries.presentation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navArgument
import com.br_slawian.countries.presentation.view_model.CountriesViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun countriesScreen(navController: NavController) {
    MaterialTheme {
        var viewModel: CountriesViewModel = hiltViewModel()
        var state = viewModel.state.value
        var scaffoldState = rememberScaffoldState()

        LaunchedEffect(key1 = true) {
            viewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is CountriesViewModel.UIEvent.ShowSnackbar -> {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = event.message
                        )
                    }
                }
            }
        }

        Scaffold(
            scaffoldState = scaffoldState,

            ) {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .padding(16.dp)
                    .border(
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(2.dp, Color.LightGray)
                    )
                    .padding(bottom = 16.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)

                    ) {
                        Text(
                            text = "Countries",
                            modifier = Modifier
                                .align(Alignment.Center),
                        )
                    }
                    Divider()

                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = viewModel.searchQuery.value,
                        onValueChange = viewModel::onSearch,
                        textStyle = TextStyle(fontSize = 14.sp),
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Search,
                                null,
                                tint = Color.Gray
                            )
                        },
                        modifier = Modifier.padding(8.dp).fillMaxWidth()
                            .background(color = Color.White, RoundedCornerShape(16.dp))
                            .border(
                                shape = RoundedCornerShape(16.dp),
                                border = BorderStroke(2.dp, Color.LightGray)
                            ),
                        placeholder = { Text(text = "Search...") },
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            backgroundColor = Color.Transparent,
                            cursorColor = Color.DarkGray
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(state.countries.size) { i ->
                            val countryItem = state.countries[i]
                            Column(modifier = Modifier.padding(start = 8.dp).clickable {
                                navController.navigate(Screen.CountryScreen.withArgs(countryItem.name))
                            }) {
                                Text(
                                    text = countryItem.emoji + " " + countryItem.name,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Divider()

                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }

        }
    }
}

