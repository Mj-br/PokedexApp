package com.manuelrodriguez.pokedex.data.remote.responses

data class ServerPokemonResult(
    val name: String,
    val url: String,
    val favorite: Boolean = false
)