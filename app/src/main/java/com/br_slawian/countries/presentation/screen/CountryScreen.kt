package com.br_slawian.countries.presentation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.br_slawian.countries.presentation.view_model.CountryViewModel

@Composable
fun countryScreen(country: String?) {
    var viewModel: CountryViewModel = hiltViewModel()
    viewModel.loadCountry(country ?: "")
    MaterialTheme {
        var state = viewModel.state.value
        var scaffoldState = rememberScaffoldState()

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
                            text = "Info",
                            modifier = Modifier
                                .align(Alignment.Center),
                        )
                    }
                    Divider()

                    Spacer(modifier = Modifier.height(16.dp))
                    val country = state.country
                    country?.let {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)

                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.Center),
                                text = country.emoji,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,

                                )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)

                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.Center),
                                text = country.name,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }

                        Row {
                            boxItem(
                                "continent",
                                country?.continent
                            )
                            boxItem(
                                "capital",
                                country?.capital
                            )
                        }

                        Row {
                            boxItem(
                                "code",
                                country?.code
                            )
                            boxItem(
                                "phone",
                                country?.phone
                            )
                        }

                        Row {
                            boxItem(
                                "currency",
                                country?.currency
                            )
                            boxItem(
                                "language",
                                country?.language
                            )
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

@Composable
fun boxItem(
    title: String,
    value: String
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .size( LocalConfiguration.current.screenWidthDp.dp/2-24.dp, 80.dp)
            .border(
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(2.dp, Color.LightGray)
            )
    ) {
        Column {
            Text(
                modifier = Modifier
                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
                color = Color.Black,
                text = title
            )

            Divider()

            Text(
                modifier = Modifier
                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
                color = Color(0xFFFF8F00),
                text = value
            )

        }
    }
}




