package com.manuelrodriguez.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.manuelrodriguez.pokedex.data.local.PokedexListEntryDatabase
import com.manuelrodriguez.pokedex.ui.theme.screens.PokemonList.PokemonListScreen

class MainActivity : ComponentActivity() {

    lateinit var db : PokedexListEntryDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(
            applicationContext,
            PokedexListEntryDatabase::class.java,
            "Pokedex-db"
        ).build()


        setContent {
            PokemonListScreen(db.pokedexListEntryDao())
        }
    }
}