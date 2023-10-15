package com.manuelrodriguez.pokedex.domain.useCases

import com.manuelrodriguez.pokedex.repository.PokemonRepository

class CollectPokemonListUseCase(private val repository: PokemonRepository) {
    operator fun invoke() = repository.pokemonList
}