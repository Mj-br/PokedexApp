package com.manuelrodriguez.pokedex.data.repositories

import com.manuelrodriguez.pokedex.data.local.PokedexListEntryDao
import com.manuelrodriguez.pokedex.data.local.toLocalPokedexEntryList
import com.manuelrodriguez.pokedex.data.local.toPokemonList
import com.manuelrodriguez.pokedex.data.models.PokemonList
import com.manuelrodriguez.pokedex.data.remote.PokemonsService
import com.manuelrodriguez.pokedex.data.remote.responses.toPokedexEntryList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LocalDataSource(private val dao: PokedexListEntryDao){

    val pokemonList: Flow<List<PokemonList>> = dao.getPokedexList().map { pokemonList ->
        pokemonList.map { it.toPokemonList() }
    }
    suspend fun updatePokemon(pokemon: PokemonList){
        dao.updatePokemon(pokemon.toLocalPokedexEntryList())
    }
    suspend fun insertAll(pokemonList: List<PokemonList>){
        dao.insertAll(pokemonList.map { it.toLocalPokedexEntryList() } )
    }
    suspend fun count(): Int{
        return dao.count()
    }

}

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