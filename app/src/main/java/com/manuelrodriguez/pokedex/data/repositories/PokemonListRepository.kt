package com.manuelrodriguez.pokedex.data.repositories

import com.manuelrodriguez.pokedex.data.local.LocalDataSource
import com.manuelrodriguez.pokedex.data.models.PokemonList
import com.manuelrodriguez.pokedex.data.remote.RemoteDatasource
import kotlinx.coroutines.flow.Flow


class PokemonListRepository(
    private val remoteDatasource: RemoteDatasource,
    private val localDataSource: LocalDataSource
) {

    val pokemonList: Flow<List<PokemonList>> = localDataSource.pokemonList

    suspend fun updatePokemonList(pokemon: PokemonList){
        localDataSource.updatePokemon(pokemon)
    }

    suspend fun requestPokemonList(){
        val isDbEmpty = localDataSource.count() == 0
        if(isDbEmpty){
            localDataSource.insertAll(remoteDatasource.getPokemonListFromRemote())
        }
    }

}