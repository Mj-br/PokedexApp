package com.manuelrodriguez.pokedex.data.local
import com.manuelrodriguez.pokedex.data.models.PokemonList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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