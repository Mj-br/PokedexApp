package com.manuelrodriguez.pokedex.domain.useCases

import com.manuelrodriguez.pokedex.data.local.model.PokemonList
import com.manuelrodriguez.pokedex.repository.PokemonRepository

class UpdatePokemonListUseCase(private val repository: PokemonRepository) {
    suspend operator fun invoke(pokemon: PokemonList) = repository.updatePokemonList(pokemon)
}