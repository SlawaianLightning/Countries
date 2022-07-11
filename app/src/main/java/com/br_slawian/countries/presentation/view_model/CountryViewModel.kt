package com.br_slawian.countries.presentation.view_model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br_slawian.countries.domain.use_case.GetCountry
import com.br_slawian.countries.presentation.state.CountriesState
import com.br_slawian.countries.presentation.state.CountryState
import com.br_slawian.countries.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val getCountry: GetCountry
) : ViewModel() {

    private val _state = mutableStateOf(CountryState())
    val state: State<CountryState> = _state

    private var job: Job? = null

    fun loadCountry(name: String) {
        job?.cancel()
        job = viewModelScope.launch {
            delay(500L)
            getCountry(name).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            country = result.data ?: null,
                            isLoading = false
                        )
                        job?.cancel()
                    }
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            country = result.data ?: null,
                            isLoading = true
                        )
                    }
                }

            }.launchIn(this)
        }
    }
}