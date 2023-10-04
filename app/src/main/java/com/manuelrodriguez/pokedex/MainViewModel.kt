package com.manuelrodriguez.pokedex

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manuelrodriguez.pokedex.data.remote.responses.ServerPokemonResult
import com.manuelrodriguez.pokedex.ui.PokemonsService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    var state: StateFlow<UiState> = _state
    init {
        viewModelScope.launch {
            _state.value = UiState(Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://pokeapi.co/api/v2/")
                .build()
                .create(PokemonsService::class.java)
                .getPokemonList(limit = 151, offset = 0)
                .results)
        }

    }

    data class UiState(
        val pokemonList: List<ServerPokemonResult> = emptyList()
    )
}