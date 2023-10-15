package com.manuelrodriguez.pokedex.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalPokedexEntryList(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val url: String,
    val favorite: Boolean = false

)