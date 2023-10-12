package com.manuelrodriguez.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.manuelrodriguez.pokedex.data.local.PokedexListEntryDatabase
import com.manuelrodriguez.pokedex.data.repositories.LocalDataSource
import com.manuelrodriguez.pokedex.data.repositories.PokemonListRepository
import com.manuelrodriguez.pokedex.data.repositories.RemoteDatasource
import com.manuelrodriguez.pokedex.ui.theme.screens.pokemonList.PokemonListScreen

class MainActivity : ComponentActivity() {

    private lateinit var db : PokedexListEntryDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(
            applicationContext,
            PokedexListEntryDatabase::class.java,
            "Pokedex-db"
        ).build()

        val repository = PokemonListRepository(
            localDataSource =  LocalDataSource(db.pokedexListEntryDao()),
            remoteDatasource = RemoteDatasource()
        )

        setContent {
            PokemonListScreen(repository)
        }
    }
}