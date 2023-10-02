package com.manuelrodriguez.pokedex.Data

data class PokemonResult(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<ServerPokemon>
)