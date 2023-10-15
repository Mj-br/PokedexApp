package com.manuelrodriguez.pokedex.ui.theme.screens.pokemonList

import com.manuelrodriguez.pokedex.data.local.model.PokemonList

data class PokemonListUiState(
        val loading: Boolean = false,
        val pokemonList: List<PokemonList> = emptyList()
    )