package com.manuelrodriguez.pokedex.domain.useCases

import com.manuelrodriguez.pokedex.data.repositories.PokemonListRepository

class CollectPokemonListUseCase(private val repository: PokemonListRepository) {
    operator fun invoke() = repository.pokemonList
}