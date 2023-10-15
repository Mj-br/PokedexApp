package com.manuelrodriguez.pokedex.domain.useCases

import com.manuelrodriguez.pokedex.repository.PokemonRepository

class RequestPokemonListUseCase(private val repository: PokemonRepository) {
    suspend operator fun invoke() = repository.requestPokemonList()
}



