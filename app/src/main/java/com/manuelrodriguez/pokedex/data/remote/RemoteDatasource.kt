package com.manuelrodriguez.pokedex.data.remote


import com.manuelrodriguez.pokedex.data.remote.responses.Pokemon
import com.manuelrodriguez.pokedex.data.remote.responses.PokemonList as RemotePokemonList
import com.manuelrodriguez.pokedex.data.remote.responses.toLocalPokemonList
import com.manuelrodriguez.pokedex.data.local.model.PokemonList as LocalPokemonList
import com.manuelrodriguez.pokedex.util.Resource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class RemoteDatasource @Inject constructor(
    private val api: PokemonServiceApi
) {

    suspend fun getPokemonListFromRemote(): List<LocalPokemonList> {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://pokeapi.co/api/v2/")
            .build()
            .create(PokemonServiceApi::class.java)
            .getPokemonList(limit = 151, offset = 0)
            .results
            .map { it.toLocalPokemonList() }
    }

    suspend fun getPokemonListFromRemote(limit: Int, offset: Int): Resource<RemotePokemonList> {
        val response = try {
            api.getPokemonList(limit, offset)
        } catch (e: Exception) {
            return Resource.Error("Couldn't reach the server. Check your internet connection.")
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonInfoFromRemote(pokemonName: String): Resource<Pokemon> {
        val response = try {
            api.getPokemonInfo(pokemonName)
        } catch (e: Exception) {
            return Resource.Error("Couldn't reach the server. Check your internet connection.")
        }
        return Resource.Success(response)
    }


}