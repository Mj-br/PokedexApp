package com.manuelrodriguez.pokedex.domain.useCases

import com.manuelrodriguez.pokedex.data.models.PokemonList
import com.manuelrodriguez.pokedex.data.repositories.PokemonListRepository

class UpdatePokemonListUseCase(private val repository: PokemonListRepository) {
    suspend operator fun invoke(pokemon: PokemonList) = repository.updatePokemonList(pokemon)
}