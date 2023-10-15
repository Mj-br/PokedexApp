package com.manuelrodriguez.pokedex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.manuelrodriguez.pokedex.data.local.model.PokemonList as LocalPokemonList

@Database(entities = [LocalPokedexEntryList::class], version = 1)
abstract class PokedexListEntryDatabase : RoomDatabase() {
    abstract fun pokedexListEntryDao(): PokedexListEntryDao
}

fun LocalPokedexEntryList.toLocalPokemonList() = LocalPokemonList(
    name = name,
    url = url,
    favorite = favorite
)

fun LocalPokemonList.toLocalPokedexEntryList() = LocalPokedexEntryList(
    name = name,
    url = url,
    favorite = favorite
)
