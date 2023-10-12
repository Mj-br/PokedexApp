package com.manuelrodriguez.pokedex.ui.theme.screens.pokemonList

import com.manuelrodriguez.pokedex.data.models.PokemonList

data class PokemonListUiState(
        val loading: Boolean = false,
        val pokemonList: List<PokemonList> = emptyList()
    )