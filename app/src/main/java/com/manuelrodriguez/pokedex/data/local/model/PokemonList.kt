package com.manuelrodriguez.pokedex.data.local.model

import androidx.room.Entity

@Entity
data class PokemonList (
    val name: String,
    val url: String,
    val favorite: Boolean = false

)