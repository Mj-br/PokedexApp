package com.manuelrodriguez.pokedex.data.local
import com.manuelrodriguez.pokedex.data.local.model.PokemonList as LocalPokemonList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDataSource(private val dao: PokedexListEntryDao){

    val pokemonList: Flow<List<LocalPokemonList>> = dao.getPokemonList().map { pokemonList ->
        pokemonList.map { it.toLocalPokemonList() }
    }
    suspend fun updatePokemon(pokemon: LocalPokemonList){
        dao.updatePokemon(pokemon.toLocalPokedexEntryList())
    }
    suspend fun insertAll(pokemonList: List<LocalPokemonList>){
        dao.insertAll(pokemonList.map { it.toLocalPokedexEntryList() } )
    }
    suspend fun count(): Int{
        return dao.count()
    }

}