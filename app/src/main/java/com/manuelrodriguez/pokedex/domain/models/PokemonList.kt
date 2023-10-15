package com.manuelrodriguez.pokedex.domain.models

import androidx.room.Entity

@Entity
data class PokemonList (
    val name: String,
    val url: String,
    val favorite: Boolean = false

)