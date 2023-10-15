package com.manuelrodriguez.pokedex.data.remote.responses

import com.manuelrodriguez.pokedex.data.local.model.PokemonList

data class ServerPokemonResult(
    val name: String,
    val url: String,
    val favorite: Boolean = false
)

fun ServerPokemonResult.toLocalPokemonList() = PokemonList(
    name = name,
    url = url,
    favorite = favorite
)