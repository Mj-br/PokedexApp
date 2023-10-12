package com.manuelrodriguez.pokedex.data.remote.responses

import com.manuelrodriguez.pokedex.data.local.LocalPokedexEntryList

data class ServerPokemonResult(
    val name: String,
    val url: String,
    val favorite: Boolean = false
)

fun ServerPokemonResult.toLocalPokedexEntryList() = LocalPokedexEntryList(
    name = name,
    url = url,
    favorite = favorite
)