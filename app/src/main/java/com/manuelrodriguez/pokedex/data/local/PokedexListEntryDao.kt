package com.manuelrodriguez.pokedex.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PokedexListEntryDao {
    @Query("SELECT * FROM LocalPokedexEntryList")
    fun getPokedexList(): Flow<List<LocalPokedexEntryList>>

    @Insert
    suspend fun insertAll(pokedexEntryList: List<LocalPokedexEntryList>)

    @Update
    suspend fun updatePokemon(pokemon: LocalPokedexEntryList)

    @Query("SELECT COUNT(*) FROM LocalPokedexEntryList")
    suspend fun count(): Int
}