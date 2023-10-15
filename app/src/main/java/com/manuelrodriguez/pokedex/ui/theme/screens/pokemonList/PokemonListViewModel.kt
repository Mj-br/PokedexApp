package com.manuelrodriguez.pokedex.ui.theme.screens.pokemonList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manuelrodriguez.pokedex.domain.models.PokemonList
import com.manuelrodriguez.pokedex.data.repositories.PokemonListRepository
import com.manuelrodriguez.pokedex.domain.useCases.CollectPokemonListUseCase
import com.manuelrodriguez.pokedex.domain.useCases.RequestPokemonListUseCase
import com.manuelrodriguez.pokedex.domain.useCases.UpdatePokemonListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PokemonListViewModel(
    private val collectPokemonListUseCase: CollectPokemonListUseCase,
    private val requestPokemonListUseCase: RequestPokemonListUseCase,
    private val updatePokemonListUseCase: UpdatePokemonListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PokemonListUiState())
    val state: StateFlow<PokemonListUiState> = _state

    init {
        viewModelScope.launch {
            _state.value = PokemonListUiState(loading = true)
            requestPokemonListUseCase()

            collectPokemonListUseCase().collect {
                _state.value = PokemonListUiState(
                    pokemonList = it
                )
            }
        }

    }

    fun onPokemonClick(pokedex: PokemonList) {
        viewModelScope.launch {

            updatePokemonListUseCase(pokedex.copy(favorite = !pokedex.favorite))

        }
    }
}
