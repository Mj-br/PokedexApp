package com.manuelrodriguez.pokedex.ui.theme.screens.pokemonList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manuelrodriguez.pokedex.data.models.PokemonList
import com.manuelrodriguez.pokedex.data.repositories.PokemonListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PokemonListViewModel(private val repository: PokemonListRepository) : ViewModel() {

    private val _state = MutableStateFlow(PokemonListUiState())
    val state: StateFlow<PokemonListUiState> = _state

    init {
        viewModelScope.launch {
            _state.value = PokemonListUiState(loading = true)
            repository.requestPokemonList()

            repository.pokemonList.collect {
                _state.value = PokemonListUiState(pokemonList = it)
            }
        }

    }

    fun onPokemonClick(pokedex: PokemonList) {
        viewModelScope.launch {

            repository.updatePokemonList(pokedex.copy(favorite = !pokedex.favorite))

        }
    }
}
