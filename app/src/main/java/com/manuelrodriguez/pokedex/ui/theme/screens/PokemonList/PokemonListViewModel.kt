package com.manuelrodriguez.pokedex.ui.theme.screens.PokemonList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manuelrodriguez.pokedex.data.local.PokedexListEntryDao
import com.manuelrodriguez.pokedex.data.local.toLocalPokedexEntryList
import com.manuelrodriguez.pokedex.data.local.toPokemonList
import com.manuelrodriguez.pokedex.data.models.PokemonList
import com.manuelrodriguez.pokedex.data.remote.responses.ServerPokemonResult
import com.manuelrodriguez.pokedex.data.remote.PokemonsService
import com.manuelrodriguez.pokedex.data.remote.responses.toLocalPokedexEntryList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonListViewModel(private val dao: PokedexListEntryDao) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state

    init {
        viewModelScope.launch {
            val isDbEmpty = dao.count() == 0

            if (isDbEmpty) {
                _state.value = UiState(loading = true)
                dao.insertAll(
                    Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("https://pokeapi.co/api/v2/")
                        .build()
                        .create(PokemonsService::class.java)
                        .getPokemonList(limit = 151, offset = 0)
                        .results
                        .map { it.toLocalPokedexEntryList() }
                )
            }

            dao.getPokedexList().collect{ pokemonList ->
                _state.value = UiState(
                    loading = false,
                    pokemonList = pokemonList.map { it.toPokemonList() }
                )
            }




        }

    }

    data class UiState(
        val loading: Boolean = false,
        val pokemonList: List<PokemonList> = emptyList()
    )

    fun onPokemonClick(pokedex: PokemonList) {
        viewModelScope.launch {

            dao.updatePokemon(pokedex.copy(favorite = !pokedex.favorite).toLocalPokedexEntryList())

        }
    }
}
