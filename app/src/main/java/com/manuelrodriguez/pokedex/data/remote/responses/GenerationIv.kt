package com.manuelrodriguez.pokedex.data.remote.responses

import com.google.gson.annotations.SerializedName

data class GenerationIv(
    @SerializedName("diamond-pearl") val diamondPearl: DiamondPearl,
    @SerializedName("heartgold-soulsilver") val heartgolSsoulsilver: HeartgoldSoulsilver,
    val platinum: Platinum
)