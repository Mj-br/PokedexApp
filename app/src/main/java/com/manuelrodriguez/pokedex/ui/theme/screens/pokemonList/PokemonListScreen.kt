package com.manuelrodriguez.pokedex.ui.theme.screens.pokemonList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.manuelrodriguez.pokedex.data.local.model.PokemonList
import com.manuelrodriguez.pokedex.domain.useCases.CollectPokemonListUseCase
import com.manuelrodriguez.pokedex.domain.useCases.RequestPokemonListUseCase
import com.manuelrodriguez.pokedex.domain.useCases.UpdatePokemonListUseCase
import com.manuelrodriguez.pokedex.ui.theme.PokedexAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen(
    navController: NavHostController,
    collectPokemonListUseCase: CollectPokemonListUseCase,
    requestPokemonListUseCase: RequestPokemonListUseCase,
    updatePokemonListUseCase: UpdatePokemonListUseCase
) {
    PokedexAppTheme {

        val viewModel: PokemonListViewModel = viewModel{ PokemonListViewModel(collectPokemonListUseCase, requestPokemonListUseCase, updatePokemonListUseCase) }
        val state by viewModel.state.collectAsState()

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(topBar = { TopAppBar(title = { Text(text = "") }) }) { padding ->

                if (state.loading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }

                }
                if (state.pokemonList.isNotEmpty()) {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(120.dp),
                        modifier = Modifier.padding(padding),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        contentPadding = PaddingValues(4.dp)
                    ) {
                        items(state.pokemonList) { pokemon ->

                            PokemonItem(
                                pokemon = pokemon,
                                onClick = { viewModel.onPokemonClick(pokemon) }
                            )
                        }
                    }
                }


            }


        }
    }

}

@Composable
fun PokemonItem(pokemon: PokemonList, onClick: () -> Unit) {
    val number = if (pokemon.url.endsWith("/")) {
        pokemon.url.dropLast(1).takeLastWhile { it.isDigit() }
    } else {
        pokemon.url.takeLastWhile { it.isDigit() }
    }
    val imageUrl =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$number.png"

    Column(modifier = Modifier.clickable { onClick() }) {

        Box() {
            AsyncImage(
                model = imageUrl, contentDescription = pokemon.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2 / 3f)
            )

            if (pokemon.favorite) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp),
                    tint = Color.Red
                )
            }
        }


        Text(
            text = pokemon.name,
            modifier = Modifier.padding(16.dp),
            maxLines = 1
        )
    }
}
