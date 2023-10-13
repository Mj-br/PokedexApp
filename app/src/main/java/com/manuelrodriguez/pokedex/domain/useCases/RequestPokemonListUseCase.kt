package com.manuelrodriguez.pokedex.domain.useCases

import com.manuelrodriguez.pokedex.data.repositories.PokemonListRepository

class RequestPokemonListUseCase(private val repository: PokemonListRepository) {
    suspend operator fun invoke() = repository.requestPokemonList()
}



