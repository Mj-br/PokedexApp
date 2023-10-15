package com.manuelrodriguez.pokedex.data.remote.responses

import com.google.gson.annotations.SerializedName

data class GenerationVi(
    @SerializedName("generation-vi") val omegarubyAlphasapphire: OmegarubyAlphasapphire,
    @SerializedName("generation-vii") val xY: XY
)