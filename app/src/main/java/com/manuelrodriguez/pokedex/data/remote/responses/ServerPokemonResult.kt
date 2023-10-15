package com.manuelrodriguez.pokedex.data.remote.responses

import com.manuelrodriguez.pokedex.data.local.LocalPokedexEntryList
import com.manuelrodriguez.pokedex.domain.models.PokemonList

data class ServerPokemonResult(
    val name: String,
    val url: String,
    val favorite: Boolean = false
)

fun ServerPokemonResult.toPokedexEntryList() = PokemonList(
    name = name,
    url = url,
    favorite = favorite
)