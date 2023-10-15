package com.manuelrodriguez.pokedex.repository

import com.manuelrodriguez.pokedex.data.local.LocalDataSource
import com.manuelrodriguez.pokedex.data.local.model.PokemonList
import com.manuelrodriguez.pokedex.data.remote.RemoteDatasource
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val remoteDatasource: RemoteDatasource,
    private val localDataSource: LocalDataSource
) {

    val pokemonList: Flow<List<PokemonList>> = localDataSource.pokemonList

    suspend fun updatePokemonList(pokemon: PokemonList) {
        localDataSource.updatePokemon(pokemon)
    }

    suspend fun requestPokemonList() {
        val isDbEmpty = localDataSource.count() == 0
        if (isDbEmpty) {
            localDataSource.insertAll(remoteDatasource.getPokemonListFromRemote())
        }
    }

}