package com.manuelrodriguez.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.manuelrodriguez.pokedex.data.local.LocalDataSource
import com.manuelrodriguez.pokedex.data.local.PokedexListEntryDatabase
import com.manuelrodriguez.pokedex.data.remote.RemoteDatasource
import com.manuelrodriguez.pokedex.di.AppModule.providePokemonServiceApi
import com.manuelrodriguez.pokedex.repository.PokemonRepository
import com.manuelrodriguez.pokedex.domain.useCases.CollectPokemonListUseCase
import com.manuelrodriguez.pokedex.domain.useCases.RequestPokemonListUseCase
import com.manuelrodriguez.pokedex.domain.useCases.UpdatePokemonListUseCase
import com.manuelrodriguez.pokedex.ui.theme.PokedexAppTheme
import com.manuelrodriguez.pokedex.ui.theme.screens.pokemonList.PokemonListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var db: PokedexListEntryDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val api = providePokemonServiceApi()

        db = Room.databaseBuilder(
            applicationContext,
            PokedexListEntryDatabase::class.java,
            "Pokedex-db"
        ).build()

        val repository = PokemonRepository(
            localDataSource = LocalDataSource(db.pokedexListEntryDao()),
            remoteDatasource = RemoteDatasource(api)
        )

        setContent {

            PokedexAppTheme {

                val collectUseCase = CollectPokemonListUseCase(repository)
                val requestUseCase = RequestPokemonListUseCase(repository)
                val updateUseCase = UpdatePokemonListUseCase(repository)

                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "pokemon_list_screen") {

                    composable("pokemon_list_screen") {
                        PokemonListScreen(
                            navController,
                            collectUseCase,
                            requestUseCase,
                            updateUseCase
                        )
                    }

                    composable("pokemon_detail_screen/{dominantColor}/{pokemonName}",
                        arguments = listOf(
                            navArgument("dominantColor") {
                                type = NavType.IntType
                            },
                            navArgument("pokemonName") {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        val dominantColor = remember {
                            val color = it.arguments?.getInt("dominantColor")
                            color?.let { Color(it) } ?: Color.White
                        }

                        val pokemonName = remember {
                            it.arguments?.getString("pokemonName")
                        }

                    }

                }
            }
        }
    }
}