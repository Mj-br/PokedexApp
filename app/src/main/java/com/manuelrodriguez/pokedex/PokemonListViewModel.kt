package com.manuelrodriguez.pokedex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manuelrodriguez.pokedex.data.remote.responses.ServerPokemonResult
import com.manuelrodriguez.pokedex.ui.PokemonsService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonListViewModel : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    var state: StateFlow<UiState> = _state


    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(
                loading = false,
                pokemonList = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://pokeapi.co/api/v2/")
                    .build()
                    .create(PokemonsService::class.java)
                    .getPokemonList(limit = 151, offset = 0)
                    .results
            )
        }

    }

    data class UiState(
        val loading: Boolean = false,
        val pokemonList: List<ServerPokemonResult> = emptyList()
    )

    fun onPokemonClick(pokemon: ServerPokemonResult) {
        //Favorite
        val pokemonList = _state.value.pokemonList.toMutableList()
        pokemonList.replaceAll {
            if (it.name == pokemon.name) pokemon.copy(favorite = !it.favorite) else it
        }

        _state.value = _state.value.copy(pokemonList = pokemonList)
    }
}
