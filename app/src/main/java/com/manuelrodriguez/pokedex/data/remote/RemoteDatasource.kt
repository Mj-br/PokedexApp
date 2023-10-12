package com.manuelrodriguez.pokedex.data.remote

import com.manuelrodriguez.pokedex.data.models.PokemonList
import com.manuelrodriguez.pokedex.data.remote.responses.toPokedexEntryList
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RemoteDatasource{

    suspend fun getPokemonListFromRemote(): List<PokemonList> {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://pokeapi.co/api/v2/")
            .build()
            .create(PokemonsService::class.java)
            .getPokemonList(limit = 151, offset = 0)
            .results
            .map { it.toPokedexEntryList() }
    }


}