package com.br_slawian.countries.presentation.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br_slawian.countries.domain.model.CountryItem
import com.br_slawian.countries.domain.use_case.GetCountries
import com.br_slawian.countries.presentation.state.CountriesState
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
class CountriesViewModel @Inject constructor(
    private val getCountries: GetCountries
) : ViewModel() {

    private val _state = mutableStateOf(CountriesState())
    val state: State<CountriesState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private var countries: List<CountryItem> = emptyList()

    private var job: Job? = null

    init {
        loadCountries()
    }

    fun onSearch(query: String) {
        _searchQuery.value = query
        job?.cancel()
        job = viewModelScope.launch {
            delay(500L)
            _state.value = state.value.copy(
                countries = if (query.equals("")) countries else countries.filter {
                    it.name.contains(
                        query,
                        true
                    )
                },
                isLoading = false
            )
        }
    }

    fun loadCountries() {
        job?.cancel()
        job = viewModelScope.launch {
            getCountries().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        countries = result.data ?: emptyList()
                        _state.value = state.value.copy(
                            countries = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        countries = result.data ?: emptyList()
                        _state.value = state.value.copy(
                            countries = result.data ?: emptyList(),
                            isLoading = false
                        )
                        _eventFlow.emit(
                            UIEvent.ShowSnackbar(
                                result.message ?: "Unknown error"
                            )
                        )
                    }
                    is Resource.Loading -> {
                        countries = result.data ?: emptyList()
                        _state.value = state.value.copy(
                            countries = result.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
    }
}