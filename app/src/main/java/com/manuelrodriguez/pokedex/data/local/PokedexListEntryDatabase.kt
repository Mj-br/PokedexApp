package com.manuelrodriguez.pokedex.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import com.manuelrodriguez.pokedex.domain.models.PokemonList
import kotlinx.coroutines.flow.Flow

@Database(entities = [LocalPokedexEntryList::class], version = 1)
abstract class PokedexListEntryDatabase : RoomDatabase() {
    abstract fun pokedexListEntryDao(): PokedexListEntryDao
}

fun LocalPokedexEntryList.toPokemonList() = PokemonList(
    name = name,
    url = url,
    favorite = favorite
)

fun PokemonList.toLocalPokedexEntryList() = LocalPokedexEntryList(
    name = name,
    url = url,
    favorite = favorite
)
