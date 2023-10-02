package com.manuelrodriguez.pokedex.ui

import com.manuelrodriguez.pokedex.Data.PokemonResult
import retrofit2.http.GET

interface PokemonsService {

    @GET("pokemon")
    suspend fun getPokemons() : PokemonResult
}