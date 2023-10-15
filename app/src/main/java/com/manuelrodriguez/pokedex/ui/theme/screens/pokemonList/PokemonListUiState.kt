package com.manuelrodriguez.pokedex.ui.theme.screens.pokemonList

import com.manuelrodriguez.pokedex.domain.models.PokemonList

data class PokemonListUiState(
        val loading: Boolean = false,
        val pokemonList: List<PokemonList> = emptyList()
    )