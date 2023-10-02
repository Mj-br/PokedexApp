package com.manuelrodriguez.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.manuelrodriguez.pokedex.Data.ServerPokemon
import com.manuelrodriguez.pokedex.ui.PokemonsService
import com.manuelrodriguez.pokedex.ui.theme.PokedexAppTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexAppTheme {
                // A surface container using the 'background' color from the theme

                val pokemons = produceState<List<ServerPokemon>>(initialValue = emptyList()) {
                    value = Retrofit.Builder()
                        .baseUrl("https://pokeapi.co/api/v2/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(PokemonsService::class.java)
                        .getPokemons()
                        .results
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(topBar = { TopAppBar(title = { Text(text = "") }) }) { padding ->

                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(120.dp),
                            modifier = Modifier.padding(padding),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            contentPadding = PaddingValues(4.dp)
                        ) {
                            items(pokemons.value) { pokemon ->

                                PokemonItem(pokemon)
                            }
                        }

                    }


                }
            }
        }
    }
}

@Composable
fun PokemonItem(pokemon: ServerPokemon) {
    Column {

        //Todo: Use AsyncImage to load images, need to look at Api
        AsyncImage(
            model = pokemon.url, contentDescription = pokemon.name,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2 / 3f)
        )
        Text(
            text = pokemon.name,
            modifier = Modifier.padding(16.dp),
            maxLines = 1
        )

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PokedexAppTheme {
        Greeting("Android")
    }
}