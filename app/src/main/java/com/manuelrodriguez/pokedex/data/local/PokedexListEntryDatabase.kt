package com.manuelrodriguez.pokedex.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import com.manuelrodriguez.pokedex.data.models.PokemonList
import kotlinx.coroutines.flow.Flow

@Database(entities = [LocalPokedexEntryList::class], version = 1)
abstract class PokedexListEntryDatabase : RoomDatabase() {
    abstract fun pokedexListEntryDao(): PokedexListEntryDao
}

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

@Entity
data class LocalPokedexEntryList(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val url: String,
    val favorite: Boolean = false

)



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
